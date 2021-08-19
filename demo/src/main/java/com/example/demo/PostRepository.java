package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository <Post,Integer> {
    static List<Post> findPostById(Integer id) {
        return null;
    }


}
