package com.itkun.controller;

import com.github.pagehelper.PageInfo;
import com.itkun.common.R;
import com.itkun.pojo.Params;
import com.itkun.pojo.User;
import com.itkun.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/29/11:00
 * @Description:
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody User user){
        User loginUser = userService.login(user);
        return R.success(loginUser);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public R register(@RequestBody User user){
        userService.add(user);
        return R.success();
    }

    /**
     * 查询和分页查询
     * @param params
     * @return
     */
    @GetMapping("/search")
    public R findBySearch(Params params){
        log.info("拦截器已放行，正式调用接口内部，查询管理员信息");
        PageInfo<User> info = userService.findBySearch(params);
        return R.success(info);
    }

    /**
     * 新增和修改
     * @param user
     * @return
     */
    @PostMapping
    public R save(@RequestBody User user){
        if (user.getId() == null){
            userService.add(user);
        }else{
            userService.update(user);
        }
        return R.success();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id){
        userService.delete(id);
        return R.success();
    }


}














