package com.itkun.pojo;

import lombok.Data;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/29/15:29
 * @Description:
 */
@Data
public class Params {

    private String name;
    private String phone;
    private Integer pageNum;
    private Integer pageSize;
    private String author;
    private Long userId;
}
