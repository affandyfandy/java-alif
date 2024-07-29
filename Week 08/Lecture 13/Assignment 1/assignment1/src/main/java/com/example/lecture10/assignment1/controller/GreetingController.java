package com.example.lecture10.assignment1.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class GreetingController {

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @GetMapping(path = "/greeting")
    public DeferredResult<String> greeting(HttpServletResponse response) {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        executorService.submit(() -> perform(deferredResult));
        return deferredResult;
    }

    private void perform(DeferredResult<String> deferredResult) {
        deferredResult.setResult("Hello, World!");
    }
}

