package com.example.mapper;

import com.example.entity.RegionalScore;
import com.example.entity.RegionalScoreGrid;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegionalScoreMapper {

    RegionalScore selectRegionalScoreById(@Param("id") Long id);

    List<RegionalScoreGrid> selectGridScores(@Param("regionalScoreId") Long regionalScoreId,
                                             @Param("pointIds") List<Long> pointIds);
}
