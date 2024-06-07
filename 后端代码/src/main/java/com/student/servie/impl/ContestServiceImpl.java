package com.student.servie.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.common.ContestAuditDetailVO;
import com.student.common.AuditVO;
import com.student.common.Result;
import com.student.common.model.AuditRequest;
import com.student.dao.entity.Contest;
import com.student.dao.entity.StUser;
import com.student.servie.ContestService;
import com.student.dao.mapper.ContestMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
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


    //头像图片上传地址
    @Value("E:/myImages/")
    private String imgFile;

    @Autowired
    private ContestMapper contestMapper;

    static int pageSize = 10;

    @Override
    public Result getAuditList(AuditRequest request) {
        StUser user = (StUser) SecurityUtils.getSubject().getPrincipal();
        IPage<Contest> page=new Page<>(request.getCurrent(),pageSize);

        IPage<Contest> pageResult = this.page(page,new QueryWrapper<Contest>()
                .select("id","student_id","student_name","approval_status","submission_date","name"));

        // 如果需要转换 Contest 为 AuditVO，可以在获取记录后转换
        List<AuditVO> auditVOs = pageResult.getRecords().stream()
                .map(this::convertToAuditVO)
                .collect(Collectors.toList());
        // IPage<StReviewGrade> page1 = reviewGradeService.page(page);
        // return Result.succ(page1);
        IPage<AuditVO> page1 = new Page<>();
        page1.setRecords(auditVOs);
        System.out.println(auditVOs);

        return Result.succ(page1);
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

    /**
     * 根据id获取复核信息
     * @param id id
     * @return s/f
     */
    @Override
    public Result getAuditById(int id) throws IOException {

        Contest contest = this.getById(id);
        ContestAuditDetailVO auditDetailVO = new ContestAuditDetailVO();
        BeanUtils.copyProperties(contest, auditDetailVO);
        auditDetailVO.setImages(new ArrayList<>());

        String[] splitUrl = contest.getProofImageUrl().split(",");

        for (String s : splitUrl) {
            String imagePath = imgFile + s; // 根据实际情况调整路径
            File imageFile = new File(imagePath);
            byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
            String s1 = Base64.getEncoder().encodeToString(imageBytes);
            auditDetailVO.getImages().add("data:image/*;base64,"+s1);
        }

        System.out.println(auditDetailVO);

        return Result.succ(auditDetailVO);
    }

    @Override
    public Result approveAward(int id) {
        this.update().set("approval_status","1").eq("id",id).update();
        return Result.succ(1);
    }

    @Override
    public Result rejectAward(int id) {
        this.update().set("approval_status","2").eq("id",id).update();
        return Result.succ(1);
    }


}




