package com.student.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.dao.domain.Score;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 清晨
* @description 针对表【score】的数据库操作Mapper
* @createDate 2024-04-18 23:15:37
* @Entity com.lxy.portrait.domain.Score
*/
@Mapper
public interface ScoreMapper extends BaseMapper<Score> {

}




