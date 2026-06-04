package com.example.service;

import com.example.mapper.PopulationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExServiceImpl extends RegionScoreDataService{

    @Autowired
    private PopulationMapper populationMapper;

    @Override
    public Map<String, List<String>> listScoreByGridMap(String date, List<String> gridIdList) {
        Map<String,List<String>> resultMap = new HashMap<>();
        getResultMapByStatistics(populationMapper.calcAgeRatio(gridIdList,date,"HOME"),resultMap,gridIdList);
        return resultMap;
    }

    @Override
    public Integer getScoreIndex() {
        return 0;
    }

    @Override
    public String getType() {
        return "RR";
    }
}
