package com.example.socinet.service;

import com.example.socinet.dto.PostDto;
import com.example.socinet.entity.Post;
import com.example.socinet.repository.PostRepository;
import com.example.socinet.security.AccountDetail;
import com.example.socinet.util.Helper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepo;
    private final FirebaseStorageService storageService;
    private final long MAX_IMAGE_SIZE = 3 * 1024 * 1024; // 3MB
    private final long MAX_VIDEO_SIZE = 15 * 1024 * 1024; //15MB

    public List<PostDto> getPosts(int page, int size){
        List<PostDto> postsDto = new ArrayList<>();
        postRepo.findAll().forEach(post -> postsDto.add(new PostDto(post)));
        return postsDto;
    }

    public PostDto getPost(Long id) throws Exception{
        Optional<Post> post = postRepo.findById(id);
        if(post.isEmpty()) throw new Exception("No post found");
        return new PostDto(post.get());
    }

    public List<PostDto> getPostsByUserId(Long userId) throws Exception{
        if(userId == null){
            AccountDetail accountDetail = Helper.getAccountDetail();
            userId = accountDetail.getUser().getId();
        }
        List<Post> posts = postRepo.findByUser_Id(userId);
        List<PostDto> postsDto = new ArrayList<>();
        posts.forEach(post -> postsDto.add(new PostDto(post)));
        return postsDto;
    }

    public PostDto createPost(String caption, MultipartFile image, MultipartFile video, Long sharedPostId) throws Exception{
        AccountDetail accountDetail = Helper.getAccountDetail();
        Post sharedPost = null;
        if(sharedPostId != null) {
            Optional<Post> sharedPostOptional = postRepo.findById(sharedPostId);
            if(sharedPostOptional.isEmpty()) throw new Exception("Shared post not found");
            else sharedPost = sharedPostOptional.get();
        }

        // Upload file lên firebase
        String imageUrl = "";
        String videoUrl = "";
        if(image != null && image.getSize() <= MAX_IMAGE_SIZE){
            imageUrl = storageService.upload("images", image);
        }

        if(video != null && video.getSize() <= MAX_VIDEO_SIZE){
            videoUrl = storageService.upload("videos", video);
        }

        Post post = Post.builder()
                .caption(caption)
                .user(accountDetail.getUser())
                .sharedPost(sharedPost)
                .imageUrl(imageUrl)
                .videoUrl(videoUrl)
                .isActive(true)
                .build();
        return new PostDto(postRepo.save(post));
    }

}
