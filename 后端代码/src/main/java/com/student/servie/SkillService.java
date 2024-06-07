package com.student.servie;

import com.student.common.Result;
import com.student.common.model.AuditRequest;
import com.student.dao.entity.Skill;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

/**
* @author 清晨
* @description 针对表【skill】的数据库操作Service
* @createDate 2024-06-05 01:46:37
*/
public interface SkillService extends IService<Skill> {

    Result getAuditList(AuditRequest request);

    Result getAuditById(int id) throws IOException;

    Result approveAward(int id);

    Result rejectAward(int id);
}
