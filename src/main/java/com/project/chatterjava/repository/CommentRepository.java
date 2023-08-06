package com.project.chatterjava.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.chatterjava.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAll();
    List<Comment> findById(String id);
    // List<Comment> findByUserId(String user_id);
    // List<Comment> findByPostId(String post_id);
    Comment deleteById(String id);
}
