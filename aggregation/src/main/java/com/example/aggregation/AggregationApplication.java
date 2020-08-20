package com.example.aggregation;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootApplication
public class AggregationApplication {

	private final String TRANSACTIONS ;
	private final String BALANCE ;


	public AggregationApplication (@Value("${transactions.url:http://knative-transactions.default.svc.cluster.local}") String trasanctions,
								   @Value("${balance.url:http://knative-balance.default.svc.cluster.local}") String balance) {
		this.TRANSACTIONS = trasanctions;
		this.BALANCE = balance;
	}



	@Bean
	public Function<String, CombinedBalance> aggregation() {
		return (lambda) -> {
			String payloadB = null;
			String payloadT = null;
			try {
				payloadB = getServiceResponse(BALANCE, lambda);
				payloadT = getServiceResponse(TRANSACTIONS, lambda);
			} catch (IOException | InterruptedException e) {
				throw new RuntimeException(e);
			}

			JSONArray jsonArray = new JSONArray(payloadT);
			List<Double> transactions = jsonArray.toList().stream().map(a -> Double.parseDouble(a.toString())).collect(Collectors.toList());
			return new CombinedBalance(Double.parseDouble(payloadB.toString()), transactions);

		};
	}

	private String getServiceResponse(String address, String lambda) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(address))
				.header("Content-Type", "application/json")
				.header("Accept", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(lambda))
				.build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return response.body();
	}

	public static void main(String[] args) {
		SpringApplication.run(AggregationApplication.class, args);
	}
}
