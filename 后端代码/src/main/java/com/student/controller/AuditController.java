package com.student.controller;

import com.student.common.Result;
import com.student.common.model.AuditRequest;
import com.student.servie.ContestService;
import com.student.servie.PracticeService;
import com.student.servie.ReviewService;
import com.student.servie.SkillService;
import com.student.vo.QueryBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author lxy
 * @date 2024/5/31 0:44
 */


@RestController
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    private ContestService contestService;

    @Autowired
    private PracticeService practiceService;

    @Autowired
    private SkillService skillService;

    @PostMapping("/contest/getAuditList")
    public Result getAuditList(@RequestBody AuditRequest request){
        return contestService.getAuditList(request);
        // return reviewService.getReviewList(queryBox.getCurrent(),queryBox.getStReviewGrade());
    }

    @GetMapping("/contest/getAuditById")
    public Result getAuditList(int id) throws IOException {
        return contestService.getAuditById(id);
    }

    @GetMapping("/contest/approveAward")
    public Result approveAward(int id) {
        return contestService.approveAward(id);
    }

    @GetMapping("/contest/rejectAward")
    public Result rejectAward(int id) {
        return contestService.rejectAward(id);
    }


    //社会实践
    @PostMapping("/prac/getAuditList")
    public Result getPracAuditList(@RequestBody AuditRequest request){
        return practiceService.getAuditList(request);
        // return reviewService.getReviewList(queryBox.getCurrent(),queryBox.getStReviewGrade());
    }

    @GetMapping("/prac/getAuditById")
    public Result getPracAuditList(int id) throws IOException {
        return practiceService.getAuditById(id);
    }

    @GetMapping("/prac/approveAward")
    public Result approvePrac(int id) {
        return practiceService.approveAward(id);
    }

    @GetMapping("/prac/rejectAward")
    public Result rejectPrac(int id) {
        return practiceService.rejectAward(id);
    }


    //技能特长
    @PostMapping("/skill/getAuditList")
    public Result getSkillAuditList(@RequestBody AuditRequest request){
        return skillService.getAuditList(request);
        // return reviewService.getReviewList(queryBox.getCurrent(),queryBox.getStReviewGrade());
    }

    @GetMapping("/skill/getAuditById")
    public Result getSkillAuditList(int id) throws IOException {
        return skillService.getAuditById(id);
    }

    @GetMapping("/skill/approveAward")
    public Result approveSkill(int id) {
        return skillService.approveAward(id);
    }

    @GetMapping("/skill/rejectAward")
    public Result rejectSkill(int id) {
        return skillService.rejectAward(id);
    }
}
