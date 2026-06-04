package com.example.service;

import com.example.mapper.PopulationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Group 2 — 金融成熟度（scoreIndex=2）
 * APP用户占比（中行 / 国有竞品 / 股份制 / 金融APP）
 */
@Service
public class FinanceMaturityServiceImpl extends RegionScoreDataService {

    @Autowired
    private PopulationMapper populationMapper;

    @Override
    public Map<String, List<String>> listScoreByGridMap(String date, List<String> gridIdList) {
        Map<String, List<String>> resultMap = new HashMap<>();

        // 1. 中行APP用户占比 WORK
        getResultMapByStatistics(
                populationMapper.calcBocAppRatio(gridIdList, date, "WORK"),
                resultMap, gridIdList);
        // 2. 国有竞品APP用户占比 WORK
        getResultMapByStatistics(
                populationMapper.calcStateOwnedAppRatio(gridIdList, date, "WORK"),
                resultMap, gridIdList);
        // 3. 全国性股份制APP用户占比 WORK
        getResultMapByStatistics(
                populationMapper.calcJointStockAppRatio(gridIdList, date, "WORK"),
                resultMap, gridIdList);
        // 4. 金融APP用户占比 WORK
        getResultMapByStatistics(
                populationMapper.calcFinanceAppRatio(gridIdList, date, "WORK"),
                resultMap, gridIdList);
        // 5. 金融理财人群占比 WORK（复用 Group1 的）
        getResultMapByStatistics(
                populationMapper.calcFinancialManagementRatio(gridIdList, date, "WORK"),
                resultMap, gridIdList);

        return resultMap;
    }

    @Override
    public Integer getScoreIndex() {
        return 2;
    }

    @Override
    public String getType() {
        return "RR";
    }
}
