package com.student.redis.delay.container;

import com.alibaba.fastjson.JSON;
import com.student.redis.delay.bean.DelayJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author daify
 * @date 2019-08-08 14:07
 **/
@Component
@Slf4j
public class ReadyQueue {

    @Autowired
    private RedisTemplate redisTemplate;

    private final String NAME = "process.queue";

    private String getKey(String topic) {
        return NAME + topic;
    }

    /**
     * 获得队列
     */
    private BoundListOperations getQueue (String topic) {
        return redisTemplate.boundListOps(getKey(topic));
    }

    /**
     * 设置任务
     */
    public void pushJob(DelayJob delayJob) {
        log.info("执行队列添加任务:{}",delayJob);
        BoundListOperations listOperations = getQueue(delayJob.getTopic());
        listOperations.leftPush(delayJob);
    }

    /**
     * 移除并获得任务
     */
    public DelayJob popJob(String topic) {
        BoundListOperations listOperations = getQueue(topic);
        Object o = listOperations.leftPop();
        if (o instanceof DelayJob) {
            log.info("执行队列取出任务:{}", JSON.toJSONString((DelayJob) o));
            return (DelayJob) o;
        }
        return null;
    }

}
