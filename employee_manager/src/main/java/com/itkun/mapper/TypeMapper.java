package com.itkun.mapper;

import com.itkun.pojo.Params;
import com.itkun.pojo.Type;
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
public interface TypeMapper extends Mapper<Type> {

    List<Type> findBySearch(@Param("params") Params params);
}









