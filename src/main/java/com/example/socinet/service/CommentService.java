package com.example.socinet.service;

import com.example.socinet.dto.CommentDto;
import com.example.socinet.entity.Comment;
import com.example.socinet.entity.Post;
import com.example.socinet.entity.User;
import com.example.socinet.repository.CommentRepository;
import com.example.socinet.repository.PostRepository;
import com.example.socinet.repository.UserRepository;
import com.example.socinet.request.CommentRequest;
import com.example.socinet.util.Helper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepo;
    private final UserRepository userRepo;
    private final PostRepository postRepo;

    public List<CommentDto> getCommentByPostId(Long postId){
        List<Comment> comments = commentRepo.findByPost_Id(postId);
        List<CommentDto> commentsDto = new ArrayList<>();
        comments.forEach(comment -> commentsDto.add(new CommentDto(comment)));
        return commentsDto;
    }

    public CommentDto createComment(CommentRequest commentRequest) throws Exception {
        // Kiểm tra post có tồn tại
        Optional<Post> postOptional;
        if(commentRequest.getPostId() == null) throw new Exception("Post id must not null");
        else{
            postOptional = postRepo.findById(commentRequest.getPostId());
            if(postOptional.isEmpty()) throw new Exception("Post not found");
        }
        // Kiểm tra parent comment (nếu có) có tồn tại
        Optional<Comment> parentOptional = Optional.empty();
        if(commentRequest.getParentCommentId() != null){
            parentOptional = commentRepo.findById(commentRequest.getParentCommentId());
            if(parentOptional.isEmpty()) throw new Exception("Parent comment not found");
        }
        // Kiểm tra nội dung comment phải được cung cấp
        if(commentRequest.getContent() == null) throw new Exception("Comment's content must not null");
        else if(commentRequest.getContent().isEmpty()) throw new Exception("Comment's content must not blank");

        Comment comment = Comment.builder()
                .user(Helper.getAccountDetail().getUser())
                .post(postOptional.get())
                .content(commentRequest.getContent())
                .parentComment(parentOptional.isPresent() ? parentOptional.get() : null)
                .build();
        return new CommentDto(comment);
    }

}
