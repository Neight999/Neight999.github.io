package com.itkun.pojo;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/29/11:08
 * @Description:
 */
@Data
@Table(name = "type")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Alias("分类名称")
    @Column(name = "name")
    private String name;

    @Alias("分类描述")
    @Column(name = "description")
    private String description;


}














