package com.co.services.sample.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class SampleController {
	final Boolean runParallel = true;
	@GetMapping("/")
	public String sample1() {
		return this.callInterMs(runParallel);

	}

	@GetMapping("/responseTime")
	public String sample2() {
		
		long totalRuntime = 0L;
		long startTime2= 0L;
		int noRuns = 1000;
		long endTime2 = 0L;
		for(int i=0; i<noRuns; i++){
			startTime2 = System.currentTimeMillis();
			this.callInterMs(!runParallel);
			endTime2 = System.currentTimeMillis(); // Get the end Time sequential
			totalRuntime += endTime2 - startTime2;
		}
	
		double avgRuntime = ((double) totalRuntime/noRuns)*100; 
		return new String("\n Response time(1k runs average): "
				+ (avgRuntime) + "ms"); // Print the difference in mili seconds

	}

	public String callInterMs(boolean runParallel) {
			
			WebClient webClient = WebClient.create();
			if (runParallel) {
				Mono<String> ms1 = webClient.get().uri("sample-service2-v2.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class);
				Mono<String> ms2 = webClient.get().uri("sample-service3-v1.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class);
				Mono<String> ms3 = webClient.get().uri("sample-service4.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class);
				Mono<String> ms4 = webClient.get().uri("sample-service5.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class);
				Mono<String> ms5 = webClient.get().uri("sample-service6.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class);
				Mono<String> ms6 = webClient.get().uri("sample-service7.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class);
				Mono<String> ms7 = webClient.get().uri("sample-service8.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class);
				Mono<String> ms8 = webClient.get().uri("sample-service9.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class);
			
				Mono<String> result = Mono.zip(ms1, ms2, ms3, ms4, ms5, ms6, ms7, ms8).flatMap(data -> {
					return Mono.just("" + data.getT1() + "<br/>" + data.getT2() + "<br/>" + data.getT3() + "<br/>" + data.getT4() + "<br/>" + data.getT5() + "<br/>" + data.getT6() + "<br/>" + data.getT7() + "<br/>" + data.getT8());
				});

				return result.block();
			}

			String ms1 = webClient.get().uri("sample-service2-v2.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class).block();
			String ms2 = webClient.get().uri("sample-service3-v1.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class).block();
			String ms3 = webClient.get().uri("sample-service4.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class).block();
			String ms4 = webClient.get().uri("sample-service5.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class).block();
			String ms5 = webClient.get().uri("sample-service6.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class).block();
			String ms6 = webClient.get().uri("sample-service7.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class).block();
			String ms7 = webClient.get().uri("sample-service8.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class).block();
			String ms8 = webClient.get().uri("sample-service9.charles-mesh.svc.cluster.local:9000/").retrieve().bodyToMono(String.class).block();

			return ms1 + "<br/>" + ms2 + "<br/>" + ms3 + "<br/>" + ms4 + "<br/>" + ms5 + "<br/>" + ms6 + "<br/>" + ms7 + "<br/>" + ms8;
		}

}
