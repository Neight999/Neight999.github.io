package com.itkun.service;


import com.github.pagehelper.PageInfo;
import com.itkun.pojo.Params;
import com.itkun.pojo.Type;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/29/11:53
 * @Description:
 */
public interface TypeService {

    void add(Type type);

    void update(Type type);

    PageInfo<Type> findBySearch(Params params);

    void delete(Long id);

    List<Type> findAll();

}
