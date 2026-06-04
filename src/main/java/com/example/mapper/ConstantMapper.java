package com.example.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 归一化常量计算 Mapper。
 * 用于预计算每类指标的全局均值分母，避免每次计算时重复查询。
 * 调用方先调此 Mapper 拿到常量，再传入 PopulationMapper 的计算方法中。
 */
public interface ConstantMapper {

    /**
     * 计算某城市某月份 HOME 类型平均每格居住人口（SUM(num)/6340）。
     * 对应原 SQL: select sum(num)/6340 from tb_grid_permanent_num where population_type='HOME'
     */
    Double calcAvgHomePerGrid(@Param("cityCode") String cityCode,
                              @Param("date") String date);

    /**
     * 计算某城市某月份 WORK 类型平均每格工作人口（SUM(num)/6340）。
     * 对应原 SQL: select sum(num)/6340 from tb_grid_permanent_num where population_type='WORK'
     */
    Double calcAvgWorkPerGrid(@Param("cityCode") String cityCode,
                              @Param("date") String date);

    /**
     * 从任意 grid_id + date 获取所属城市编码。
     */
    String getCityCodeByGridId(@Param("gridId") String gridId,
                               @Param("date") String date);

    /**
     * 计算某城市总面积（网格数 × 14400）。
     * 替代原写死的城市面积常量 1767984.22。
     */
    Double calcCityTotalArea(@Param("cityCode") String cityCode);
}
