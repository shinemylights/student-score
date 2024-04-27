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
    public Result calculateFeatureAbilities(int id){
        return Result.succ(new int[]{75,80,96,78,85,70});
    }


    // @GetMapping("/kmeans")
    // public Result calculateFeatureAbilities(int id) {
    //     List<Course> courseList = myCourseService.list();
    //
    //     Map<String, BigDecimal> result = new LinkedHashMap<>();
    //     BigDecimal[] labels = new BigDecimal[7]; // Initialize label scores
    //     int[] sortNum = new int[7]; // Initialize course count for each label
    //
    //     // Initialize label scores to 5
    //     for (int i = 0; i < labels.length; i++) {
    //         labels[i] = BigDecimal.valueOf(5);
    //     }
    //
    //     // Count course numbers for each label
    //     for (Course course : courseList) {
    //         int sortId = course.getSortId(); // Get category ID for the course
    //         sortNum[sortId]++; // Increment course count for the corresponding label
    //     }
    //
    //     // Process each course
    //     for (Course course : courseList) {
    //         BigDecimal low, mid, high; // Clustering centers
    //
    //         // Retrieve or compute clustering centers (low, mid, high) for the course
    //
    //             // Call Python for K-means clustering
    //             ClusteringResult clusteringResult = callPythonForClustering(course.getId());
    //             low = clusteringResult.getLow();
    //             mid = clusteringResult.getMid();
    //             high = clusteringResult.getHigh();
    //             // Store in cache
    //
    //         QueryWrapper<Score> eq = new QueryWrapper<>();
    //         eq.eq("student_id", id).eq("course_id", course.getId());
    //
    //         BigDecimal score = new BigDecimal(scoreService.getOne(eq).getScore());
    //         int sortId = course.getSortId();
    //         BigDecimal label = labels[sortId];
    //
    //         // Update label score based on course score and clustering centers
    //         if (score.compareTo(BigDecimal.valueOf(60)) < 0) {
    //             label = label.add(BigDecimal.valueOf(-4).divide(BigDecimal.valueOf(sortNum[sortId]),3, RoundingMode.HALF_UP));
    //         } else if (score.compareTo(low) < 0) {
    //             label = label.add(BigDecimal.ONE.divide(BigDecimal.valueOf(sortNum[sortId]),3, RoundingMode.HALF_UP));
    //         } else if (score.compareTo(mid) < 0) {
    //             label = label.add(BigDecimal.valueOf(2).divide(BigDecimal.valueOf(sortNum[sortId]),3, RoundingMode.HALF_UP));
    //         } else if (score.compareTo(high) < 0) {
    //             label = label.add(BigDecimal.valueOf(3).divide(BigDecimal.valueOf(sortNum[sortId]),3, RoundingMode.HALF_UP));
    //         } else {
    //             label = label.add(BigDecimal.valueOf(4).divide(BigDecimal.valueOf(sortNum[sortId]),3, RoundingMode.HALF_UP));
    //         }
    //
    //         labels[sortId] = label; // Update label score
    //         System.out.println(labels);
    //     }
    //
    //     return Result.succ(labels);
    // }

    public ClusteringResult callPythonForClustering(int courseId){
        String line = null;
        try {
            // 假设您的Python脚本位于"F:/PycharmProjects/your_script.py"
            // 并且您要传入的课程ID是"123"
            String pythonScriptPath = "E:\\pythonprojects\\demo1\\StudentScore.py";

            // 构建命令和参数
            String[] cmd = new String[]{"python", pythonScriptPath, Integer.valueOf(courseId).toString()};

            // 执行Python脚本
            Process process = Runtime.getRuntime().exec(cmd);

            // 等待脚本执行完成
            process.waitFor();

            // 读取脚本的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            line = reader.readLine();
            // while ((line = reader.readLine()) != null) {
            //     System.out.println(line);
            // }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ClusteringResult(line);
    }


}

