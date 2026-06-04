package com.example.service;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RegionScoreDataService {

    /**
     * 获取所有的数据
     * @param date 当前的时间
     * @param gridIdList 当前的网格id集合
     * @return
     */
    public abstract Map<String, List<String>> listScoreByGridMap(String date,List<String> gridIdList);

    /**
     * 当前的计算结果的下标
     * @return
     */
    public abstract Integer getScoreIndex();

    /**
     * 当前的类型
     * @return
     */
    public abstract String getType();

    /**
     * 获取最终的结果
     * @param statisticsList 当前的数据
     * @param resultMap 当前的返回结果
     * @param gridIdList 当前网格id集合
     */
    public void getResultMapByStatistics(List<Map<String,Object>> statisticsList, Map<String, List<String>> resultMap, List<String> gridIdList) {
        Map<String,String> statisticsMap = new HashMap<>();
        if(!CollectionUtils.isEmpty(statisticsList)) {
            for (Map<String,Object> currStatisticsMap: statisticsList) {
                if(currStatisticsMap.get("result")!=null) {
                    statisticsMap.put(currStatisticsMap.get("code").toString(),currStatisticsMap.get("result").toString());
                }
            }
        }
        for (String gridId: gridIdList) {
            List<String> currList = resultMap.getOrDefault(gridId,new ArrayList<>());
            String result = statisticsMap.getOrDefault(gridId,"0");
            currList.add(result);
            resultMap.put(gridId,currList);
        }
    }
}
