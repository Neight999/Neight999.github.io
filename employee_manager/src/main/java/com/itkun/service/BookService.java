package com.itkun.service;

import com.github.pagehelper.PageInfo;
import com.itkun.pojo.Book;
import com.itkun.pojo.Params;
import com.itkun.pojo.User;

/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/29/11:53
 * @Description:
 */
public interface BookService {

    PageInfo<Book> findBySearch(Params params);

    void add(Book book);

    void update(Book book);

    void delete(Long id);
}
