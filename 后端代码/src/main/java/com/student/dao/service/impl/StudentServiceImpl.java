package com.student.dao.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.dao.domain.Student;
import com.student.dao.mapper.StudentMapper;
import com.student.dao.service.MyStudentService;
import org.springframework.stereotype.Service;

/**
* @author 清晨
* @description 针对表【student】的数据库操作Service实现
* @createDate 2024-04-18 23:16:40
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements MyStudentService {

}




