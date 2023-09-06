package com.itkun.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageInfo;
import com.itkun.common.R;
import com.itkun.exception.CustomException;
import com.itkun.pojo.Params;
import com.itkun.pojo.Type;
import com.itkun.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA 2021.
 *
 * @Author: Mr Qin
 * @Date: 2023/08/29/11:00
 * @Description:
 */
@RestController
@RequestMapping("/type")
@CrossOrigin
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping
    public R findAll(){
        return R.success(typeService.findAll());
    }

    /**
     * 新增和修改
     * @param type
     * @return
     */
    @PostMapping
    public R save(@RequestBody Type type){
        if (type.getId() == null){
            typeService.add(type);
        }else{
            typeService.update(type);
        }
        return R.success();
    }


    @GetMapping("/search")
    public R findBySearch(Params params){
        PageInfo<Type> info = typeService.findBySearch(params);
        return R.success(info);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id){
        typeService.delete(id);
        return R.success();
    }

    /**
     * 批量删除
     * @param list
     * @return
     */
    @PutMapping("/delBatch")
    public R delBatch(@RequestBody List<Type> list){
        for(Type type : list){
            typeService.delete(type.getId());
        }
        return R.success();
    }

    @GetMapping("/export")
    public R export(HttpServletResponse response) throws IOException {
        //1.从数据库中查询出所有数据
        List<Type> all = typeService.findAll();
        if (CollectionUtil.isEmpty(all)){
            throw new CustomException("未找到数据");
        }
        //2.定义一个list和Map<key,value>出来，存储处理之后得数据，然后塞到list里
        List<Map<String,Object>> list = new ArrayList<>(all.size());
        //3.遍历每一条数据，然后封装到Map<key,value>里，把这个map塞到list里面
        for (Type type : all) {
            Map<String,Object> row = new HashMap<>();
            row.put("分类名称",type.getName());
            row.put("分类描述",type.getDescription());

            list.add(row);
        }
        //4.创建一个 ExcelWriter,把list数据用这个 write写 出来
        ExcelWriter wr = ExcelUtil.getWriter(true);
        wr.write(list,true);
        //5.把这个excel下载下来
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposiyion","attachment;filename=type.xlsx");
        ServletOutputStream out = response.getOutputStream();
        wr.flush(out,true);
        wr.close();
        IoUtil.close(System.out);


        return R.success();
    }

    @PostMapping("/upload")
    public R upload(MultipartFile file) throws IOException {
        List<Type> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(Type.class);
        if (!CollectionUtil.isEmpty(infoList)){
            for (Type type : infoList) {
                try {
                    typeService.add(type);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return R.success();
    }
}














