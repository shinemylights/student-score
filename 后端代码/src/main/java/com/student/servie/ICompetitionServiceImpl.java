package com.student.servie;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.dao.entity.Competition;
import com.student.dao.mapper.CompetitionMapper;
import com.student.servie.ICompetitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 学科竞赛 服务层接口实现
 * @author 郑为中
 */
@Slf4j
@Service
public class ICompetitionServiceImpl extends ServiceImpl<CompetitionMapper, Competition> implements ICompetitionService {

}