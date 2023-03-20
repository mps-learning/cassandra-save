package com.mps.service.impl;

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
        log.info("\n going to call external Api to get All photos. \n");

        externalApi
                .getAllPhotos()
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(photosRepo::savePhoto)
                .doOnNext(photo->{log.info("save the photo {}, total saved {}",photo.getId(),savedPhotos.incrementAndGet());}) //save photo return
                .doOnError(th -> log.error(" error occurred in saving", th))
                .blockLast();
    }
}
