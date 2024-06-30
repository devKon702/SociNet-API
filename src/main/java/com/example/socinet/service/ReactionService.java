package com.example.socinet.service;

import com.example.socinet.dto.ReactionDto;
import com.example.socinet.entity.Post;
import com.example.socinet.entity.React;
import com.example.socinet.entity.Reaction;
import com.example.socinet.repository.PostRepository;
import com.example.socinet.repository.ReactRepository;
import com.example.socinet.repository.ReactionRepository;
import com.example.socinet.request.ReactionRequest;
import com.example.socinet.util.Helper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ReactionService {
    private final ReactionRepository reactionRepo;
    private final PostRepository postRepsitory;
    private final ReactRepository reactRepo;

    public ReactionDto createReaction(ReactionRequest reactionRequest) throws Exception {
        Optional<Post> postOptional = Optional.empty();
        if(reactionRequest.getPostId() == null) throw new Exception("Post ID must not null");
        else{
            postOptional = postRepsitory.findById(reactionRequest.getPostId());
            if(postOptional.isEmpty()) throw new Exception("Post not found");
        }
        Optional<React> reactOptional = Optional.empty();
        if(reactionRequest.getType() == null) throw new Exception("Reaction type must not null");
        else{
            reactOptional = reactRepo.findById(reactionRequest.getType());
            if(reactOptional.isEmpty()) throw new Exception("Reaction's type not found");
        }

        Reaction reaction = Reaction.builder()
                .user(Helper.getAccountDetail().getUser())
                .post(postOptional.get())
                .react(reactOptional.get())
                .build();
        return new ReactionDto(reaction);
    }

    public void deleteReaction(Long postId){
        reactionRepo.deleteByUser_IdAndPost_Id(Helper.getAccountDetail().getUser().getId(), postId);
    }
}
