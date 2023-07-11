package com.example.BankDocumentManagementSystem.util;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientMethods <T>{
    public Mono<T> post(String url, String resouce, Object object, Class<T> returnType){
        WebClient client = WebClient.create(url);
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.post();
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(resouce);
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.body(
                Mono.just(object), object.getClass());
        Mono<T> response = headersSpec.retrieve()
                .bodyToMono(returnType);

        return response;
    }
    public Mono<T> get(String url, String resouce, int postId, Class<T> returnType){
        WebClient client = WebClient.create(url);
        WebClient.RequestHeadersSpec<?> uriSpec = client.get().uri(resouce, postId);
        Mono<T> response = uriSpec.retrieve()
                .bodyToMono(returnType);

        return response;
    }
}
