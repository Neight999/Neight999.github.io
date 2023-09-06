package com.itkun.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itkun.mapper.BookMapper;
import com.itkun.pojo.Book;
import com.itkun.pojo.Params;
import com.itkun.service.BookService;
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
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;


    @Override
    public PageInfo<Book> findBySearch(Params params) {
        //开启分页查询
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Book> list = bookMapper.findBySearch(params);
        return PageInfo.of(list);
    }

    @Override
    public void add(Book book) {
        bookMapper.insertSelective(book);
    }

    @Override
    public void update(Book book) {
        bookMapper.updateByPrimaryKeySelective(book);
    }

    @Override
    public void delete(Long id) {
        bookMapper.deleteByPrimaryKey(id);
    }
}













