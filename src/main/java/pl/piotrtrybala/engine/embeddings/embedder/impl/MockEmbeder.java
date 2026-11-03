package pl.piotrtrybala.engine.embeddings.embedder.impl;

import com.google.gson.Gson;
import pl.piotrtrybala.engine.embeddings.embedder.Embedder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class MockEmbeder implements Embedder {

    public static class MockEmbedderResponse {
        public String data;
    }

    private final HttpClient client;
    private final Gson gson;
    private static final String EMBEDDING_API_URL = "https://embedder.toolkit23.com/v1/embed";

    public MockEmbeder() {
        this.client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
        this.gson = new Gson();
    }

    @Override
    public float[] create(byte[] data) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(EMBEDDING_API_URL))
                .header("Content-Type", "application/octet-stream")
                .POST(HttpRequest.BodyPublishers.ofByteArray(data))
                .build();

        CompletableFuture<MockEmbedderResponse> futureResponse =
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .thenApply(response -> {
                            MockEmbedderResponse embedderResponse = null;
                            try {
                                embedderResponse = gson.fromJson(response.body(), MockEmbedderResponse.class);
                            } catch (Exception e) {
                                embedderResponse = new MockEmbedderResponse();
                                embedderResponse.data = response.body();
                            }

                            return embedderResponse;
                        });

        futureResponse.join();
        return null;
    }
}