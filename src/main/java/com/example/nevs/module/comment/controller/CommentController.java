package com.example.nevs.module.comment.controller;

import com.example.nevs.common.R;
import com.example.nevs.module.comment.emtity.Comment;
import com.example.nevs.module.comment.service.ICommentService;
import com.example.nevs.module.user.entity.User;
import com.example.nevs.module.user.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {


    @Autowired
    private ICommentService commentService;
    @Autowired
    private IAdminService adminService;


    @GetMapping("/getCommentTree")
    public R getCommentTree(@RequestParam String carId){
        return commentService.getCommentTree(carId);
    }

    @GetMapping("/getCommentById/{commentId}")
    public R getCommentById(@PathVariable String commentId){
        return commentService.getCommentById(commentId);
    }

    @PostMapping("/getCommentList")
    public R getCommentList( @RequestBody Map<String,String> map){
        return commentService.getCommentList(map);
    }
    @PostMapping("/insertComment")
    public R insertComment(@RequestBody Comment comment, HttpServletRequest request){
        Map<String, String> map = adminService.jwtPayload(request);
        User user = adminService.getAdminByUserId(map.get("id"));
        comment.setUser(user);
        return commentService.insertComment(comment);
    }
    @GetMapping("/deleteCommentById")
    public R deleteCommentById(@RequestParam String id){
        return commentService.deleteCommentById(id);
    }

    @GetMapping("/getCarScore")
    public R getCarScore(@RequestParam String carId){
        return commentService.getCarScore(carId);

    }


}
