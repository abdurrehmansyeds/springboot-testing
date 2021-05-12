package com.abdur.testing.service.impl;

import com.abdur.testing.entity.manytomany.Post;
import com.abdur.testing.repository.PostRepository;
import com.abdur.testing.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void add(Post post) {
        postRepository.save(post);
    }

    @Override
    public Optional<Post> get(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }
}
