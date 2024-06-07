package com.student.controller;

import com.student.common.WenjuanScoresVO;
import com.student.dao.entity.Result;
import com.student.dao.entity.StUser;
import com.student.dao.entity.Wenjuan;
import com.student.servie.WenjuanService;
import com.student.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lxy
 * @date 2024/6/2 10:45
 */
@Slf4j
@RestController
@RequestMapping("/wenjuan")
@Transactional
public class WenJuanController {

    @Autowired
    private WenjuanService wenjuanService;

    // @RequestMapping(value = "/getOneByType", method = RequestMethod.GET)
    // @ApiOperation(value = "根据类型查询学生的单个问卷")
    // public Result<List<String>> getOneByType(@RequestParam int type){
    //     StudentEvaluate se = evaluateUtils.getMyStudentEvaluate();
    //     QueryWrapper<Questionnaire> qw = new QueryWrapper<>();
    //     qw.eq("evaluate_id",se.getId());
    //     qw.eq("type",type);
    //     List<Questionnaire> questionnaireList = iQuestionnaireService.list(qw);
    //     if(questionnaireList.size() < 1) {
    //         return new ResultUtil<List<String>>().setData(new ArrayList<>());
    //     }
    //     return new ResultUtil<List<String>>().setData(StrToListUtils.getListByStr(questionnaireList.get(0).getValue()));
    // }

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ApiOperation(value = "保存单个问卷")
    public Result<Object> save(@RequestParam int type, @RequestParam String value){
        StUser currUser = (StUser) SecurityUtils.getSubject().getPrincipal();
        Wenjuan wenjuan = new Wenjuan();
        wenjuan.setStudentId(currUser.getUsername());
        wenjuan.setStudentName(currUser.getRealName());
        wenjuan.setType(type);
        wenjuan.setValue(value);
        wenjuan.setCreateTime(new Date());


        List<Integer> collect = Arrays.asList(value.split(",")).stream().map(t -> new Integer(t)).collect(Collectors.toList());

        int sum=0;
        for (Integer item : collect) {
            sum+=item;
        }
        wenjuan.setScore(sum);

        wenjuanService.save(wenjuan);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/listScores", method = RequestMethod.GET)
    public Result<List<WenjuanScoresVO>> getMyStudentEvaluate(
            @RequestParam(required = false,defaultValue = "") String username,
            @RequestParam(required = false,defaultValue = "0") Float grades1,
            @RequestParam(required = false,defaultValue = "100") Float grades2){

        List<Wenjuan> list = wenjuanService.list();
        Map<String, List<Wenjuan>> collect = list.stream().collect(Collectors.groupingBy(Wenjuan::getStudentId));

        ArrayList<WenjuanScoresVO> result = new ArrayList<>();
        for (List<Wenjuan> value : collect.values()) {
            WenjuanScoresVO vo = new WenjuanScoresVO();
            vo.setStudentId(value.get(0).getStudentId());
            vo.setStudentName(value.get(0).getStudentName());
            int sum=0;
            for (Wenjuan wenjuan : value) {
                if (wenjuan.getType()==1){
                    vo.setXyScore(wenjuan.getScore());
                }else if (wenjuan.getType()==2){
                    vo.setPdScore(wenjuan.getScore());
                }else {
                    vo.setSxScore(wenjuan.getScore());
                }
                sum+=wenjuan.getScore();
            }
            vo.setAverage(new BigDecimal(sum/value.size()).setScale(2));
            result.add(vo);
        }

        return new ResultUtil<List<WenjuanScoresVO>>().setData(result);
    }

    // @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    // @ApiOperation(value = "查询单条问卷")
    // public Result<Questionnaire> get(@RequestParam String id){
    //     return new ResultUtil<Questionnaire>().setData(iQuestionnaireService.getById(id));
    // }

    // @RequestMapping(value = "/count", method = RequestMethod.GET)
    // @ApiOperation(value = "查询全部问卷个数")
    // public Result<Long> getCount(){
    //     return new ResultUtil<Long>().setData(iQuestionnaireService.count());
    // }
    //
    // @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    // @ApiOperation(value = "查询全部问卷")
    // public Result<List<Questionnaire>> getAll(){
    //     return new ResultUtil<List<Questionnaire>>().setData(iQuestionnaireService.list());
    // }
    //
    // @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    // @ApiOperation(value = "查询问卷")
    // public Result<IPage<Questionnaire>> getByPage(@ModelAttribute Questionnaire questionnaire , @ModelAttribute PageVo page){
    //     QueryWrapper<Questionnaire> qw = new QueryWrapper<>();
    //     if(!ZwzNullUtils.isNull(questionnaire.getCreateBy())) {
    //     }
    //     IPage<Questionnaire> data = iQuestionnaireService.page(PageUtil.initMpPage(page),qw);
    //     return new ResultUtil<IPage<Questionnaire>>().setData(data);
    // }
    //
    // @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    // @ApiOperation(value = "增改问卷")
    // public Result<Questionnaire> saveOrUpdate(Questionnaire questionnaire){
    //     if(iQuestionnaireService.saveOrUpdate(questionnaire)){
    //         return new ResultUtil<Questionnaire>().setData(questionnaire);
    //     }
    //     return ResultUtil.error();
    // }
    //
    // @RequestMapping(value = "/insert", method = RequestMethod.POST)
    // @ApiOperation(value = "新增问卷")
    // public Result<Questionnaire> insert(Questionnaire questionnaire){
    //     iQuestionnaireService.saveOrUpdate(questionnaire);
    //     return new ResultUtil<Questionnaire>().setData(questionnaire);
    // }
    //
    // @RequestMapping(value = "/update", method = RequestMethod.POST)
    // @ApiOperation(value = "编辑问卷")
    // public Result<Questionnaire> update(Questionnaire questionnaire){
    //     iQuestionnaireService.saveOrUpdate(questionnaire);
    //     return new ResultUtil<Questionnaire>().setData(questionnaire);
    // }
    //
    // @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    // @ApiOperation(value = "删除问卷")
    // public Result<Object> delByIds(@RequestParam String[] ids){
    //     for(String id : ids){
    //         iQuestionnaireService.removeById(id);
    //     }
    //     return ResultUtil.success();
    // }
}

