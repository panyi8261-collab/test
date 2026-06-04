package com.example.service;

import com.example.mapper.PopulationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Group 5 — 交通便利因素（scoreIndex=5）
 * 公交站数 / 停车场数量 / 地铁站数
 */
@Service
public class TransportationServiceImpl extends RegionScoreDataService {

    @Autowired
    private PopulationMapper populationMapper;

    @Override
    public Map<String, List<String>> listScoreByGridMap(String date, List<String> gridIdList) {
        Map<String, List<String>> resultMap = new HashMap<>();

        // 1. 公交站数
        getResultMapByStatistics(
                populationMapper.calcGeoLocCount(gridIdList, date, Arrays.asList("公交站"), null),
                resultMap, gridIdList);
        // 2. 停车场数量
        getResultMapByStatistics(
                populationMapper.calcGeoLocCount(gridIdList, date, Arrays.asList("停车场"), null),
                resultMap, gridIdList);
        // 3. 地铁站数
        getResultMapByStatistics(
                populationMapper.calcGeoLocCount(gridIdList, date, Arrays.asList("地铁站"), null),
                resultMap, gridIdList);

        return resultMap;
    }

    @Override
    public Integer getScoreIndex() {
        return 5;
    }

    @Override
    public String getType() {
        return "RR";
    }
}
