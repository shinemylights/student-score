package com.student.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.dao.domain.Course;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 清晨
* @description 针对表【course】的数据库操作Mapper
* @createDate 2024-04-18 23:14:53
* @Entity com.lxy.portrait.domain.Course
*/
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

}




