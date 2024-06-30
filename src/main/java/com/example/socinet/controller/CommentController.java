package com.example.socinet.controller;

import com.example.socinet.dto.CommentDto;
import com.example.socinet.entity.Comment;
import com.example.socinet.request.CommentRequest;
import com.example.socinet.service.CommentService;
import com.example.socinet.util.Helper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/post/{id}")
    public ResponseEntity<?> getCommentByPostId(@PathVariable Long id){
        List<CommentDto> comments = commentService.getCommentByPostId(id);
        return Helper.returnSuccessResponse("Get post's comments success", comments);
    }

    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody CommentRequest commentRequest) throws Exception{
        CommentDto comment = commentService.createComment(commentRequest);
        return Helper.returnSuccessResponse("Create comment success", comment);
    }
}
