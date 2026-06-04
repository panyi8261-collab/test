package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.ReportData;
import com.example.dto.SiteReportRequest;
import com.example.entity.RegionalScore;
import com.example.entity.RegionalScoreGrid;
import com.example.mapper.RegionalScoreMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SiteReportService {

    private final RegionalScoreMapper regionalScoreMapper;
    private final ObjectMapper objectMapper;

    public SiteReportService(RegionalScoreMapper regionalScoreMapper, ObjectMapper objectMapper) {
        this.regionalScoreMapper = regionalScoreMapper;
        this.objectMapper = objectMapper;
    }

    public ApiResponse<ReportData> buildReport(SiteReportRequest request) {
        ApiResponse<ReportData> invalidRequest = validateRequest(request);
        if (invalidRequest != null) {
            return invalidRequest;
        }

        RegionalScore regionalScore = regionalScoreMapper.selectRegionalScoreById(request.getRegionalScoreId());
        if (regionalScore == null || isNotCompleted(regionalScore)) {
            return ApiResponse.error(1002, "评估不存在或尚未完成");
        }

        String scope = resolveScope(request.getScope(), regionalScore.getUseType());
        if (scope == null) {
            return ApiResponse.error(1001, "scope 只能是 multi 或 single");
        }
        if ("single".equals(scope) && !CollectionUtils.isEmpty(request.getPointIds()) && request.getPointIds().size() > 1) {
            return ApiResponse.error(1001, "single 模式只能传一个 pointId");
        }

        List<RegionalScoreGrid> grids = regionalScoreMapper.selectGridScores(regionalScore.getId(), request.getPointIds());
        if (!CollectionUtils.isEmpty(request.getPointIds()) && countDistinctGridIds(grids) < uniquePointIds(request.getPointIds()).size()) {
            return ApiResponse.error(1003, "指定点位不属于该评估");
        }
        if ("single".equals(scope)) {
            grids = selectSingleGrid(grids);
            if (grids.isEmpty()) {
                return ApiResponse.error(1003, "该评估没有可用点位");
            }
        }

        sortGrids(grids);
        List<DimensionConfig> dimensions = buildDimensionConfigs(regionalScore.getModelParamJson());
        TierRuleConfig tierRuleConfig = resolveTierRuleConfig(regionalScore.getModelScoreMaxMin());
        List<ReportData.Point> points = buildPoints(regionalScore, grids, dimensions, tierRuleConfig);
        List<ReportData.Dim> dims = buildDims(points, dimensions);
        List<ReportData.Tier> tiers = buildTiers(points, tierRuleConfig);

        ReportData data = new ReportData();
        data.setMeta(buildMeta(regionalScore, scope, points.size()));
        data.setDims(dims);
        data.setTiers(tiers);
        data.setPoints(points);
        data.setSingle("single".equals(scope) ? buildSingle(points) : null);
        return ApiResponse.ok(data);
    }

    private ApiResponse<ReportData> validateRequest(SiteReportRequest request) {
        if (request == null || request.getRegionalScoreId() == null) {
            return ApiResponse.error(1001, "regionalScoreId 不能为空");
        }
        if (StringUtils.hasText(request.getBox())
                && !"white".equals(request.getBox())
                && !"black".equals(request.getBox())) {
            return ApiResponse.error(1001, "box 只能是 white 或 black");
        }
        if (StringUtils.hasText(request.getScope())
                && !"multi".equals(request.getScope())
                && !"single".equals(request.getScope())) {
            return ApiResponse.error(1001, "scope 只能是 multi 或 single");
        }
        if (!CollectionUtils.isEmpty(request.getPointIds())) {
            for (Long pointId : request.getPointIds()) {
                if (pointId == null) {
                    return ApiResponse.error(1001, "pointIds 不能包含空值");
                }
            }
        }
        if ("single".equals(request.getScope())
                && !CollectionUtils.isEmpty(request.getPointIds())
                && request.getPointIds().size() > 1) {
            return ApiResponse.error(1001, "single 模式只能传一个 pointId");
        }
        return null;
    }

    private boolean isNotCompleted(RegionalScore regionalScore) {
        return regionalScore.getStatus() != null && regionalScore.getStatus().intValue() != 1;
    }

    private String resolveScope(String requestScope, Short useType) {
        if (!StringUtils.hasText(requestScope)) {
            return useType != null && useType.intValue() == 2 ? "single" : "multi";
        }
        if ("multi".equals(requestScope) || "single".equals(requestScope)) {
            return requestScope;
        }
        return null;
    }

    private int countDistinctGridIds(List<RegionalScoreGrid> grids) {
        Set<Long> gridIds = new HashSet<Long>();
        for (RegionalScoreGrid grid : grids) {
            gridIds.add(grid.getGridId());
        }
        return gridIds.size();
    }

    private Set<Long> uniquePointIds(List<Long> pointIds) {
        Set<Long> uniqueIds = new HashSet<Long>();
        for (Long pointId : pointIds) {
            if (pointId != null) {
                uniqueIds.add(pointId);
            }
        }
        return uniqueIds;
    }

    private List<RegionalScoreGrid> selectSingleGrid(List<RegionalScoreGrid> grids) {
        if (CollectionUtils.isEmpty(grids)) {
            return Collections.emptyList();
        }
        sortGrids(grids);
        List<RegionalScoreGrid> result = new ArrayList<RegionalScoreGrid>();
        result.add(grids.get(0));
        return result;
    }

    private void sortGrids(List<RegionalScoreGrid> grids) {
        Collections.sort(grids, new Comparator<RegionalScoreGrid>() {
            @Override
            public int compare(RegionalScoreGrid left, RegionalScoreGrid right) {
                BigDecimal leftScore = requireScore(left);
                BigDecimal rightScore = requireScore(right);
                return rightScore.compareTo(leftScore);
            }
        });
    }

    private BigDecimal requireScore(RegionalScoreGrid grid) {
        if (grid.getGridIndicatorScore() == null) {
            throw new IllegalStateException("网格 " + grid.getGridId() + " 缺少综合得分 grid_indicator_score");
        }
        return grid.getGridIndicatorScore();
    }

    private ReportData.Meta buildMeta(RegionalScore regionalScore, String scope, int pointCount) {
        ReportData.Meta meta = new ReportData.Meta();
        meta.setReportTitle("single".equals(scope) ? "单点点位评估报告" : "全量点位评估报告");
        meta.setCity(regionalScore.getCityCode() == null ? null : regionalScore.getCityCode().toString());
        meta.setRegion(regionalScore.getName());
        meta.setModel(regionalScore.getModelId() == null ? null : regionalScore.getModelId().toString());
        meta.setPeriod(regionalScore.getCollectionDate());
        meta.setCount(pointCount);
        meta.setArea(null);
        meta.setGap(null);
        meta.setRegionSubtitle(null);
        meta.setGeneratedAt(regionalScore.getUpdateTime() != null ? regionalScore.getUpdateTime() : regionalScore.getCreateTime());
        return meta;
    }

    private List<ReportData.Point> buildPoints(RegionalScore regionalScore, List<RegionalScoreGrid> grids,
                                               List<DimensionConfig> dimensions, TierRuleConfig tierRuleConfig) {
        List<ReportData.Point> points = new ArrayList<ReportData.Point>();
        int rank = 1;
        for (RegionalScoreGrid grid : grids) {
            ReportData.Point point = new ReportData.Point();
            point.setRank(rank++);
            point.setCode(grid.getGridId() == null ? null : grid.getGridId().toString());
            point.setRegion(regionalScore.getName());
            point.setScore(requireScore(grid));
            point.setCoord(regionalScore.getCoord());
            point.setTierKey(resolveTierKey(point.getScore(), tierRuleConfig));
            point.setDims(buildPointDims(grid, dimensions));
            points.add(point);
        }
        return points;
    }

    private Map<String, BigDecimal> buildPointDims(RegionalScoreGrid grid, List<DimensionConfig> dimensions) {
        Map<String, BigDecimal> dims = new LinkedHashMap<String, BigDecimal>();
        for (DimensionConfig dimension : dimensions) {
            dims.put(dimension.getKey(), dimension.valueOf(grid));
        }
        return dims;
    }

    private List<ReportData.Dim> buildDims(List<ReportData.Point> points, List<DimensionConfig> dimensions) {
        List<ReportData.Dim> result = new ArrayList<ReportData.Dim>();
        for (DimensionConfig dimension : dimensions) {
            List<BigDecimal> values = new ArrayList<BigDecimal>();
            for (ReportData.Point point : points) {
                BigDecimal value = point.getDims().get(dimension.getKey());
                if (value != null) {
                    values.add(value);
                }
            }

            ReportData.Dim dim = new ReportData.Dim();
            dim.setKey(dimension.getKey());
            dim.setName(dimension.getName());
            dim.setWeight(dimension.getWeight());
            dim.setDesc("");
            dim.setSrc("");
            dim.setMin(min(values));
            dim.setMax(max(values));
            dim.setAvg(avg(values));
            result.add(dim);
        }
        return result;
    }

    private List<ReportData.Tier> buildTiers(List<ReportData.Point> points, TierRuleConfig tierRuleConfig) {
        List<TierConfig> configs = defaultTierConfigs(tierRuleConfig);
        List<ReportData.Tier> tiers = new ArrayList<ReportData.Tier>();
        for (TierConfig config : configs) {
            List<BigDecimal> scores = new ArrayList<BigDecimal>();
            for (ReportData.Point point : points) {
                if (config.getKey().equals(point.getTierKey())) {
                    scores.add(point.getScore());
                }
            }

            ReportData.Tier tier = new ReportData.Tier();
            tier.setKey(config.getKey());
            tier.setName(config.getName());
            tier.setRange(config.getRange());
            tier.setMin(config.getMin());
            tier.setColorKey(config.getKey());
            tier.setStrategy("");
            tier.setCount(scores.size());
            tier.setAvg(avg(scores));
            tiers.add(tier);
        }
        return tiers;
    }

    private ReportData.Single buildSingle(List<ReportData.Point> points) {
        ReportData.Single single = new ReportData.Single();
        single.setDefaultPointCode(points.isEmpty() ? null : points.get(0).getCode());
        single.setBankCompetition(Collections.emptyList());
        single.setInsights(Collections.emptyList());
        single.setVerdictTitle("综合评价");
        return single;
    }

    private String resolveTierKey(BigDecimal score, TierRuleConfig tierRuleConfig) {
        if (score.compareTo(tierRuleConfig.getPrefMin()) >= 0) {
            return "pref";
        }
        if (score.compareTo(tierRuleConfig.getRecMin()) >= 0) {
            return "rec";
        }
        if (score.compareTo(tierRuleConfig.getAltMin()) >= 0) {
            return "alt";
        }
        return "obs";
    }

    private BigDecimal min(List<BigDecimal> values) {
        if (values.isEmpty()) {
            return null;
        }
        BigDecimal min = values.get(0);
        for (BigDecimal value : values) {
            if (value.compareTo(min) < 0) {
                min = value;
            }
        }
        return scaleOne(min);
    }

    private BigDecimal max(List<BigDecimal> values) {
        if (values.isEmpty()) {
            return null;
        }
        BigDecimal max = values.get(0);
        for (BigDecimal value : values) {
            if (value.compareTo(max) > 0) {
                max = value;
            }
        }
        return scaleOne(max);
    }

    private BigDecimal avg(List<BigDecimal> values) {
        if (values.isEmpty()) {
            return null;
        }
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal value : values) {
            total = total.add(value);
        }
        return total.divide(new BigDecimal(values.size()), 1, RoundingMode.HALF_UP);
    }

    private BigDecimal scaleOne(BigDecimal value) {
        return value.setScale(1, RoundingMode.HALF_UP);
    }

    private List<DimensionConfig> buildDimensionConfigs(String modelParamJson) {
        List<DimensionConfig> dimensions = defaultDimensions();
        if (!StringUtils.hasText(modelParamJson)) {
            return dimensions;
        }

        Map<String, JsonNode> paramByScoreCode = parseModelParamJson(modelParamJson);
        for (DimensionConfig dimension : dimensions) {
            JsonNode node = paramByScoreCode.get(dimension.getScoreCode());
            if (node != null) {
                JsonNode scoreNameNode = node.get("scoreName");
                if (scoreNameNode != null && StringUtils.hasText(scoreNameNode.asText())) {
                    dimension.setName(scoreNameNode.asText());
                }
                JsonNode customWeightNode = node.get("customWeight");
                if (customWeightNode != null && !customWeightNode.isNull() && StringUtils.hasText(customWeightNode.asText())) {
                    dimension.setWeight(parseWeight(customWeightNode.asText(), dimension.getScoreCode()));
                }
            }
        }
        return dimensions;
    }

    private Map<String, JsonNode> parseModelParamJson(String modelParamJson) {
        JsonNode root;
        try {
            root = objectMapper.readTree(modelParamJson);
        } catch (IOException e) {
            throw new IllegalStateException("model_param_json 解析失败", e);
        }
        JsonNode modelParamJsonNode = root.get("modelParamJson");
        if (modelParamJsonNode == null || !modelParamJsonNode.isArray()) {
            return Collections.emptyMap();
        }
        Map<String, JsonNode> result = new HashMap<String, JsonNode>();
        for (JsonNode item : modelParamJsonNode) {
            JsonNode scoreCodeNode = item.get("scoreCode");
            if (scoreCodeNode != null && StringUtils.hasText(scoreCodeNode.asText())) {
                result.put(scoreCodeNode.asText(), item);
            }
        }
        return result;
    }

    private BigDecimal parseWeight(String rawWeight, String scoreCode) {
        try {
            return new BigDecimal(rawWeight.trim());
        } catch (NumberFormatException e) {
            throw new IllegalStateException(scoreCode + " 的 customWeight 不是有效数字: " + rawWeight, e);
        }
    }

    private List<DimensionConfig> defaultDimensions() {
        List<DimensionConfig> dimensions = new ArrayList<DimensionConfig>();
        dimensions.add(new DimensionConfig("pop", "pop_score", "人口规模评分", new ValueReader() {
            @Override
            public BigDecimal read(RegionalScoreGrid grid) {
                return grid.getGridPopScore();
            }
        }));
        dimensions.add(new DimensionConfig("peer", "peer_score", "同业竞争评分", new ValueReader() {
            @Override
            public BigDecimal read(RegionalScoreGrid grid) {
                return grid.getGridPeerScore();
            }
        }));
        dimensions.add(new DimensionConfig("resource", "resource_score", "资源评分", new ValueReader() {
            @Override
            public BigDecimal read(RegionalScoreGrid grid) {
                return grid.getGridResourceScore();
            }
        }));
        dimensions.add(new DimensionConfig("traffic", "traffic_score", "交通设施评分", new ValueReader() {
            @Override
            public BigDecimal read(RegionalScoreGrid grid) {
                return grid.getGridTrafficScore();
            }
        }));
        dimensions.add(new DimensionConfig("road", "road_score", "道路种类评分", new ValueReader() {
            @Override
            public BigDecimal read(RegionalScoreGrid grid) {
                return grid.getGridRoadScore();
            }
        }));
        return dimensions;
    }

    private TierRuleConfig resolveTierRuleConfig(String modelScoreMaxMin) {
        return new TierRuleConfig(new BigDecimal("90"), new BigDecimal("85"), new BigDecimal("80"));
    }

    private List<TierConfig> defaultTierConfigs(TierRuleConfig tierRuleConfig) {
        List<TierConfig> configs = new ArrayList<TierConfig>();
        configs.add(new TierConfig("pref", "优选区", "≥90分", tierRuleConfig.getPrefMin()));
        configs.add(new TierConfig("rec", "推荐区", "85-89分", tierRuleConfig.getRecMin()));
        configs.add(new TierConfig("alt", "备选区", "80-84分", tierRuleConfig.getAltMin()));
        configs.add(new TierConfig("obs", "观察区", "<80分", BigDecimal.ZERO));
        return configs;
    }

    private interface ValueReader {
        BigDecimal read(RegionalScoreGrid grid);
    }

    private static class DimensionConfig {
        private final String key;
        private final String scoreCode;
        private final ValueReader valueReader;
        private String name;
        private BigDecimal weight;

        DimensionConfig(String key, String scoreCode, String name, ValueReader valueReader) {
            this.key = key;
            this.scoreCode = scoreCode;
            this.name = name;
            this.valueReader = valueReader;
        }

        String getKey() {
            return key;
        }

        String getScoreCode() {
            return scoreCode;
        }

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        BigDecimal getWeight() {
            return weight;
        }

        void setWeight(BigDecimal weight) {
            this.weight = weight;
        }

        BigDecimal valueOf(RegionalScoreGrid grid) {
            return valueReader.read(grid);
        }
    }

    private static class TierConfig {
        private final String key;
        private final String name;
        private final String range;
        private final BigDecimal min;

        TierConfig(String key, String name, String range, BigDecimal min) {
            this.key = key;
            this.name = name;
            this.range = range;
            this.min = min;
        }

        String getKey() {
            return key;
        }

        String getName() {
            return name;
        }

        String getRange() {
            return range;
        }

        BigDecimal getMin() {
            return min;
        }
    }

    private static class TierRuleConfig {
        private final BigDecimal prefMin;
        private final BigDecimal recMin;
        private final BigDecimal altMin;

        TierRuleConfig(BigDecimal prefMin, BigDecimal recMin, BigDecimal altMin) {
            this.prefMin = prefMin;
            this.recMin = recMin;
            this.altMin = altMin;
        }

        BigDecimal getPrefMin() {
            return prefMin;
        }

        BigDecimal getRecMin() {
            return recMin;
        }

        BigDecimal getAltMin() {
            return altMin;
        }
    }
}
