package com.abdur.testing.service;

import com.abdur.testing.entity.manytomany.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    void add(Post post);

    Optional<Post> get(Long id);

    List<Post> getAll();
}
