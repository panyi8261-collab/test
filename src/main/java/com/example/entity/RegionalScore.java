package com.example.entity;

import java.time.LocalDateTime;

public class RegionalScore {

    private Long id;
    private String name;
    private Short type;
    private Integer provinceCode;
    private Integer cityCode;
    private String districtCode;
    private String collectionDate;
    private Long modelId;
    private Short status;
    private String modelParamJson;
    private String modelScoreMaxMin;
    private Short useType;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String coord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Integer getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(Integer provinceCode) {
        this.provinceCode = provinceCode;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getModelParamJson() {
        return modelParamJson;
    }

    public void setModelParamJson(String modelParamJson) {
        this.modelParamJson = modelParamJson;
    }

    public String getModelScoreMaxMin() {
        return modelScoreMaxMin;
    }

    public void setModelScoreMaxMin(String modelScoreMaxMin) {
        this.modelScoreMaxMin = modelScoreMaxMin;
    }

    public Short getUseType() {
        return useType;
    }

    public void setUseType(Short useType) {
        this.useType = useType;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }
}
