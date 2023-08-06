package com.project.chatterjava.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "username")
  private String username;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "firstname")
  private String firstname;

  @Column(name = "lastname")
  private String lastname;

  @OneToMany(
    mappedBy = "author",
    fetch = FetchType.LAZY,
    cascade = CascadeType.ALL
  )
  private List<Post> posts = new ArrayList<>();

  @OneToMany(
    mappedBy = "author",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Comment> comments;

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public List<Post> getPosts() {
    return posts;
  }

  public void setPosts(List<Post> posts) {
    this.posts = posts;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments() {
    this.comments = comments;
  }

  public User() {
    this.id = UUID.randomUUID().toString();
  }

  public String toString() {
    return (
      "user{" +
      "id: " +
      id +
      " " +
      "username: " +
      username +
      " " +
      "email: " +
      email +
      " " +
      "firstname: " +
      firstname +
      " " +
      "lastname: " +
      lastname
    );
  }
}
