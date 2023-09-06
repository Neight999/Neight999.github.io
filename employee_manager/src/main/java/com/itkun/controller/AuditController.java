package com.itkun.controller;

import com.github.pagehelper.PageInfo;
import com.itkun.common.R;
import com.itkun.pojo.Audit;
import com.itkun.pojo.Params;
import com.itkun.service.AuditService;
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
@RequestMapping("/audit")
@CrossOrigin
public class AuditController {

    @Autowired
    private AuditService auditService;

    /**
     * 新增和修改
     * @param audit
     * @return
     */
    @PostMapping
    public R save(@RequestBody Audit audit){
        if (audit.getId() == null){
            auditService.add(audit);
        }else{
            auditService.update(audit);
        }
        return R.success();
    }


    @GetMapping("/search")
    public R findBySearch(Params params){
        PageInfo<Audit> info = auditService.findBySearch(params);
        return R.success(info);
    }

    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id){
        auditService.delete(id);
        return R.success();
    }

}














