package com.example.socinet.dto;

import com.example.socinet.entity.Comment;
import com.example.socinet.entity.Post;
import com.example.socinet.entity.Reaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDto {
    Long id;
    UserDto user;
    String caption;
    String imageUrl;
    String videoUrl;
    Post sharedPost;
    List<CommentDto> comments;
    List<ReactionDto> reactions;
    @JsonProperty("isActive")
    boolean isActive;
    Date createdAt;
    Date updatedAt;

    public PostDto(Post post){
        this.id = post.getId();
        this.user = new UserDto(post.getUser());
        this.caption = post.getCaption();
        this.imageUrl = post.getImageUrl();
        this.videoUrl = post.getVideoUrl();
        this.sharedPost = post.getSharedPost();
        this.comments = new ArrayList<>();
        post.getComments().forEach(comment -> this.comments.add(new CommentDto(comment)));
        this.reactions = new ArrayList<>();
        post.getReactions().forEach(reaction -> this.reactions.add(new ReactionDto(reaction)));
        this.isActive = post.isActive();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
