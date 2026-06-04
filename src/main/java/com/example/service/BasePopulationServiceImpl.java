package com.example.service;

import com.example.mapper.ConstantMapper;
import com.example.mapper.PopulationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Group 0 — 基础人口指标（scoreIndex=0）
 * 客流密度比 / 居住人口密度比 / 工作人口密度比 / 工作日节假日客流比
 */
@Service
public class BasePopulationServiceImpl extends RegionScoreDataService {

    @Autowired
    private PopulationMapper populationMapper;
    @Autowired
    private ConstantMapper constantMapper;

    @Override
    public Map<String, List<String>> listScoreByGridMap(String date, List<String> gridIdList) {
        Map<String, List<String>> resultMap = new HashMap<>();

        // 先从第一个 grid 查出城市编码（同批 grid 都在同一城市）
        String cityCode = constantMapper.getCityCodeByGridId(gridIdList.get(0), date);

        // 1. 客流密度比（动态查该城市总面积）
        Double cityArea = constantMapper.calcCityTotalArea(cityCode);
        getResultMapByStatistics(
                populationMapper.calcPedestrianDensity(gridIdList, date, cityArea),
                resultMap, gridIdList);

        // 2. 居住人口密度比
        Double avgHome = constantMapper.calcAvgHomePerGrid(cityCode, date);
        getResultMapByStatistics(
                populationMapper.calcResidentDensity(gridIdList, date, avgHome),
                resultMap, gridIdList);

        // 3. 工作人口密度比
        Double avgWork = constantMapper.calcAvgWorkPerGrid(cityCode, date);
        getResultMapByStatistics(
                populationMapper.calcWorkingDensity(gridIdList, date, avgWork),
                resultMap, gridIdList);

        // 4. 工作日节假日客流比
        getResultMapByStatistics(
                populationMapper.calcWorkHolidayFlowRatio(gridIdList, date),
                resultMap, gridIdList);

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
