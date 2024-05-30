package com.student.controller;

import com.student.common.Result;
import com.student.common.model.AuditRequest;
import com.student.servie.ContestService;
import com.student.servie.ReviewService;
import com.student.vo.QueryBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lxy
 * @date 2024/5/31 0:44
 */


@RestController
@RequestMapping("/audit")
public class AuditController {

    @Autowired
    private ContestService contestService;

    @PostMapping("/getAuditList")
    public Result getAuditList(@RequestBody AuditRequest request){
        return contestService.getAuditList(request);
        // return reviewService.getReviewList(queryBox.getCurrent(),queryBox.getStReviewGrade());
    }
}
