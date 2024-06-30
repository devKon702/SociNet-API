package com.example.socinet.repository;

import com.example.socinet.entity.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    void deleteByUser_IdAndPost_Id(Long userId, Long postId);
}
