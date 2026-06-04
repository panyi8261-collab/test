package com.example.service;

import com.example.mapper.PopulationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Group 6 — 不利因素（scoreIndex=6）
 * 陵园数量
 */
@Service
public class AdverseFactorServiceImpl extends RegionScoreDataService {

    @Autowired
    private PopulationMapper populationMapper;

    @Override
    public Map<String, List<String>> listScoreByGridMap(String date, List<String> gridIdList) {
        Map<String, List<String>> resultMap = new HashMap<>();

        // 1. 陵园数量
        getResultMapByStatistics(
                populationMapper.calcCemeteryCount(gridIdList, date),
                resultMap, gridIdList);

        return resultMap;
    }

    @Override
    public Integer getScoreIndex() {
        return 6;
    }

    @Override
    public String getType() {
        return "RR";
    }
}
