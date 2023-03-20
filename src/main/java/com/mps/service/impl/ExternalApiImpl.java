package com.mps.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mps.data.Photos;
import com.mps.service.ExternalApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author manvendrasingh
 * @since 2023-March-17
 * <p>
 * </p>
 **/
@Slf4j
@Service
public class ExternalApiImpl implements ExternalApi {

    private static final String JSON_PLACEHOLDER_TYPICODE_URL = "https://jsonplaceholder.typicode.com/photos";
    private final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public Flux<Photos> getAllPhotos() {
//      return getPhotosFluxFromExternalApi();
        return getPhotosFluxFromFile();
    }

    /**
     * Having problem in  unmarshalling the non-reactive api response
     * So mimicking it as below.
     * @return
     */
    private Flux<Photos> getPhotosFluxFromFile()  {
        log.info("\n Using local json file to generate Flux of Photos !!\n");
        List<Photos> photos = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile("classpath:photos.json");
            JsonNode photosNode = MAPPER.readTree(file);
            if (photosNode.isArray()) {
                for (JsonNode photoNode : photosNode) {
                    photos.add(MAPPER.readValue(photoNode.toString(), Photos.class));
                }
            }
            return Flux.fromIterable(photos);
        }catch (Exception e) {
            log.error("unable to read file.", e);
            return Flux.empty();
        }
    }


    private Flux<Photos> getPhotosFluxFromExternalApi() {
        log.info("\nGoing to use HTTP Client !!\n");
        return HttpClient
                .create()
                .get()
                .uri(JSON_PLACEHOLDER_TYPICODE_URL)
                .responseContent()
                .asString()
                .map(e -> {
                    try {
                        log.info("\n (((( this is the value {} ))))",e);//Json not broken per photo.
                        return MAPPER.readValue(e, Photos.class);
                    } catch (JsonProcessingException ex) {
                        throw new RuntimeException(ex);
                    }
                });
        // return WebClient.builder()
        //              .build().
        //              .get()
        //                .uri(JSON_PLACEHOLDER_TYPICODE_URL)
        //                .accept(MediaType.APPLICATION_JSON)
        //                .retrieve()
        //                .bodyToFlux(Photos.class);
    }



}
