package com.abdur.testing.controller;

import com.abdur.testing.entity.manytomany.Post;
import com.abdur.testing.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Post post){
        postService.add(post);
        return ResponseEntity.ok("Added successfully");
    }

    @GetMapping("/get")
    public ResponseEntity get(@RequestParam Long id){
        return ResponseEntity.ok(postService.get(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity getAll(){
        return ResponseEntity.ok(postService.getAll());
    }

}
