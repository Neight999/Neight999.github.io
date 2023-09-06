package com.itkun.service;

import com.github.pagehelper.PageInfo;
import com.itkun.pojo.Params;
import com.itkun.pojo.User;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/29/11:53
 * @Description:
 */
public interface UserService {
    /**
     * 查询所有用户信息
     * @return
     */

    PageInfo<User> findBySearch(Params params);

    /**
     * 新增
     * @param user
     */
    void add(User user);

    /**
     * 更新
     * @param user
     */
    void update(User user);

    void delete(Long id);

    User login(User user);

    User findById(Integer id);
}
