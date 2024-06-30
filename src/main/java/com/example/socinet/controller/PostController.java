package com.example.socinet.controller;

import com.example.socinet.dto.PostDto;
import com.example.socinet.entity.Post;
import com.example.socinet.response.Response;
import com.example.socinet.service.PostService;
import com.google.api.Http;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public ResponseEntity<?> getPosts(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size){
        Response response = Response.builder()
                .isSuccess(true)
                .message("Get post list success")
                .data(postService.getPosts(page, size))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) throws Exception{
        PostDto postDto = postService.getPost(id);
        Response response = Response.builder()
                .isSuccess(true)
                .message("Get post success")
                .data(postDto)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<?> getPostByUser(@PathVariable(required = false) Long id) throws Exception{
        List<PostDto> posts = postService.getPostsByUserId(id);
        Response response = Response.builder()
                .isSuccess(true)
                .message("Get posts success")
                .data(posts)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("")
    public ResponseEntity<?> createPost(@RequestParam String caption,
                                        @RequestParam(required = false) MultipartFile image,
                                        @RequestParam(required = false) MultipartFile video,
                                        @RequestParam(required = false) Long sharedPostId) throws Exception {
        Response response = Response.builder()
                .isSuccess(true)
                .message("Create post success")
                .data(postService.createPost(caption, image,video,sharedPostId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
