package com.example.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 人口画像指标计算 Mapper。
 * 所有方法根据 grid 列表批量计算，返回 {code: grid_id, result: 计算值} 的映射列表。
 * <p>
 * 入参说明：
 * - gridList:       网格ID列表（同一城市的网格集合）
 * - date:           数据月份，格式 yyyyMM，如 202604
 * - populationType: 'HOME' 或 'WORK'
 * - 各密度/占比常量:   由 ConstantMapper 预计算后传入
 */
public interface PopulationMapper {

    // ========================================================================
    // 第一类：直接来自 tb_grid_pop_attr1（单表，无JOIN）
    // ========================================================================

    /**
     * 年龄 35-54 占比：(age_3544 + age_4554) / (gender_female + gender_male)
     * 对应 population_profile_age_35_54_*
     */
    List<Map<String, Object>> calcAgeRatio(@Param("gridList") List<String> gridList,
                                           @Param("date") String date,
                                           @Param("populationType") String populationType);

    /**
     * 男性占比：gender_male / (gender_female + gender_male)
     * 对应 man_*
     */
    List<Map<String, Object>> calcMaleRatio(@Param("gridList") List<String> gridList,
                                            @Param("date") String date,
                                            @Param("populationType") String populationType);

    /**
     * 有车占比：has_car / (gender_female + gender_male)
     * 对应 population_profile_car_*
     */
    List<Map<String, Object>> calcCarRatio(@Param("gridList") List<String> gridList,
                                           @Param("date") String date,
                                           @Param("populationType") String populationType);

    /**
     * 本科及以上占比：qualification_undergraduate_graduate / (gender_female + gender_male)
     * 对应 population_profile_bachelor_*
     */
    List<Map<String, Object>> calcBachelorRatio(@Param("gridList") List<String> gridList,
                                                @Param("date") String date,
                                                @Param("populationType") String populationType);

    // ========================================================================
    // 第二类：JOIN tb_grid_personalized_portrait（需要比例还原）
    // ========================================================================

    /**
     * 有房占比：SUM(there_room) / SUM(gender_female+gender_male)
     * 对应 population_profile_home_*
     */
    List<Map<String, Object>> calcHomeOwnerRatio(@Param("gridList") List<String> gridList,
                                                 @Param("date") String date,
                                                 @Param("populationType") String populationType);

    /**
     * 白领占比：white_collar_general_staff / 还原后总人口
     * 对应 population_profile_white_collar_*
     */
    List<Map<String, Object>> calcWhiteCollarRatio(@Param("gridList") List<String> gridList,
                                                   @Param("date") String date,
                                                   @Param("populationType") String populationType);

    /**
     * 中产占比：senior_middle_class / 还原后总人口
     * 对应 population_profile_senior_middle_class_*
     */
    List<Map<String, Object>> calcSeniorMiddleClassRatio(@Param("gridList") List<String> gridList,
                                                         @Param("date") String date,
                                                         @Param("populationType") String populationType);

    /**
     * 银发占比：urban_silver_haired / 还原后总人口
     * 对应 population_profile_urban_silver_haired_*
     */
    List<Map<String, Object>> calcSilverHairedRatio(@Param("gridList") List<String> gridList,
                                                    @Param("date") String date,
                                                    @Param("populationType") String populationType);

    /**
     * 金融理财人群占比：financial_management / 还原后总人口
     * 对应 population_profile_financial_management_*
     */
    List<Map<String, Object>> calcFinancialManagementRatio(@Param("gridList") List<String> gridList,
                                                           @Param("date") String date,
                                                           @Param("populationType") String populationType);

    /**
     * 高消费占比：consume_high / 还原后总人口
     * 对应 population_profile_consume_high_*
     */
    List<Map<String, Object>> calcConsumeHighRatio(@Param("gridList") List<String> gridList,
                                                   @Param("date") String date,
                                                   @Param("populationType") String populationType);

    /**
     * 高净值人群占比：hieg_end_individual / 还原后总人口
     * 对应 population_profile_hieg_end_individual_*
     */
    List<Map<String, Object>> calcHighEndIndividualRatio(@Param("gridList") List<String> gridList,
                                                         @Param("date") String date,
                                                         @Param("populationType") String populationType);

    // ========================================================================
    // 第三类：密度计算（需要面积 / 归一化常量）
    // ========================================================================

    /**
     * 客流密度：num / 14400 / cityTotalArea
     * 对应 pedestrian_density（cityTotalArea = 1767984.22 写死传入）
     */
    List<Map<String, Object>> calcPedestrianDensity(@Param("gridList") List<String> gridList,
                                                    @Param("date") String date,
                                                    @Param("totalArea") Double totalArea);

    /**
     * 居住人口密度：num / 14400 / avgHomePerGrid
     * 对应 resident_density（avgHomePerGrid 由 ConstantMapper.calcAvgHomePerGrid 预计算）
     */
    List<Map<String, Object>> calcResidentDensity(@Param("gridList") List<String> gridList,
                                                  @Param("date") String date,
                                                  @Param("avgPerGrid") Double avgPerGrid);

    /**
     * 工作人口密度：num / 14400 / avgWorkPerGrid
     * 对应 working_density（avgWorkPerGrid 由 ConstantMapper.calcAvgWorkPerGrid 预计算）
     */
    List<Map<String, Object>> calcWorkingDensity(@Param("gridList") List<String> gridList,
                                                 @Param("date") String date,
                                                 @Param("avgPerGrid") Double avgPerGrid);

    /**
     * 工作日/节假日客流比：WROK_HOUR_FLOW / HOLIDAY_HOUR_FLOW
     * 对应 work_perdestrian_density
     */
    List<Map<String, Object>> calcWorkHolidayFlowRatio(@Param("gridList") List<String> gridList,
                                                       @Param("date") String date);
}
