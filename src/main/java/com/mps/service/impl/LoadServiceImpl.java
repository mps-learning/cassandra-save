package com.mps.service.impl;

import com.mps.data.Photos;
import com.mps.data.PhotosRepo;
import com.mps.service.ExternalApi;
import com.mps.service.LoadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author manvendrasingh
 * @since 2023-March-17
 * <p>
 * </p>
 **/
@Slf4j
@Service
public class LoadServiceImpl implements LoadService {
    private final ExternalApi externalApi;
    private final PhotosRepo photosRepo;
    private final AtomicInteger savedPhotos=new AtomicInteger(0);
    private final AtomicInteger errorPhotos=new AtomicInteger(0);

    public LoadServiceImpl(ExternalApi externalApi,
                           PhotosRepo photosRepo) {
        this.externalApi = externalApi;
        this.photosRepo = photosRepo;
    }

    @Override
    public void loadData() {
        log.info("\n Load data called. \n");

        externalApi
                .getAllPhotos()
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(photosRepo::savePhoto)
                .doOnNext(this::logSaveSuccess) //save photo returns boolean then how this chain
                .doOnError(LoadServiceImpl::logSaveError)
                .blockLast();
    }

    private static void logSaveError(Throwable th) {
        log.error(" error occurred in saving",
                th);
    }

    private void logSaveSuccess(Photos photo) {
        log.info("saved the photo {}, total saved {}",
                photo.getId(),
                savedPhotos.incrementAndGet());
    }
}
