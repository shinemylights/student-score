package com.student.controller;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.common.PageVo;
import com.student.common.UploadResult;
import com.student.dao.entity.*;
import com.student.servie.ContestService;
import com.student.servie.ICompetitionService;
import com.student.servie.PracticeService;
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
import java.util.List;

/**
 * @author lxy
 * @date 2024/5/22 23:07
 */
@RestController
    @RequestMapping("/practice")
public class PracticeController {

    //头像图片上传地址
    @Value("E:/myImages/")
    private String imgFile;

    @Autowired
    private ICompetitionService iCompetitionService;

    @Autowired
    private ContestService contestService;

    @Autowired
    private PracticeService practiceService;


    @RequestMapping("/upEvidence")
    public Result<UploadResult> upEvidence(@RequestBody MultipartFile file){
        String oriFilename= file.getOriginalFilename();
        // 截取图片后缀名
        String extension = oriFilename.substring(oriFilename.lastIndexOf("."));
        //用构造文件名
        StUser currUser = (StUser) SecurityUtils.getSubject().getPrincipal();
        String newFilename= UUID.randomUUID()+"-"+currUser.getId()+extension;
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

        UploadResult uploadResult = new UploadResult();
        uploadResult.setProofImageUrl(newFilename);
        return new ResultUtil<UploadResult>().setData(uploadResult);

    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增社会实践")
    public Result<Practice> insert(@RequestBody Practice practice) {
        // StudentEvaluate se = evaluateUtils.getMyStudentEvaluate();
        // competition.setEvaluateId(se.getId());
        StUser currUser = (StUser) SecurityUtils.getSubject().getPrincipal();
        practice.setStudentId(currUser.getUsername());
        practice.setStudentName(currUser.getRealName());
        practice.setProofImageUrl(practice.getProofImageUrl().substring(1));

        practiceService.save(practice);
        return new ResultUtil<Practice>().setData(practice);
    }

    @RequestMapping(value = "/getMyPage", method = RequestMethod.POST)
    @ApiOperation(value = "查询我的社会实践")
    public Result<IPage<Practice>> getMyPage(@RequestBody PageVo page){
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
        QueryWrapper<Practice> qw = new QueryWrapper<>();
        qw.eq("student_id",currUser.getUsername());
        if(!ZwzNullUtils.isNull(page.getName())) {
            qw.like("name",page.getName());
        }

        IPage<Practice> data = practiceService.page(PageUtil.initMpPage(page), qw);

        return new ResultUtil<IPage<Practice>>().setData(data);


        // List<Competition> list = iCompetitionService.list(qw);
        // System.out.println(list);
        // // System.out.println(competition);
        // IPage<Competition> data = iCompetitionService.page(PageUtil.initMpPage(page),qw);
        // System.out.println(data);
        // return new ResultUtil<IPage<Competition>>().setData(data);
    }

    // @RequestMapping(value = "/insert", method = RequestMethod.POST)
    // @ApiOperation(value = "新增社会实践")
    // public Result<Contest> insert(@RequestBody Contest contest){
    //     // StudentEvaluate se = evaluateUtils.getMyStudentEvaluate();
    //     // competition.setEvaluateId(se.getId());
    //     StUser currUser = (StUser) SecurityUtils.getSubject().getPrincipal();
    //     contest.setStudentId(currUser.getUsername());
    //     contest.setStudentName(currUser.getRealName());
    //     contest.setProofImageUrl(contest.getProofImageUrl().substring(1));
    //     contestService.save(contest);
    //     return new ResultUtil<Contest>().setData(contest);
    //
    //     // competition.setEvaluateId(currUser.getId().toString());
    //     // competition.setId(UUID.randomUUID().toString());
    //     // competition.setImage(competition.getImage().substring(1));
    //     // System.out.println(competition);
    //     // iCompetitionService.saveOrUpdate(competition);
    //     //
    //     // // calGrades(se);
    //     // return new ResultUtil<Competition>().setData(competition);
    // }




    // @RequestMapping(value = "/getMyPage", method = RequestMethod.POST)
    // @ApiOperation(value = "查询我的社会实践")
    // public Result<IPage<Contest>> getMyPage(@RequestBody PageVo page){
    //     StUser currUser = (StUser) SecurityUtils.getSubject().getPrincipal();
    //     // QueryWrapper<StudentEvaluate> seQw = new QueryWrapper<>();
    //     // seQw.eq("user_id",currUser.getId());
    //     // List<StudentEvaluate> evaluateList = iStudentEvaluateService.list(seQw);
    //     // StudentEvaluate se = null;
    //     // if(evaluateList.size() < 1) {
    //     //     se = EvaluateUtils.getBlackStudentEvaluate(currUser.getId());
    //     //     iStudentEvaluateService.saveOrUpdate(se);
    //     // } else {
    //     //     se = evaluateList.get(0);
    //     // }
    //     QueryWrapper<Contest> qw = new QueryWrapper<>();
    //     qw.eq("student_id",currUser.getUsername());
    //     if(!ZwzNullUtils.isNull(page.getLevel())) {
    //         qw.like("level",page.getLevel());
    //     }
    //     if(!ZwzNullUtils.isNull(page.getName())) {
    //         qw.like("name",page.getName());
    //     }
    //     if(!ZwzNullUtils.isNull(page.getTitle())) {
    //         qw.like("title",page.getTitle());
    //     }
    //     List<Contest> contestList = contestService.list(qw);
    //     IPage<Contest> data = contestService.page(PageUtil.initMpPage(page),qw);
    //     System.out.println(data);
    //     return new ResultUtil<IPage<Contest>>().setData(data);
    //
    //
    //     // List<Competition> list = iCompetitionService.list(qw);
    //     // System.out.println(list);
    //     // // System.out.println(competition);
    //     // IPage<Competition> data = iCompetitionService.page(PageUtil.initMpPage(page),qw);
    //     // System.out.println(data);
    //     // return new ResultUtil<IPage<Competition>>().setData(data);
    // }

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


    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改学科竞赛")
    public Result<Competition> saveOrUpdate(Competition competition){
        if(iCompetitionService.saveOrUpdate(competition)){
            return new ResultUtil<Competition>().setData(competition);
        }
        return ResultUtil.error();
    }

    // @RequestMapping(value = "/insert", method = RequestMethod.POST)
    // @ApiOperation(value = "新增学科竞赛")
    // public Result<Competition> insert(Competition competition){
    //     // StudentEvaluate se = evaluateUtils.getMyStudentEvaluate();
    //     // competition.setEvaluateId(se.getId());
    //     StUser currUser = (StUser) SecurityUtils.getSubject().getPrincipal();
    //     competition.setEvaluateId(currUser.getId().toString());
    //     competition.setId(UUID.randomUUID().toString());
    //     iCompetitionService.saveOrUpdate(competition);
    //
    //     // calGrades(se);
    //     return new ResultUtil<Competition>().setData(competition);
    // }



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
