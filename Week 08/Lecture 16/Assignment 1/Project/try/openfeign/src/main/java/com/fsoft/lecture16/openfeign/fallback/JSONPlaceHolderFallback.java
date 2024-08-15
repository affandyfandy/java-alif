package com.fsoft.lecture16.openfeign.fallback;

import com.fsoft.lecture16.openfeign.client.JSONPlaceHolderClient;
import com.fsoft.lecture16.openfeign.entity.Post;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class JSONPlaceHolderFallback implements JSONPlaceHolderClient {

    @Override
    public List<Post> getPosts() {
        return Collections.emptyList();
    }

    @Override
    public Post getPostById(Long id) {
        return null;
    }
}
