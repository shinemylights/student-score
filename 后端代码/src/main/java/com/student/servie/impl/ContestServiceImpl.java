package com.student.servie.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.common.AuditVO;
import com.student.common.Result;
import com.student.common.model.AuditRequest;
import com.student.dao.entity.Contest;
import com.student.dao.entity.StReviewGrade;
import com.student.dao.entity.StUser;
import com.student.servie.ContestService;
import com.student.dao.mapper.ContestMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 清晨
* @description 针对表【contest】的数据库操作Service实现
* @createDate 2024-05-22 23:03:46
*/
@Service
public class ContestServiceImpl extends ServiceImpl<ContestMapper, Contest>
    implements ContestService{

    @Autowired
    private ContestMapper contestMapper;

    static int pageSize = 10;

    @Override
    public Result getAuditList(AuditRequest request) {
        StUser user = (StUser) SecurityUtils.getSubject().getPrincipal();
        IPage<Contest> page=new Page<>(request.getCurrent(),pageSize);

        IPage<Contest> pageResult = this.page(page,new QueryWrapper<Contest>().select("student_id","student_name"));

        // 如果需要转换 Contest 为 AuditVO，可以在获取记录后转换
        List<AuditVO> auditVOs = pageResult.getRecords().stream()
                .map(this::convertToAuditVO)
                .collect(Collectors.toList());
        // IPage<StReviewGrade> page1 = reviewGradeService.page(page);
        // return Result.succ(page1);

        System.out.println(auditVOs);

        return Result.succ(auditVOs);
    }

    /**
     * 将 Contest 实体转换为 AuditVO 对象
     * @param contest Contest 实体
     * @return AuditVO 对象
     */
    private AuditVO convertToAuditVO(Contest contest) {
        AuditVO auditVO = new AuditVO();
        // 假设 Contest 和 AuditVO 有一些共通的属性，可以使用 BeanUtils 复制属性
        BeanUtils.copyProperties(contest, auditVO);
        // 根据需要设置其他 AuditVO 特有的属性
        // auditVO.setSomeField(...);
        return auditVO;
    }
}




