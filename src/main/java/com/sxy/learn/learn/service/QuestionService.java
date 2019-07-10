package com.sxy.learn.learn.service;

import com.sxy.learn.learn.dto.PaginationDTO;
import com.sxy.learn.learn.dto.QuestionDTO;
import com.sxy.learn.learn.mapper.QuestionMapper;
import com.sxy.learn.learn.mapper.UserMapper;
import com.sxy.learn.learn.model.Question;
import com.sxy.learn.learn.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        Integer totalPage;
        //计算总页数
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }

        if(page < 1){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage,page);
        Integer offset = size *(page - 1);


        List<Question>  questions = questionMapper.list(offset,size);



        for(Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }

    public PaginationDTO listByUser(Integer userId, Integer page, Integer size) {

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(userId);
        Integer totalPage;
        //计算总页数
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }

        if(totalPage != 0){
            if(page < 1){
                page = 1;
            }else if(page > totalPage){
                page = totalPage;
            }
        }

        Integer offset = size *(page - 1);

        paginationDTO.setPagination(totalPage,page);
        List<Question>  questions = questionMapper.listByUser(userId,offset,size);



        for(Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestions(questionDTOList);


        return paginationDTO;
    }
}
