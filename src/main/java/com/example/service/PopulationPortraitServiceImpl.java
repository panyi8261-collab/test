package com.example.service;

import com.example.mapper.PopulationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Group 1 — 人群画像（scoreIndex=1, 23项）
 */
@Service
public class PopulationPortraitServiceImpl extends RegionScoreDataService {

    @Autowired
    private PopulationMapper populationMapper;

    @Override
    public Map<String, List<String>> listScoreByGridMap(String date, List<String> gridIdList) {
        Map<String, List<String>> resultMap = new HashMap<>();

        // 1. 有房占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "homeOwner");
        // 2. 有车占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "car");
        // 3. 高资产人群占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "highAsset");
        // 4. 高净值人群占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "highEnd");
        // 5. 中产占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "middleClass");
        // 6. 高端消费占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "highEndConsume");
        // 7. 消费能力预测高占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "consumeHigh");
        // 8. 收入2w+人群占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "income2w");
        // 9. 本科及以上占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "bachelor");
        // 10. 银发占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "silverHaired");
        // 11. 小镇中老年占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "quinquagenarian");
        // 12. 35-54年龄人群密度比 WORK
        addResult("WORK", date, gridIdList, resultMap, "ageRatio");
        // 13. 白领占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "whiteCollar");
        // 14. 生产操作者占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "worker");
        // 15. 管理者和企业主占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "manager");
        // 16. 专业技术人员占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "engineer");
        // 17. 公务员&事业单位占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "servant");
        // 18. 教师占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "teacher");
        // 19. 商旅人士占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "businessTraveler");
        // 20. 餐饮生活消费占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "food");
        // 21. 超市便利店消费占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "supermarket");
        // 22. 网购能力预测占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "onlineShopping");
        // 23. 收入2499及以下人群占比 WORK
        addResult("WORK", date, gridIdList, resultMap, "incomeLow");

        return resultMap;
    }

    private void addResult(String type, String date, List<String> gridIdList,
                           Map<String, List<String>> resultMap, String indicator) {
        switch (indicator) {
            case "homeOwner":
                getResultMapByStatistics(populationMapper.calcHomeOwnerRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "car":
                getResultMapByStatistics(populationMapper.calcCarRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "highAsset":
                getResultMapByStatistics(populationMapper.calcHighAssetRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "highEnd":
                getResultMapByStatistics(populationMapper.calcHighEndIndividualRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "middleClass":
                getResultMapByStatistics(populationMapper.calcSeniorMiddleClassRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "highEndConsume":
                getResultMapByStatistics(populationMapper.calcHighEndConsumeRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "consumeHigh":
                getResultMapByStatistics(populationMapper.calcConsumeHighRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "income2w":
                getResultMapByStatistics(populationMapper.calcIncome2wRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "bachelor":
                getResultMapByStatistics(populationMapper.calcBachelorRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "silverHaired":
                getResultMapByStatistics(populationMapper.calcSilverHairedRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "quinquagenarian":
                getResultMapByStatistics(populationMapper.calcQuinquagenarianRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "ageRatio":
                getResultMapByStatistics(populationMapper.calcAgeRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "whiteCollar":
                getResultMapByStatistics(populationMapper.calcWhiteCollarRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "worker":
                getResultMapByStatistics(populationMapper.calcWorkerRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "manager":
                getResultMapByStatistics(populationMapper.calcManagerRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "engineer":
                getResultMapByStatistics(populationMapper.calcEngineerRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "servant":
                getResultMapByStatistics(populationMapper.calcServantRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "teacher":
                getResultMapByStatistics(populationMapper.calcTeacherRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "businessTraveler":
                getResultMapByStatistics(populationMapper.calcBusinessTravelerRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "food":
                getResultMapByStatistics(populationMapper.calcFoodRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "supermarket":
                getResultMapByStatistics(populationMapper.calcSupermarketRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "onlineShopping":
                getResultMapByStatistics(populationMapper.calcOnlineShoppingRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
            case "incomeLow":
                getResultMapByStatistics(populationMapper.calcIncomeLowRatio(gridIdList, date, type), resultMap, gridIdList);
                break;
        }
    }

    @Override
    public Integer getScoreIndex() {
        return 1;
    }

    @Override
    public String getType() {
        return "RR";
    }
}
