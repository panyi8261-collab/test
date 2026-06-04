package com.example.dto;

import java.util.List;

public class SiteReportRequest {

    private Long regionalScoreId;
    private String scope;
    private String box;
    private List<Long> pointIds;

    public Long getRegionalScoreId() {
        return regionalScoreId;
    }

    public void setRegionalScoreId(Long regionalScoreId) {
        this.regionalScoreId = regionalScoreId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public List<Long> getPointIds() {
        return pointIds;
    }

    public void setPointIds(List<Long> pointIds) {
        this.pointIds = pointIds;
    }
}
