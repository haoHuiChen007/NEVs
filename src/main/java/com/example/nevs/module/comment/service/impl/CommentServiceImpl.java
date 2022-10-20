package com.example.nevs.module.comment.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import com.example.nevs.common.R;
import com.example.nevs.module.comment.emtity.Comment;
import com.example.nevs.module.comment.service.ICommentService;
import com.example.nevs.util.ITreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public R getCommentTree(String carId) {
        List<Comment> comments = getCommentList(carId);
        List<Tree<String>> tree = ITreeUtil.getTree(comments, 5, "0", CollUtil.newArrayList("isDelete", "level"));
        return R.success(tree);
    }

    @Override
    public R getCommentById(String commentId) {
        List<String> ids = new ArrayList<>();
        ids.add(commentId);
        List<Comment> comments = getChildCommentById(ids);
        List<Tree<String>> tree = ITreeUtil.getTree(comments, null, commentId, CollUtil.newArrayList("isDelete", "level"));
        return R.success(tree);
    }
    public List<Comment> getChildCommentById(List<String> ids){
        Query query = new Query();
        query.addCriteria(Criteria.where("parentId").in(ids));
        List<Comment> comments = mongoTemplate.find(query, Comment.class);
        if (CollectionUtil.isNotEmpty(comments)){
            List<String> childIds = comments.stream().map(Comment::getId).collect(Collectors.toList());
            comments.addAll(getChildCommentById(childIds));
        }return comments;
    }
    public List<Comment> getCommentList(String carId){
        Query query = new Query();
        query.addCriteria(Criteria.where("carId").is(carId).and("isDelete").is(0));
        return mongoTemplate.find(query, Comment.class);
    }
}
