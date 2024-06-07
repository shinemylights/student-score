package com.student.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.common.Result;
import com.student.common.TagListRequest;
import com.student.dao.entity.StUser;
import com.student.dao.entity.Tag;
import com.student.servie.TagService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lxy
 * @date 2024/6/2 9:47
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    static int pageSize = 10;

    @PostMapping("/getTagList")
    public Result getAuditList(@RequestBody TagListRequest request){
        IPage<Tag> page=new Page<>(request.getCurrent(),pageSize);
        IPage<Tag> pageResult = tagService.page(page);

        return Result.succ(pageResult);
        // return contestService.getAuditList(request);
    }

    @PostMapping("/saveOrUpdateTag")
    public Result saveOrUpdateTag(@RequestBody Tag tag){
        boolean isSuccess = tagService.saveOrUpdate(tag);
        return Result.succ(isSuccess);
    }


}
