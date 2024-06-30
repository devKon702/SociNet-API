package com.example.socinet.dto;

import com.example.socinet.entity.Comment;
import com.example.socinet.entity.Post;
import com.example.socinet.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {
    Long id;
    User user;
    Post post;
    String content;
    Comment parentComment;
    List<Comment> childComments;
    Date createdAt;
    Date updatedAt;

    public CommentDto(Comment comment){
        this.id = comment.getId();
        this.user = comment.getUser();
        this.post = comment.getPost();
        this.content = comment.getContent();
        this.parentComment = comment.getParentComment();
        this.childComments = comment.getChildComments();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
