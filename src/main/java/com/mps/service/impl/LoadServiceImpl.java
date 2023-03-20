package com.mps.service.impl;

import com.mps.data.Item;
import com.mps.data.ItemsRepo;
import com.mps.service.ExternalApi;
import com.mps.service.LoadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.Instant;
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
    private final ItemsRepo itemsRepo;
    private final AtomicInteger savedItemsCount = new AtomicInteger(0);
    private final AtomicInteger errorItemsCount = new AtomicInteger(0);

    public LoadServiceImpl(ExternalApi externalApi,
                           ItemsRepo itemsRepo) {
        this.externalApi = externalApi;
        this.itemsRepo = itemsRepo;
    }

    @Override
    public void loadData() {
        Instant start = Instant.now();
        log.info("\n Load data called. \n");

        externalApi
                .getAllItems()
                .subscribeOn(Schedulers.newParallel("kk",1))
                .flatMap(item -> itemsRepo.saveItem(item)
                        .doOnNext(isSuccess -> this.logSaveResult(item, isSuccess))
                        .doOnError(th -> this.logSaveError(item, th))
                ).subscribe();

        log.info("\n existing loadService:loadData, total time taken {}\n",
                Duration.between(start,Instant.now()));
    }

    private void logSaveResult(Item item, Boolean isSuccess) {
        if (isSuccess) {
            logSaveSuccess(item);
        } else {
            logSaveError(item, null);
        }
    }

    private void logSaveError(Item item, Throwable th) {
        log.error(" error occurred in saving {}, exception is {}", item, th.getMessage());
        errorItemsCount.incrementAndGet();
    }

    private void logSaveSuccess(Item item) {
        log.info("saved the item {}, total saved {}",
                item.getId(),
                savedItemsCount.incrementAndGet());
    }
}
