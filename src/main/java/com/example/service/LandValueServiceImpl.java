package com.example.service;

import com.example.mapper.PopulationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Group 4 — 地块价值（scoreIndex=4）
 * 平均房价比 / 平均房租比
 */
@Service
public class LandValueServiceImpl extends RegionScoreDataService {

    @Autowired
    private PopulationMapper populationMapper;

    @Override
    public Map<String, List<String>> listScoreByGridMap(String date, List<String> gridIdList) {
        Map<String, List<String>> resultMap = new HashMap<>();

        // 1. 平均房价比
        getResultMapByStatistics(
                populationMapper.calcAvgHousePrice(gridIdList, date),
                resultMap, gridIdList);
        // 2. 平均房租比
        getResultMapByStatistics(
                populationMapper.calcAvgRent(gridIdList, date),
                resultMap, gridIdList);

        return resultMap;
    }

    @Override
    public Integer getScoreIndex() {
        return 4;
    }

    @Override
    public String getType() {
        return "RR";
    }
}
