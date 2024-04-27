package com.student.dao.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.dao.domain.Course;
import com.student.dao.mapper.CourseMapper;
import com.student.dao.service.MyCourseService;
import org.springframework.stereotype.Service;

/**
* @author 清晨
* @description 针对表【course】的数据库操作Service实现
* @createDate 2024-04-18 23:14:53
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements MyCourseService {

}




