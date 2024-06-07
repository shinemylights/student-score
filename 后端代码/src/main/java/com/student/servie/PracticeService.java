package com.student.servie;

import com.baomidou.mybatisplus.extension.service.IService;
import com.student.common.Result;
import com.student.common.model.AuditRequest;
import com.student.dao.entity.Practice;

import java.io.IOException;


/**
* @author 清晨
* @description 针对表【practice】的数据库操作Service
* @createDate 2024-06-04 23:25:19
*/
public interface PracticeService extends IService<Practice> {

    Result getAuditList(AuditRequest request);

    Result getAuditById(int id) throws IOException;

    Result approveAward(int id);

    Result rejectAward(int id);
}
