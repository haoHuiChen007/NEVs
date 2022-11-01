package com.example.nevs.module.comment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.StrUtil;
import com.example.nevs.common.E;
import com.example.nevs.common.PageResult;
import com.example.nevs.common.R;
import com.example.nevs.module.comment.emtity.Comment;
import com.example.nevs.module.comment.service.ICommentService;
import com.example.nevs.module.user.entity.User;
import com.example.nevs.module.user.service.IAdminService;
import com.example.nevs.util.IPageHelper;
import com.example.nevs.util.ITreeUtil;
import com.example.nevs.util.NlpUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IPageHelper pageHelper;
    @Autowired
    private IAdminService adminService;
    @Override
    public R getCommentTree(String carId) {
        List<Comment> comments = getCommentList(carId);
        List<Tree<String>> tree = ITreeUtil.getTree(comments, 5, "0");
        return R.success(tree);
    }

    @Override
    public R getCommentById(String commentId) {
        List<String> ids = new ArrayList<>();
        ids.add(commentId);
        List<Comment> comments = getChildCommentById(ids);
        List<Tree<String>> tree = ITreeUtil.getTree(comments, null, commentId, CollUtil.newArrayList("isDelete"));
        return R.success(tree);
    }

    @Override
    public R getCommentList( Map<String,String> map) {
        Query query = getCommentQuery(map);
        query.with(Sort.by(CollUtil.newArrayList(new Sort.Order(Sort.Direction.DESC, "_id"))));
        PageResult<Comment> commentPageResult = pageHelper.pageQuery(query, Comment.class, Integer.valueOf(map.get("size")), Integer.valueOf(map.get("page")));
        return R.success(commentPageResult);
    }

    @Override
    public R insertComment(Comment comment) {
        if (StrUtil.isBlank(comment.getContent())){
            return R.error(E.COMMENT_NOT_EMPTY);
        }
        if (StrUtil.isBlank(comment.getParentId())){
            comment.setParentId("0").setLevel(0);
        }else {
            comment.setLevel(comment.getLevel()+1);
        }
        String replay ;
        comment.setCommentTime(new Date());
        Comment insert = mongoTemplate.insert(comment);
        if (BeanUtil.isNotEmpty(insert)){
            String s = NlpUtil.textEmotion(comment.getContent());
            if (s.equals("1")){
                replay="感谢您的积极评论";
            }else if (s.equals("0")){
                replay="您的评论为消极评论请三思而后评";
            }else
                replay= "感谢您中肯的评论";
            return R.success(replay);
        }
        return R.error(E.INSERT_ERROR);
    }

    @Override
    public R deleteCommentById(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Comment andRemove = mongoTemplate.findAndRemove(query, Comment.class);
        if (BeanUtil.isNotEmpty(andRemove)){
            return R.success();
        }
        return R.error(E.DELETE_ERROR);
    }

    @Override
    public Comment getOneById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("carId").is(id));
        List<Comment> comments = mongoTemplate.find(query, Comment.class);
        if (comments.size() > 0){
            return comments.get(0);
        }
        return null;
    }

    @Override
    public R getCarScore(String carId) {
        List<Comment> comments = getCommentList(carId);
        int positive=0,negative=0,neutral=0;
        double score = 0;
        for (Comment comment : comments) {
            String s = NlpUtil.textEmotion(comment.getContent());
            if (s.equals("1")){
                positive++;
            }else if (s.equals("0")){
                negative++;
            }else{
                neutral++;
            }
            score = (0.8*neutral +positive)/(neutral+positive+negative)*5;
        }
        return R.success(score);
    }

    @SneakyThrows
    private Query getCommentQuery(Map<String,String> map){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String userId = map.get("userId");
        String startTime = map.get("startTime");
        String endTime = map.get("endTime");
        String username = map.get("username");
        Query query = new Query();
        if (StrUtil.isNotBlank(username)){
            User userByUsername = adminService.getUserByUsername(username);
            if (BeanUtil.isNotEmpty(userByUsername)){
                String id = userByUsername.getId();
                query.addCriteria(Criteria.where("userId").is(id));
            }else {
                query.addCriteria(Criteria.where("userId").is("0"));
            }
        }
        if (StrUtil.isNotBlank(userId)){
            query.addCriteria(Criteria.where("userId").is(userId));
        }if (StrUtil.isNotBlank(startTime)){
            query.addCriteria(Criteria.where("commentTime").gte(format.parse(startTime)));
        }if (StrUtil.isNotBlank(endTime)){
            query.addCriteria(Criteria.where("commentTime").lte(format.parse(endTime)));
        }
        return query;
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
        query.addCriteria(Criteria.where("carId").is(carId));
        return mongoTemplate.find(query, Comment.class);
    }

}
