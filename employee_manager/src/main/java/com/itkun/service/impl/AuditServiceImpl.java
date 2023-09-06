package com.itkun.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itkun.common.JwtTokenUtils;
import com.itkun.exception.CustomException;
import com.itkun.mapper.AuditMapper;
import com.itkun.mapper.UserMapper;
import com.itkun.pojo.Audit;
import com.itkun.pojo.Params;
import com.itkun.pojo.User;
import com.itkun.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/29/11:54
 * @Description:
 */
@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    private AuditMapper auditMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(Audit audit) {
        auditMapper.insertSelective(audit);
    }

    @Override
    public void update(Audit audit) {
        auditMapper.updateByPrimaryKeySelective(audit);
    }

    @Override
    public PageInfo<Audit> findBySearch(Params params) {

        User user = JwtTokenUtils.getCurrentUser();
        if (ObjectUtil.isNull(user)){
            throw new CustomException("从token中为解析到用户信息，请重新登录");
        }
        if ("ROLE_STUDENT".equals(user.getRole())){
            params.setUserId(user.getId());
        }
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Audit> list = auditMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public void delete(Long id) {
        auditMapper.deleteByPrimaryKey(id);
    }

}













