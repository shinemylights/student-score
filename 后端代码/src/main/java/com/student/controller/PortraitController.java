package com.student.controller;

/**
 * @author lxy
 * @date 2024/4/17 22:40
 */
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.student.common.ClusteringResult;
import com.student.common.Result;
import com.student.dao.domain.Course;
import com.student.dao.domain.Score;
import com.student.dao.service.*;
import com.student.servie.CourseService;
import com.student.servie.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/portrait")
public class PortraitController {

    @Resource
    private MyCourseService myCourseService;

    @Resource
    private MyStudentService myStudentService;

    @Resource
    private ScoreService scoreService;

    @Resource
    private StudentcourseService studentcourseService;

    @Resource
    private SortService sortService;


    @GetMapping("/kmeans")
    public Result calculateFeatureAbilities(int id) {

        String line = null;
        try {
            // 假设您的Python脚本位于"F:/PycharmProjects/your_script.py"
            // 并且您要传入的课程ID是"123"
            String pythonScriptPath = "E:\\pythonprojects\\demo1\\StudentScore2.py";

            // 构建命令和参数
            String[] cmd = new String[]{"python", pythonScriptPath, Integer.valueOf(id).toString()};

            // 执行Python脚本
            Process process = Runtime.getRuntime().exec(cmd);

            // 等待脚本执行完成
            process.waitFor();

            // 读取脚本的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            line = reader.readLine();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        String[] labels = line.split(",");
        List<BigDecimal> result = Stream.of(labels).map(item -> new BigDecimal(item).setScale(2, BigDecimal.ROUND_HALF_UP)).collect(Collectors.toList());

        System.out.println("标签得分情况:"+result);
        return Result.succ(result);
    }

}

