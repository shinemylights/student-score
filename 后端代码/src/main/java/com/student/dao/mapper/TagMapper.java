package com.student.dao.mapper;

import com.student.dao.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 清晨
* @description 针对表【tag】的数据库操作Mapper
* @createDate 2024-06-02 09:50:28
* @Entity com.student.dao.entity.Tag
*/
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}




