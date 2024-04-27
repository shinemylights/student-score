package com.student.dao.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.dao.domain.Score;
import com.student.dao.mapper.ScoreMapper;
import com.student.dao.service.ScoreService;
import org.springframework.stereotype.Service;

/**
* @author 清晨
* @description 针对表【score】的数据库操作Service实现
* @createDate 2024-04-18 23:15:37
*/
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score>
    implements ScoreService {

}




