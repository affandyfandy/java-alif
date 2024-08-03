package com.fsoft.lecture16.openfeign.client;

import com.fsoft.lecture16.openfeign.config.ClientConfiguration;
import com.fsoft.lecture16.openfeign.entity.Post;
import com.fsoft.lecture16.openfeign.fallback.JSONPlaceHolderFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "jplaceholder",
        url = "https://jsonplaceholder.typicode.com",
        configuration = ClientConfiguration.class,
        fallback = JSONPlaceHolderFallback.class)
public interface JSONPlaceHolderClient {

    @RequestMapping(method = RequestMethod.GET, value = "/posts")
    List<Post> getPosts();

    @RequestMapping(method = RequestMethod.GET, value = "/posts/{id}", produces = "application/json")
    Post getPostById(@PathVariable("id") Long id);
}
