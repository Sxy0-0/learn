package com.sxy.learn.learn.Controller;

import com.sxy.learn.learn.dto.PaginationDTO;
import com.sxy.learn.learn.dto.QuestionDTO;
import com.sxy.learn.learn.mapper.QuestionMapper;
import com.sxy.learn.learn.mapper.UserMapper;
import com.sxy.learn.learn.model.Question;
import com.sxy.learn.learn.model.User;
import com.sxy.learn.learn.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

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
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                cookie.getName();
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }


        PaginationDTO pagination = questionService.list(page,size);
//        for(QuestionDTO questionDTO : questionList){
//            questionDTO.setDescription("reset");
//        }
        model.addAttribute("pagination",pagination);
        return "index";
    }

}
