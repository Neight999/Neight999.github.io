package com.itkun.pojo;

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
@Table(name = "audit")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "time")
    private String time;

    @Column(name = "day")
    private String day;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "status")
    private String status;

    @Column(name = "reason")
    private String reason;

    @Transient
    private String userName;
}














