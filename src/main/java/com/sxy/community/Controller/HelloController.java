package com.sxy.community.Controller;

import com.sxy.community.dto.PaginationDTO;
import com.sxy.community.mapper.UserMapper;
import com.sxy.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {

//    @GetMapping("/index")
//    public String hello(@RequestParam(name = "name") String name, Model model){
//        model.addAttribute("name",name);
//        return "index";
//    }

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    //进入主页
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size){


        PaginationDTO pagination = questionService.list(page,size);
//        for(QuestionDTO questionDTO : questionList){
//            questionDTO.setDescription("reset");
//        }
        model.addAttribute("pagination",pagination);
        return "index";
    }

}
