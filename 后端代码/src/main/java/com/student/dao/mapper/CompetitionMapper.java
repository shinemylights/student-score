package com.student.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.dao.entity.Competition;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学科竞赛数据处理层
 * @author 郑为中
 */
@Mapper
public interface CompetitionMapper extends BaseMapper<Competition> {

}