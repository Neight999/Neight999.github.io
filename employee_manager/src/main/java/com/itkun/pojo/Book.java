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
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "author")
    private String author;

    @Column(name = "press")
    private String press;

    @Column(name = "img")
    private String img;

    @Column(name = "typeId")
    private String typeId;

    @Transient
    private String typeName;

}














