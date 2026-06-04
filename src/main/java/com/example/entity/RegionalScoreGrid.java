package com.example.entity;

import java.math.BigDecimal;

public class RegionalScoreGrid {

    private Long gridId;
    private Long regionalScoreId;
    private String name;
    private BigDecimal gridIndicatorScore;
    private BigDecimal gridPopScore;
    private BigDecimal gridPeerScore;
    private BigDecimal gridResourceScore;
    private BigDecimal gridTrafficScore;
    private BigDecimal gridRoadScore;

    public Long getGridId() {
        return gridId;
    }

    public void setGridId(Long gridId) {
        this.gridId = gridId;
    }

    public Long getRegionalScoreId() {
        return regionalScoreId;
    }

    public void setRegionalScoreId(Long regionalScoreId) {
        this.regionalScoreId = regionalScoreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getGridIndicatorScore() {
        return gridIndicatorScore;
    }

    public void setGridIndicatorScore(BigDecimal gridIndicatorScore) {
        this.gridIndicatorScore = gridIndicatorScore;
    }

    public BigDecimal getGridPopScore() {
        return gridPopScore;
    }

    public void setGridPopScore(BigDecimal gridPopScore) {
        this.gridPopScore = gridPopScore;
    }

    public BigDecimal getGridPeerScore() {
        return gridPeerScore;
    }

    public void setGridPeerScore(BigDecimal gridPeerScore) {
        this.gridPeerScore = gridPeerScore;
    }

    public BigDecimal getGridResourceScore() {
        return gridResourceScore;
    }

    public void setGridResourceScore(BigDecimal gridResourceScore) {
        this.gridResourceScore = gridResourceScore;
    }

    public BigDecimal getGridTrafficScore() {
        return gridTrafficScore;
    }

    public void setGridTrafficScore(BigDecimal gridTrafficScore) {
        this.gridTrafficScore = gridTrafficScore;
    }

    public BigDecimal getGridRoadScore() {
        return gridRoadScore;
    }

    public void setGridRoadScore(BigDecimal gridRoadScore) {
        this.gridRoadScore = gridRoadScore;
    }
}
