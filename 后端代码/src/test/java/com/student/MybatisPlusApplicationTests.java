package com.student;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.student.common.WordCloudVO;
import com.student.dao.entity.Contest;
import com.student.dao.entity.Tag;
import com.student.servie.ContestService;
import com.student.servie.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@SpringBootTest
class MybatisPlusApplicationTests {
//    @Autowired
//

    @Autowired
    private ContestService contestService;

    @Autowired
    private TagService tagService;

    @Test
    void contextLoads() {
//        BoundZSetOperations test = redisTemplate.boundZSetOps("test");
//        test.add("a",7);
//        test.add("b",9);
//        test.add("c",8);
//        test.rangeByScore(7,8).forEach(item-> System.out.println(item));
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
        }
        System.out.println(resultList);
        System.out.println(contestList);
    }

    @Test
    void test(){
/**
 * 六种时间类型的类
 * 数据库格式的时间三种格式
 */
        java.util.Date date = new java.util.Date();//年与日时分秒
        //数据库的三种类都继承了java.util.Date，在除了数据库的情况下使用
        java.sql.Date sDate = new java.sql.Date(date.getTime());//年月日
        java.sql.Time  sTime = new java.sql.Time(sDate.getTime());//时分秒
        java.sql.Timestamp sTimeStamp = new java.sql.Timestamp(sTime.getTime());//年月日时分秒毫秒

        //时间格式转换
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        //日历类是一个抽象基类
        java.util.Calendar calender = Calendar.getInstance();//

        System.out.println(date);
        System.out.println(sDate);
        System.out.println(sTime);
        System.out.println(sTimeStamp);
        System.out.println(calender);
    }

}
