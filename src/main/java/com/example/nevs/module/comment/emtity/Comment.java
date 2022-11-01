package com.example.nevs.module.comment.emtity;

import com.example.nevs.module.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@Accessors(chain = true)
@Document("co_comment")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private String id;
    private String carId;
    private Integer level;
    private String parentId;
    @DBRef
    private User user;
    private String toUserId;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date commentTime;
    private Integer like;
}
