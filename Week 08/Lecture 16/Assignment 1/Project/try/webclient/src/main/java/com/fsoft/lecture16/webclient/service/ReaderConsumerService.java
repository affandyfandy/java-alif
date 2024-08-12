package com.fsoft.lecture16.webclient.service;

import com.fsoft.lecture16.webclient.model.Book;
import com.fsoft.lecture16.webclient.model.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ReaderConsumerService {
    private final WebClient webClient;

    @Autowired
    public ReaderConsumerService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Book> getFavoriteBooks() {
        Mono<List<Reader>> response = webClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Reader>>() {});

        List<Reader> readers = response.block();
        return readers.stream()
                .map(Reader::getFavoriteBook)
                .toList();
    }
}
