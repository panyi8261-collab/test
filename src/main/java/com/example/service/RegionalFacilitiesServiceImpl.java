package com.example.service;

import com.example.mapper.PopulationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Group 3 — 区域配套（scoreIndex=3）
 * 购物中心个数 / 超市 / 小区数 / 公司数 / 行政事业单位数量 / 高校数量 / 医院数量
 */
@Service
public class RegionalFacilitiesServiceImpl extends RegionScoreDataService {

    @Autowired
    private PopulationMapper populationMapper;

    @Override
    public Map<String, List<String>> listScoreByGridMap(String date, List<String> gridIdList) {
        Map<String, List<String>> resultMap = new HashMap<>();

        // 1. 购物中心个数
        getResultMapByStatistics(
                populationMapper.calcGeoLocCount(gridIdList, date, Arrays.asList("购物中心"), null),
                resultMap, gridIdList);
        // 2. 超市
        getResultMapByStatistics(
                populationMapper.calcGeoLocCount(gridIdList, date, Arrays.asList("超市", "便利超市", "大卖场"), null),
                resultMap, gridIdList);
        // 3. 小区数
        getResultMapByStatistics(
                populationMapper.calcGeoLocCount(gridIdList, date, Arrays.asList("住宅区"), null),
                resultMap, gridIdList);
        // 4. 公司数
        getResultMapByStatistics(
                populationMapper.calcGeoLocCount(gridIdList, date, Arrays.asList("公司"), null),
                resultMap, gridIdList);
        // 5. 行政事业单位数量
        getResultMapByStatistics(
                populationMapper.calcGeoLocCount(gridIdList, date, null, "政府机构"),
                resultMap, gridIdList);
        // 6. 高校数量
        getResultMapByStatistics(
                populationMapper.calcGeoLocCount(gridIdList, date, Arrays.asList("中学", "高等院校", "成人教育"), null),
                resultMap, gridIdList);
        // 7. 医院数量
        getResultMapByStatistics(
                populationMapper.calcGeoLocCount(gridIdList, date, Arrays.asList("医院"), null),
                resultMap, gridIdList);

        return resultMap;
    }

    @Override
    public Integer getScoreIndex() {
        return 3;
    }

    @Override
    public String getType() {
        return "RR";
    }
}
