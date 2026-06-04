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
    // Group 0 — 基础人口指标
    // ========================================================================

    List<Map<String, Object>> calcAgeRatio(@Param("gridList") List<String> gridList,
                                           @Param("date") String date,
                                           @Param("populationType") String populationType);

    List<Map<String, Object>> calcMaleRatio(@Param("gridList") List<String> gridList,
                                            @Param("date") String date,
                                            @Param("populationType") String populationType);

    List<Map<String, Object>> calcCarRatio(@Param("gridList") List<String> gridList,
                                           @Param("date") String date,
                                           @Param("populationType") String populationType);

    List<Map<String, Object>> calcBachelorRatio(@Param("gridList") List<String> gridList,
                                                @Param("date") String date,
                                                @Param("populationType") String populationType);

    List<Map<String, Object>> calcHomeOwnerRatio(@Param("gridList") List<String> gridList,
                                                 @Param("date") String date,
                                                 @Param("populationType") String populationType);

    List<Map<String, Object>> calcWhiteCollarRatio(@Param("gridList") List<String> gridList,
                                                   @Param("date") String date,
                                                   @Param("populationType") String populationType);

    List<Map<String, Object>> calcSeniorMiddleClassRatio(@Param("gridList") List<String> gridList,
                                                         @Param("date") String date,
                                                         @Param("populationType") String populationType);

    List<Map<String, Object>> calcSilverHairedRatio(@Param("gridList") List<String> gridList,
                                                    @Param("date") String date,
                                                    @Param("populationType") String populationType);

    List<Map<String, Object>> calcFinancialManagementRatio(@Param("gridList") List<String> gridList,
                                                           @Param("date") String date,
                                                           @Param("populationType") String populationType);

    List<Map<String, Object>> calcConsumeHighRatio(@Param("gridList") List<String> gridList,
                                                   @Param("date") String date,
                                                   @Param("populationType") String populationType);

    List<Map<String, Object>> calcHighEndIndividualRatio(@Param("gridList") List<String> gridList,
                                                         @Param("date") String date,
                                                         @Param("populationType") String populationType);

    List<Map<String, Object>> calcPedestrianDensity(@Param("gridList") List<String> gridList,
                                                    @Param("date") String date,
                                                    @Param("totalArea") Double totalArea);

    List<Map<String, Object>> calcResidentDensity(@Param("gridList") List<String> gridList,
                                                  @Param("date") String date,
                                                  @Param("avgPerGrid") Double avgPerGrid);

    List<Map<String, Object>> calcWorkingDensity(@Param("gridList") List<String> gridList,
                                                 @Param("date") String date,
                                                 @Param("avgPerGrid") Double avgPerGrid);

    List<Map<String, Object>> calcWorkHolidayFlowRatio(@Param("gridList") List<String> gridList,
                                                       @Param("date") String date);

    // ========================================================================
    // Group 1 — 人群画像（新增）
    // ========================================================================

    /** 高资产人群占比：asset_grade_high / (gender_female+gender_male) */
    List<Map<String, Object>> calcHighAssetRatio(@Param("gridList") List<String> gridList,
                                                 @Param("date") String date,
                                                 @Param("populationType") String populationType);

    /** 收入2w+人群占比：income_20000 / (gender_female+gender_male) */
    List<Map<String, Object>> calcIncome2wRatio(@Param("gridList") List<String> gridList,
                                                @Param("date") String date,
                                                @Param("populationType") String populationType);

    /** 收入2499及以下人群占比：income_9999 / (gender_female+gender_male) */
    List<Map<String, Object>> calcIncomeLowRatio(@Param("gridList") List<String> gridList,
                                                 @Param("date") String date,
                                                 @Param("populationType") String populationType);

    /** 网购能力预测占比：online_shopping */
    List<Map<String, Object>> calcOnlineShoppingRatio(@Param("gridList") List<String> gridList,
                                                      @Param("date") String date,
                                                      @Param("populationType") String populationType);

    /** 小镇中老年占比：quinquagenarian */
    List<Map<String, Object>> calcQuinquagenarianRatio(@Param("gridList") List<String> gridList,
                                                       @Param("date") String date,
                                                       @Param("populationType") String populationType);

    /** 教师占比：teacher */
    List<Map<String, Object>> calcTeacherRatio(@Param("gridList") List<String> gridList,
                                               @Param("date") String date,
                                               @Param("populationType") String populationType);

    /** 商旅人士占比：business_traveler */
    List<Map<String, Object>> calcBusinessTravelerRatio(@Param("gridList") List<String> gridList,
                                                        @Param("date") String date,
                                                        @Param("populationType") String populationType);

    /** 公务员&事业单位占比：servant_public_institution */
    List<Map<String, Object>> calcServantRatio(@Param("gridList") List<String> gridList,
                                               @Param("date") String date,
                                               @Param("populationType") String populationType);

    /** 管理者和企业主占比：job_manager_bussines_owner */
    List<Map<String, Object>> calcManagerRatio(@Param("gridList") List<String> gridList,
                                               @Param("date") String date,
                                               @Param("populationType") String populationType);

    /** 生产操作者占比：job_worker */
    List<Map<String, Object>> calcWorkerRatio(@Param("gridList") List<String> gridList,
                                              @Param("date") String date,
                                              @Param("populationType") String populationType);

    /** 专业技术人员占比：job_predict_engineer */
    List<Map<String, Object>> calcEngineerRatio(@Param("gridList") List<String> gridList,
                                                @Param("date") String date,
                                                @Param("populationType") String populationType);

    /** 餐饮生活消费占比：food */
    List<Map<String, Object>> calcFoodRatio(@Param("gridList") List<String> gridList,
                                            @Param("date") String date,
                                            @Param("populationType") String populationType);

    /** 超市便利店消费占比：supermarket_convenience_store */
    List<Map<String, Object>> calcSupermarketRatio(@Param("gridList") List<String> gridList,
                                                   @Param("date") String date,
                                                   @Param("populationType") String populationType);

    /** 高端消费占比：accessories + electronic_digital */
    List<Map<String, Object>> calcHighEndConsumeRatio(@Param("gridList") List<String> gridList,
                                                      @Param("date") String date,
                                                      @Param("populationType") String populationType);

    // ========================================================================
    // Group 2 — 金融成熟度
    // ========================================================================

    /** 中行APP用户占比：debit_card_bc / 比例还原 */
    List<Map<String, Object>> calcBocAppRatio(@Param("gridList") List<String> gridList,
                                              @Param("date") String date,
                                              @Param("populationType") String populationType);

    /** 国有竞品APP用户占比：工农建交邮储之和 / 比例还原 */
    List<Map<String, Object>> calcStateOwnedAppRatio(@Param("gridList") List<String> gridList,
                                                     @Param("date") String date,
                                                     @Param("populationType") String populationType);

    /** 全国性股份制APP用户占比 */
    List<Map<String, Object>> calcJointStockAppRatio(@Param("gridList") List<String> gridList,
                                                     @Param("date") String date,
                                                     @Param("populationType") String populationType);

    /** 金融APP用户占比 */
    List<Map<String, Object>> calcFinanceAppRatio(@Param("gridList") List<String> gridList,
                                                  @Param("date") String date,
                                                  @Param("populationType") String populationType);

    // ========================================================================
    // Group 3 — 区域配套（tb_grid_geo_loc）
    // ========================================================================

    List<Map<String, Object>> calcGeoLocCount(@Param("gridList") List<String> gridList,
                                              @Param("date") String date,
                                              @Param("level2List") List<String> level2List,
                                              @Param("level1") String level1);

    // ========================================================================
    // Group 4 — 地块价值（tb_grid_land_value）
    // ========================================================================

    List<Map<String, Object>> calcAvgHousePrice(@Param("gridList") List<String> gridList,
                                                @Param("date") String date);

    List<Map<String, Object>> calcAvgRent(@Param("gridList") List<String> gridList,
                                          @Param("date") String date);

    // ========================================================================
    // Group 6 — 不利因素
    // ========================================================================

    List<Map<String, Object>> calcCemeteryCount(@Param("gridList") List<String> gridList,
                                                @Param("date") String date);
}
