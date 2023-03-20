package com.mps.data;

import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author manvendrasingh
 * @since 2023-March-17
 * <p>
 * </p>
 **/
public interface PhotosRepo {
    List<Photos> getAllPhotos();
    void saveAllPhotos(List<Photos> photos);
    Mono<Boolean> savePhoto(Photos photo);
}
