package com.mps.data;

import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author manvendrasingh
 * @since 2023-March-17
 * <p>
 * </p>
 **/
public interface ItemsRepo {
    void setupRepo();
    List<Item> getAllItems();
    Mono<Boolean> saveItem(Item item);
}
