package com.itkun.controller;

import com.github.pagehelper.PageInfo;
import com.itkun.common.R;
import com.itkun.pojo.Book;
import com.itkun.pojo.Params;
import com.itkun.service.BookService;
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
@RequestMapping("/book")
@CrossOrigin
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 查询和分页查询
     * @param params
     * @return
     */
    @GetMapping("/search")
    public R findBySearch(Params params){
        PageInfo<Book> info = bookService.findBySearch(params);
        return R.success(info);
    }

    /**
     * 新增和修改
     * @param book
     * @return
     */
    @PostMapping
    public R save(@RequestBody Book book){
        if (book.getId() == null){
            bookService.add(book);
        }else{
            bookService.update(book);
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
        bookService.delete(id);
        return R.success();
    }

}














