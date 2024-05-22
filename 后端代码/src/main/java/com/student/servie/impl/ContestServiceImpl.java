package com.student.servie.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.dao.entity.Contest;
import com.student.servie.ContestService;
import com.student.dao.mapper.ContestMapper;
import org.springframework.stereotype.Service;

/**
* @author 清晨
* @description 针对表【contest】的数据库操作Service实现
* @createDate 2024-05-22 23:03:46
*/
@Service
public class ContestServiceImpl extends ServiceImpl<ContestMapper, Contest>
    implements ContestService{

}




