package com.example.nevs.module.comment.controller;

import com.example.nevs.common.R;
import com.example.nevs.module.comment.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @GetMapping("/getCommentTree/{carId}")
    public R getCommentTree(@PathVariable String carId){
        return commentService.getCommentTree(carId);

    }
    @GetMapping("/getCommentById/{commentId}")
    public R getCommentById(@PathVariable String commentId){
        return commentService.getCommentById(commentId);
    }

}
