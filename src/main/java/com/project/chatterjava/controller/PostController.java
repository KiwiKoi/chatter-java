package com.project.chatterjava.controller;

import com.project.chatterjava.model.Post;
import com.project.chatterjava.model.User;
import com.project.chatterjava.repository.PostRepository;
import com.project.chatterjava.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts/")
public class PostController {

  @Autowired
  PostRepository postRepository;

  @Autowired
  UserRepository userRepository;

  @GetMapping("/")
  public ResponseEntity<List<Post>> getAllPosts() {
    try {
      List<Post> posts = postRepository.findAll();
      if (posts.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(posts, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping
  public ResponseEntity<Post> createPost(
    @RequestBody Post post,
    @RequestParam String user_id
  ) {
    try {
      Optional<User> author = userRepository.findById(user_id);

      if (author.isEmpty()) {
        System.out.println("Author not found for id: " + user_id);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
      }

        post.setAuthor(author.get());
        post.setPublished(false);
        post.setCreatedAt(LocalDateTime.now());

        Post newPost = postRepository.save(post);

        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    } catch (RuntimeException e) {
      System.out.println("Error saving post: " + e.getMessage());
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> getPostById(@PathVariable("id") String postId) {
    Optional<Post> postData = postRepository.findById(postId);
    if (postData.isPresent()) {
      Post post = postData.get();
      return new ResponseEntity<>(post, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/user/{user_id}")
  public ResponseEntity<List<Post>> getPostsByUser(
    @PathVariable String user_id
  ) {
    try {
      List<Post> posts = postRepository.findByAuthorId(user_id);
      if (posts.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(posts, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
