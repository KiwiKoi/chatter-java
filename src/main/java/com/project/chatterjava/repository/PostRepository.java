package com.project.chatterjava.repository;

import com.project.chatterjava.model.Post;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
  Optional<Post> findById(String id);
  List<Post> findByAuthorId(String id);
}
