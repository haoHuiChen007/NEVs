package com.example.nevs.module.comment.service;

import com.example.nevs.common.R;
import com.example.nevs.module.comment.emtity.Comment;

import java.util.Map;

public interface ICommentService {
    R getCommentTree(String carId);

    R getCommentById(String commentId);

    R getCommentList(Map<String,String> map);

    R insertComment(Comment comment);

    R deleteCommentById(String id);

    Comment getOneById(String id);

    R getCarScore(String carId);
}
