package com.example.nevs.module.comment.service;

import com.example.nevs.common.R;

public interface ICommentService {
    R getCommentTree(String carId);

    R getCommentById(String commentId);
}
