package com.mps.service;

import com.mps.data.Photos;
import reactor.core.publisher.Flux;

/**
 * @author manvendrasingh
 * @since 2023-March-17
 * <p>
 * </p>
 **/
public interface ExternalApi {
    Flux<Photos> getAllPhotos();
}
