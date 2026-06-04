package com.example.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ReportData {

    private Meta meta;
    private List<Dim> dims;
    private List<Tier> tiers;
    private List<Point> points;
    private Single single;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Dim> getDims() {
        return dims;
    }

    public void setDims(List<Dim> dims) {
        this.dims = dims;
    }

    public List<Tier> getTiers() {
        return tiers;
    }

    public void setTiers(List<Tier> tiers) {
        this.tiers = tiers;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public Single getSingle() {
        return single;
    }

    public void setSingle(Single single) {
        this.single = single;
    }

    public static class Meta {
        private String reportTitle;
        private String city;
        private String region;
        private String model;
        private String period;
        private int count;
        private String area;
        private String gap;
        private String regionSubtitle;
        @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime generatedAt;

        public String getReportTitle() {
            return reportTitle;
        }

        public void setReportTitle(String reportTitle) {
            this.reportTitle = reportTitle;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getGap() {
            return gap;
        }

        public void setGap(String gap) {
            this.gap = gap;
        }

        public String getRegionSubtitle() {
            return regionSubtitle;
        }

        public void setRegionSubtitle(String regionSubtitle) {
            this.regionSubtitle = regionSubtitle;
        }

        public LocalDateTime getGeneratedAt() {
            return generatedAt;
        }

        public void setGeneratedAt(LocalDateTime generatedAt) {
            this.generatedAt = generatedAt;
        }
    }

    public static class Dim {
        private String key;
        private String name;
        private BigDecimal weight;
        private String desc;
        private String src;
        private BigDecimal min;
        private BigDecimal max;
        private BigDecimal avg;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getWeight() {
            return weight;
        }

        public void setWeight(BigDecimal weight) {
            this.weight = weight;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public BigDecimal getMin() {
            return min;
        }

        public void setMin(BigDecimal min) {
            this.min = min;
        }

        public BigDecimal getMax() {
            return max;
        }

        public void setMax(BigDecimal max) {
            this.max = max;
        }

        public BigDecimal getAvg() {
            return avg;
        }

        public void setAvg(BigDecimal avg) {
            this.avg = avg;
        }
    }

    public static class Tier {
        private String key;
        private String name;
        private String range;
        private BigDecimal min;
        private String colorKey;
        private String strategy;
        private int count;
        private BigDecimal avg;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRange() {
            return range;
        }

        public void setRange(String range) {
            this.range = range;
        }

        public BigDecimal getMin() {
            return min;
        }

        public void setMin(BigDecimal min) {
            this.min = min;
        }

        public String getColorKey() {
            return colorKey;
        }

        public void setColorKey(String colorKey) {
            this.colorKey = colorKey;
        }

        public String getStrategy() {
            return strategy;
        }

        public void setStrategy(String strategy) {
            this.strategy = strategy;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public BigDecimal getAvg() {
            return avg;
        }

        public void setAvg(BigDecimal avg) {
            this.avg = avg;
        }
    }

    public static class Point {
        private int rank;
        private String code;
        private String region;
        private BigDecimal score;
        private String coord;
        private String tierKey;
        private Map<String, BigDecimal> dims;

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public BigDecimal getScore() {
            return score;
        }

        public void setScore(BigDecimal score) {
            this.score = score;
        }

        public String getCoord() {
            return coord;
        }

        public void setCoord(String coord) {
            this.coord = coord;
        }

        public String getTierKey() {
            return tierKey;
        }

        public void setTierKey(String tierKey) {
            this.tierKey = tierKey;
        }

        public Map<String, BigDecimal> getDims() {
            return dims;
        }

        public void setDims(Map<String, BigDecimal> dims) {
            this.dims = dims;
        }
    }

    public static class Single {
        private String defaultPointCode;
        private List<Object> bankCompetition;
        private List<Object> insights;
        private String verdictTitle;

        public String getDefaultPointCode() {
            return defaultPointCode;
        }

        public void setDefaultPointCode(String defaultPointCode) {
            this.defaultPointCode = defaultPointCode;
        }

        public List<Object> getBankCompetition() {
            return bankCompetition;
        }

        public void setBankCompetition(List<Object> bankCompetition) {
            this.bankCompetition = bankCompetition;
        }

        public List<Object> getInsights() {
            return insights;
        }

        public void setInsights(List<Object> insights) {
            this.insights = insights;
        }

        public String getVerdictTitle() {
            return verdictTitle;
        }

        public void setVerdictTitle(String verdictTitle) {
            this.verdictTitle = verdictTitle;
        }
    }
}
