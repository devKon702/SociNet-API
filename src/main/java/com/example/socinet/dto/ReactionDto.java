package com.example.socinet.dto;

import com.example.socinet.entity.Post;
import com.example.socinet.entity.React;
import com.example.socinet.entity.Reaction;
import com.example.socinet.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.parameters.P;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReactionDto {
    PostDto post;
    String reactType;

    public ReactionDto(Reaction reaction){
        this.post = new PostDto(reaction.getPost());
        this.reactType = reaction.getReact().getType();
    }
}
