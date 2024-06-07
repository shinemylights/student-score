package com.student.servie.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.dao.entity.Tag;
import com.student.servie.TagService;
import com.student.dao.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author 清晨
* @description 针对表【tag】的数据库操作Service实现
* @createDate 2024-06-02 09:50:28
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




