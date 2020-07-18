package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Category;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller

public class IndexController {
    @Autowired
    CategoryService categoryService;


    @RequestMapping({"","/","index"})
    public String index(){
        return "index";
    }

    @RequestMapping("/tt")
    @ResponseBody
    public List<Category> tt(){
        return   categoryService.list(new QueryWrapper<Category>()
                .eq("status", 0)
        );
    }
}
