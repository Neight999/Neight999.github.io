package com.itkun.mapper;

import com.itkun.pojo.Params;
import com.itkun.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/29/11:55
 * @Description:
 */
@Repository
public interface UserMapper extends Mapper<User> {

    /**
     * 分页查询
     * @param params
     * @return
     */
    List<User> findBySearch(@Param("params") Params params);

    User findByName(@Param("name") String name);

    User findByNameAndPassword(@Param("name")String name, @Param("password")String password);
}









