package com.sxy.learn.learn.Controller;

import com.sxy.learn.learn.mapper.QuestionMapper;
import com.sxy.learn.learn.mapper.UserMapper;
import com.sxy.learn.learn.model.Question;
import com.sxy.learn.learn.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public  String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
    @RequestParam(value = "title",required = false) String title,
    @RequestParam("description") String description,
    @RequestParam(value = "tag",required = false) String tag,
    HttpServletRequest request,
    Model model
    ){
        Question question = new Question();
        question.setTitle(title);
        System.out.println(title);
        question.setDescription(description);
        System.out.println(description);
        question.setTag(tag);
        System.out.println(tag);

        User user = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            cookie.getName();
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                if (user != null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }

        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";
    }
}
