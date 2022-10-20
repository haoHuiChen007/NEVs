package com.example.nevs.module.comment.emtity;

import com.example.nevs.module.user.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@Accessors(chain = true)
@Document("co_comment")
public class Comment {
    private String id;
    private String carId;
    private Integer level;
    @DBRef
    private User user;
    private String toUsername;
    private String content;
    private Date commentTime;
    private Integer like;
    private Integer isDelete;
}
