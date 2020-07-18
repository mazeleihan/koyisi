package com.example.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.example.controller.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 公众号：java思维导图
 * @since 2020-07-06
 */
@Controller
public class PostController extends BaseController {
    @GetMapping("/category/{id:\\d*}")
    public String category(@PathVariable(name="id")Long id){
        return "post/category";
    }

    @GetMapping("/post/{id:\\d*}")
    public String detail(@PathVariable(name="id")Long id){
        return "post/detail";
    }
}
