package at.jku.client;

import at.jku.restservice.HandlebarConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

public class HandlebarConfigClient {
    public static void main(String[] args) {
        final Scanner in = new Scanner(System.in);
        final ObjectMapper objectMapper = new ObjectMapper();
        final HttpClient client = HttpClient.newHttpClient();
        while (true) {
            System.out.println("Choose type of handlebar. Available values are:");
            try {
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/getAvailableHandlebarTypes")).build();
                HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
                objectMapper.readValue(response.body(), List.class).forEach(System.out::println);
                System.out.println("Input >");
                final String handlebarType = in.nextLine();

                System.out.println("Choose type of material. Available values are:");
                request = HttpRequest.newBuilder().uri(URI.create(
                    "http://localhost:8080/getAvailableMaterial?handlebarType=" + handlebarType))
                    .build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                objectMapper.readValue(response.body(), List.class).forEach(System.out::println);
                System.out.println("Input >");
                final String handlebarMaterial = in.nextLine();

                System.out.println("Choose type of gearshift. Available values are:");
                request = HttpRequest.newBuilder().uri(URI.create(
                    "http://localhost:8080/getAvailableGearshifts?handlebarMaterial="
                        + handlebarMaterial)).build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                objectMapper.readValue(response.body(), List.class).forEach(System.out::println);
                System.out.println("Input >");
                final String handlebarGearshift = in.nextLine();

                System.out.println("Choose type of handle material. Available values are:");
                request = HttpRequest.newBuilder().uri(URI.create(
                    "http://localhost:8080/getAvailableHandleMaterial?handlebarType="
                        + handlebarMaterial + "&handlebarMaterial=" + handlebarMaterial)).build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                objectMapper.readValue(response.body(), List.class).forEach(System.out::println);
                System.out.println("Input >");
                final String handleMaterial = in.nextLine();

                final String uri = MessageFormat
                    .format("http://localhost:8080/order/{0}/{1}/{2}/{3}", handlebarType,
                        handlebarMaterial, handlebarGearshift, handleMaterial);
                request = HttpRequest.newBuilder().uri(URI.create(uri))
                    .POST(HttpRequest.BodyPublishers.ofString("")).build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                final String body = response.body();
                if (response.statusCode() == 200) {
                    final HandlebarConfig handlebarConfig =
                        objectMapper.readValue(body, HandlebarConfig.class);
                    System.out.println("Result > " + handlebarConfig);
                } else {
                    if (body != null && !body.isEmpty()) {
                        System.out.println("Something went wrong > " + body);
                    } else {
                        System.out.println("Something went wrong > " + response.toString());
                    }
                }
                System.out.println();
            } catch (final IOException e) {
                e.printStackTrace();
                break;
            } catch (final InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
