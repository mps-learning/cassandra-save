package com.mps.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mps.data.Item;
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

    private static final String JSON_PLACEHOLDER_TYPICODE_URL = "https://jsonplaceholder.typicode.com/items";
    private final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public Flux<Item> getAllItems() {
//      return getItemsFluxFromExternalApi();
        return getItemsFluxFromFile();
    }

    /**
     * Having problem in  unmarshalling the non-reactive api response
     * So mimicking it as below.
     * @return
     */
    private Flux<Item> getItemsFluxFromFile()  {
        log.info("\n Using local json file to generate Flux of Items !!\n");
        List<Item> items = new ArrayList<>();
        try {
            File file = ResourceUtils.getFile("classpath:items.json");
            JsonNode itemsNode = MAPPER.readTree(file);
            if (itemsNode.isArray()) {
                for (JsonNode itemNode : itemsNode) {
                    items.add(MAPPER.readValue(itemNode.toString(), Item.class));
                }
            }
            return Flux.fromIterable(items);
        }catch (Exception e) {
            log.error("unable to read file.", e);
            return Flux.empty();
        }
    }


    private Flux<Item> getItemsFluxFromExternalApi() {
        log.info("\nGoing to use HTTP Client !!\n");
        return HttpClient
                .create()
                .get()
                .uri(JSON_PLACEHOLDER_TYPICODE_URL)
                .responseContent()
                .asString()
                .map(e -> {
                    try {
                        log.info("\n (((( this is the value {} ))))",e);//Json not broken per item.
                        return MAPPER.readValue(e, Item.class);
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
        //                .bodyToFlux(Items.class);
    }



}
