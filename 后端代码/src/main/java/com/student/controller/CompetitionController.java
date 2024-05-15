package com.student.controller;

import cn.hutool.core.lang.UUID;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.student.common.PageVo;
import com.student.dao.entity.Competition;
import com.student.dao.entity.Result;
import com.student.dao.entity.StUser;
import com.student.servie.ICompetitionService;
import com.student.utils.PageUtil;
import com.student.utils.ResultUtil;
import com.student.utils.ZwzNullUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author lxy
 * @date 2024/5/12 19:46
 */

@RestController
@RequestMapping("/competition")
public class CompetitionController {

    //头像图片上传地址
    @Value("E:/")
    private String imgFile;

    @Autowired
    private ICompetitionService iCompetitionService;


    @RequestMapping("/upEvidence")
    public void upAvatar(@RequestBody MultipartFile file){
        String oriFilename= file.getOriginalFilename();
        //用uuid构造唯一文件名
        String newFilename= UUID.randomUUID()+"-"+"-"+oriFilename;
        //文件路径
        File filePath = new File(imgFile);
        if(!filePath.exists()){
            if(!filePath.mkdirs()) {

            }
        }
        //上传新文件
        try {
            file.transferTo(new File(imgFile+newFilename));
        } catch (IOException e) {

        }

    }

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条学科竞赛")
    public Result<Competition> get(@RequestParam String id){
        return new ResultUtil<Competition>().setData(iCompetitionService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部学科竞赛个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iCompetitionService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部学科竞赛")
    public Result<List<Competition>> getAll(){
        return new ResultUtil<List<Competition>>().setData(iCompetitionService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询学科竞赛")
    public Result<IPage<Competition>> getByPage(@ModelAttribute Competition competition , @ModelAttribute PageVo page){
        QueryWrapper<Competition> qw = new QueryWrapper<>();
        // if(!ZwzNullUtils.isNull(competition.getCreateBy())) {
        // }
        IPage<Competition> data = iCompetitionService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<Competition>>().setData(data);
    }

    @RequestMapping(value = "/getMyPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询我的学科竞赛")
    public Result<IPage<Competition>> getMyPage(@ModelAttribute Competition competition ,@ModelAttribute PageVo page){
        StUser currUser = (StUser) SecurityUtils.getSubject().getPrincipal();
        // QueryWrapper<StudentEvaluate> seQw = new QueryWrapper<>();
        // seQw.eq("user_id",currUser.getId());
        // List<StudentEvaluate> evaluateList = iStudentEvaluateService.list(seQw);
        // StudentEvaluate se = null;
        // if(evaluateList.size() < 1) {
        //     se = EvaluateUtils.getBlackStudentEvaluate(currUser.getId());
        //     iStudentEvaluateService.saveOrUpdate(se);
        // } else {
        //     se = evaluateList.get(0);
        // }
        QueryWrapper<Competition> qw = new QueryWrapper<>();
        qw.eq("evaluate_id",currUser.getId());
        if(!ZwzNullUtils.isNull(competition.getLevel())) {
            qw.like("level",competition.getLevel());
        }
        if(!ZwzNullUtils.isNull(competition.getTitle())) {
            qw.like("title",competition.getTitle());
        }
        if(!ZwzNullUtils.isNull(competition.getValue())) {
            qw.like("value",competition.getValue());
        }
        IPage<Competition> data = iCompetitionService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<Competition>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改学科竞赛")
    public Result<Competition> saveOrUpdate(Competition competition){
        if(iCompetitionService.saveOrUpdate(competition)){
            return new ResultUtil<Competition>().setData(competition);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增学科竞赛")
    public Result<Competition> insert(Competition competition){
        // StudentEvaluate se = evaluateUtils.getMyStudentEvaluate();
        // competition.setEvaluateId(se.getId());
        StUser currUser = (StUser) SecurityUtils.getSubject().getPrincipal();
        competition.setEvaluateId(currUser.getId().toString());
        competition.setId(UUID.randomUUID().toString());
        iCompetitionService.saveOrUpdate(competition);

        // calGrades(se);
        return new ResultUtil<Competition>().setData(competition);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑学科竞赛")
    public Result<Competition> update(Competition competition){
        // StudentEvaluate se = evaluateUtils.getMyStudentEvaluate();
        // competition.setEvaluateId(se.getId());
        iCompetitionService.saveOrUpdate(competition);
        // calGrades(se);
        return new ResultUtil<Competition>().setData(competition);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除学科竞赛")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iCompetitionService.removeById(id);
        }
        return ResultUtil.success();
    }


    // @ApiOperation(value = "重算竞赛得分")
    // private void calGrades(StudentEvaluate se) {
        // 找到竞赛
    //     QueryWrapper<Competition> qw = new QueryWrapper<>();
    //     qw.eq("evaluate_id",se.getId());
    //     List<Competition> competitionList = iCompetitionService.list(qw);
    //     int sum = 0;
    //     for (Competition competition : competitionList) {
    //         if(Objects.equals("国家级",competition.getLevel())) {
    //             sum += 10;
    //         } else if(Objects.equals("省级",competition.getLevel())) {
    //             sum += 5;
    //         } else if(Objects.equals("市级",competition.getLevel())) {
    //             sum += 3;
    //         } else if(Objects.equals("校级",competition.getLevel())) {
    //             sum += 1;
    //         }
    //     }
    //     se.setCompetitionMine(BigDecimal.valueOf(sum));
    //     double oneSum = (se.getAchievementSum().floatValue() +  se.getQuestionnaireOne().floatValue()) / 2 + se.getCompetitionMine().floatValue();
    //     se.setAnsOne(BigDecimal.valueOf(oneSum));
    //     double ansSum = (se.getAnsOne().floatValue() + se.getAnsTwo().floatValue() + se.getAnsThree().floatValue()) / 3;
    //     se.setAnsSum(BigDecimal.valueOf(ansSum));
    //     iStudentEvaluateService.saveOrUpdate(se);
    // }

}
