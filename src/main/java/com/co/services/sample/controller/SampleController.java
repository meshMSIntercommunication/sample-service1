package com.co.services.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class SampleController {
	@GetMapping("/")
	public Mono<String> sample1() {
		WebClient webClient = WebClient.create();
		Mono<String> ms1 = webClient.get().uri("http://sample-service2-v2.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class);
		Mono<String> ms2 = webClient.get().uri("http://sample-service3-v1.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class);
// 		Mono<String> ms3 = webClient.get().uri("http://localhost:9004/").retrieve().bodyToMono(String.class);
// 		Mono<String> ms4 = webClient.get().uri("http://localhost:9005/").retrieve().bodyToMono(String.class);
// 		Mono<String> ms5 = webClient.get().uri("http://localhost:9006/").retrieve().bodyToMono(String.class);
// 		Mono<String> ms6 = webClient.get().uri("http://localhost:9007/").retrieve().bodyToMono(String.class);
// 		Mono<String> ms7 = webClient.get().uri("http://localhost:9008/").retrieve().bodyToMono(String.class);
// 		Mono<String> ms8 = webClient.get().uri("http://localhost:9009/").retrieve().bodyToMono(String.class);
		return Mono.zip(ms1, ms2).flatMap(data -> {
			return Mono.just("" + data.getT1() + "<br/>" + data.getT2() + "<br/>");
		});
// 		return Mono.zip(ms1, ms2, ms3, ms4, ms5, ms6, ms7, ms8).flatMap(data -> {
// 			return Mono.just("" + data.getT1() + "<br/>" + data.getT2() + "<br/>" + data.getT3() + "<br/>" + data.getT4() + "<br/>" + data.getT5() + "<br/>" + data.getT6() + "<br/>" + data.getT7() + "<br/>" + data.getT8());
// 		});
	}

}
