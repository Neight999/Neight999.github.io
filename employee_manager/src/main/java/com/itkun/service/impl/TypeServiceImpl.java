package com.itkun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itkun.mapper.TypeMapper;
import com.itkun.pojo.Params;
import com.itkun.pojo.Type;
import com.itkun.service.TypeService;
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
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public void add(Type type) {
        typeMapper.insertSelective(type);
    }

    @Override
    public void update(Type type) {
        typeMapper.updateByPrimaryKeySelective(type);
    }

    @Override
    public PageInfo<Type> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        List<Type> list = typeMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public void delete(Long id) {
        typeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Type> findAll() {
        return typeMapper.selectAll();
    }

}













