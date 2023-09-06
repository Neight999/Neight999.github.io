package com.itkun.pojo;

import lombok.AllArgsConstructor;
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
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "phone")
    private String phone;

    @Column(name = "img")
    private String img;

    @Column(name = "role")
    private String role;

    @Transient
    private String token;
}














