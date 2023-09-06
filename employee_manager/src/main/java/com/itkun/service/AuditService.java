package com.itkun.service;


import com.github.pagehelper.PageInfo;
import com.itkun.pojo.Audit;
import com.itkun.pojo.Params;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/29/11:53
 * @Description:
 */
public interface AuditService {

    void add(Audit audit);

    void update(Audit audit);

    PageInfo<Audit> findBySearch(Params params);

    void delete(Long id);

}
