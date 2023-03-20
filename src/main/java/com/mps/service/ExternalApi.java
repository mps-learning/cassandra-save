package com.mps.service;

import com.mps.data.Item;
import reactor.core.publisher.Flux;

/**
 * @author manvendrasingh
 * @since 2023-March-17
 * <p>
 * </p>
 **/
public interface ExternalApi {
    Flux<Item> getAllItems();
}
