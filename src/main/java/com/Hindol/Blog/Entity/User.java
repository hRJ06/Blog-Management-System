package com.Hindol.Blog.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users") // You can use Table Annotation in order to change Entity Name
@NoArgsConstructor
@Getter
@Setter
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false) // You can use Column Annotation in order to change Column Name
    private String name;
    private String password;
    private String email;
    private String about;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();
}