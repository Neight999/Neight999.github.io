package com.itkun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itkun.common.JwtTokenUtils;
import com.itkun.exception.CustomException;
import com.itkun.mapper.UserMapper;
import com.itkun.pojo.Params;
import com.itkun.pojo.User;
import com.itkun.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询所有用户信息,开启分页查询
     * @return
     */
    @Override
    public PageInfo<User> findBySearch(Params params) {
        //开启分页查询
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        //接下来的查询会自动按照当前开启的分页设置来查询
        List<User> list = userMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    /**
     * 新增
     * @param user
     */
    @Override
    public void add(User user) {
        //1.用户名一定要有，否则不让新增（后面需要用户名登录）
        if (user.getName() == null || "".equals(user.getName())){
            throw new CustomException("用户名不能为空");
        }

        //2.进行重复性判断，同一个名字的管理员不允许重复新增：只要根据用户名去数据库查询一下就可以了
        User userAdmin = userMapper.findByName(user.getName());
        if (userAdmin != null){
            //已存在，这里提示前台不允许新增
            throw new CustomException("该用户名已存在，请勿重复添加");
        }
        if (user.getPassword() == null){
            user.setPassword("123456");
        }
        userMapper.insertSelective(user);
    }

    /**
     * 更新
     * @param user
     */
    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User login(User user) {
        //1.进行一些非空判断
        if (user.getName() == null || "".equals(user.getName())){
            throw new CustomException("用户名不能为空");
        }

        if (user.getPassword() == null || "".equals(user.getPassword())){
            throw new CustomException("密码不能为空");
        }

        //2.从数据库里面根据这个用户名和密码去查询对应的管理员信息
        User user1 = userMapper.findByNameAndPassword(user.getName(),user.getPassword());
        if (user1 == null){
            // 如果查出来没有，那说明输入的用户名或者密码有误，提示用户，不允许登录
            throw new CustomException("用户名或密码有误");
        }
        // 如果查出来了有，那说明确实有这个管理员，而且输入的用户名和密码都对
        //生成该登录用户对应的token，然后跟着user一起返回到前台
        String token = JwtTokenUtils.genToken(user1.getId().toString(), user1.getPassword());
        user1.setToken(token);
        return user1;
    }

    @Override
    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
