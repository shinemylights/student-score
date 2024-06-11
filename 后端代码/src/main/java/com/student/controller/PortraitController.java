package com.student.controller;

/**
 * @author lxy
 * @date 2024/4/17 22:40
 */
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.student.common.ClusteringResult;
import com.student.common.Result;
import com.student.common.WordCloudVO;
import com.student.dao.domain.Course;
import com.student.dao.domain.Score;
import com.student.dao.entity.Contest;
import com.student.dao.entity.Practice;
import com.student.dao.entity.Tag;
import com.student.dao.service.*;
import com.student.servie.*;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/portrait")
public class PortraitController {

    @Autowired
    private ContestService contestService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PracticeService practiceService;

    @Autowired
    private SkillService skillService;

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
        Random random = new Random();
        int randomNumber = random.nextInt(190) + 1; // 下界为1，上界为190
        id = randomNumber;
        String line = null;
        try {
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


    @GetMapping("/wordCloud")
    public Result getWordCloud(int id) {
        ArrayList<WordCloudVO> resultList = new ArrayList<>();
        List<Contest> contestList = contestService.list(new QueryWrapper<Contest>().eq("student_id", "311909020308"));
        Map<String, List<Contest>> collect = contestList.stream().collect(Collectors.groupingBy(Contest::getCategory));
        for (List<Contest> value : collect.values()) {
            WordCloudVO wordCloudVO = new WordCloudVO();
            int sum=0;
            for (Contest item : value) {
                String level = item.getLevel();
                String title = item.getTitle();
                String category = item.getCategory();

                if ("国家级".equals(level)){
                    sum+=30;
                }else if("省级".equals(level)){
                    sum+=20;
                }else{
                    sum+=10;
                }

                if("一等奖".equals(title)){
                    sum+=6;
                }else if ("一等奖".equals(title)){
                    sum+=4;
                }else if ("一等奖".equals(title)){
                    sum+=2;
                }

                Tag tag = tagService.getOne(new QueryWrapper<Tag>().eq("category", category));
                if (sum>=30&&sum<60){
                    wordCloudVO.setName(tag.getBasic());
                    wordCloudVO.setValue("10");
                }else if (sum>=60&&sum<90){
                    wordCloudVO.setName(tag.getMiddle());
                    wordCloudVO.setValue("20");
                }else if (sum>=90){
                    wordCloudVO.setName(tag.getAdvanced());
                    wordCloudVO.setValue("30");
                }

            }


            resultList.add(wordCloudVO);
        };

        return Result.succ(resultList);
    }


}

