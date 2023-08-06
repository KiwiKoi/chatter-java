package com.project.chatterjava.controller;

import com.project.chatterjava.model.Post;
import com.project.chatterjava.model.User;
import com.project.chatterjava.repository.PostRepository;
import com.project.chatterjava.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @PostMapping("/{authorId}")
  public ResponseEntity<Post> createPost(
    @RequestBody Post post,
    @PathVariable String authorId
  ) {
    try {
      System.out.println("authorId: " + authorId);

      Optional<User> author = userRepository.findById(authorId);
      if (!author.isPresent()) {
        System.out.println("Author not found for id: " + authorId);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
      }

      if (author.isPresent()) {
        post.setAuthor(author.get());
        post.setPublished(false);

        System.out.println("post: " + post);
        Post newPost = postRepository.save(post);
        System.out.println("newPost: " + newPost);

        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
      } else {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
      }
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

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Post>> getPostsByUser(
    @PathVariable String userId
  ) {
    try {
      List<Post> posts = postRepository.findByAuthorId(userId);
      if (posts.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(posts, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
