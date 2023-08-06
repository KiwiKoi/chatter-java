package com.project.chatterjava.controller;

import com.project.chatterjava.model.User;
import com.project.chatterjava.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @PersistenceContext
  private EntityManager entityManager;

  @Autowired
  UserRepository userRepository;

  @GetMapping("/all")
  public ResponseEntity<List<User>> getAllusers() {
    try {
      List<User> users = userRepository.findAll();
      if (users.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(users, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // @GetMapping("/{id}")
  // public User getUserById(@PathVariable String id) {
  //   Optional<User> user = entityManager
  //     .createQuery("SELECT u FROM User u WHERE u.id = :id", User.class)
  //     .setParameter("id", id)
  //     .getResultList()
  //     .stream()
  //     .findFirst();
  //   return user.orElse(null);
  // }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable String id) {
    try {
      Optional<User> userData = userRepository.findById(id);
      if (userData.isPresent()) {
        return new ResponseEntity<>(userData.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(path="/create/{userId}")
  public ResponseEntity<User> createUser(
    @RequestBody User user,
    @PathVariable String userId
  ) {
    try {
      System.out.println("userId: " + userId);
      System.out.println("userId: " + user);
      user.setId(userId);
      user.setEmail(user.getEmail());
      User newUser = userRepository.save(user);

      return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    } catch (RuntimeException e) {
      System.out.println("Error saving user: " + e.getMessage());

      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // @PutMapping("/update/{userId}")
  // public ResponseEntity<User> updateUser(
  //   @RequestBody User user,
  //   @PathVariable String userId
  // ){
  //   try {

  //   }
  // }

}
