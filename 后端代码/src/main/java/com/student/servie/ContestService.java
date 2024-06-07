package com.student.servie;

import com.student.common.Result;
import com.student.common.model.AuditRequest;
import com.student.dao.entity.Contest;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

/**
* @author 清晨
* @description 针对表【contest】的数据库操作Service
* @createDate 2024-05-22 23:03:46
*/
public interface ContestService extends IService<Contest> {

    Result getAuditList(AuditRequest request);

    Result getAuditById(int id) throws IOException;

    Result approveAward(int id);

    Result rejectAward(int id);

}
