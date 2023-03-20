package com.mps.data.impl;

import com.mps.data.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.ReactiveSession;
import org.springframework.data.cassandra.core.cql.ReactiveCqlTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author manvendrasingh
 * @since 2023-March-19
 * <p>
 * </p>
 **/
@Slf4j
@Service
public class ItemsRepoImpl implements ItemsRepo {

    private final DatabaseQueries queries;
    private final ReactiveCqlTemplate reactiveCqlTemplate;

    public ItemsRepoImpl(DatabaseQueries queries,
                          ReactiveSession reactiveSession) {
        this.queries = queries;
        this.reactiveCqlTemplate = new ReactiveCqlTemplate(reactiveSession);

    }

    @PostConstruct
    @Override
    public void setupRepo() {
        reactiveCqlTemplate.execute(queries.getCreateKeyspace()).block();
        reactiveCqlTemplate.execute(queries.getCreateTable()).block();
    }

    @Override
    public List<Item> getAllItems() {
        return null;
    }


    @Override
    public Mono<Boolean> saveItem(Item item) {
        log.debug("saveItem is called for item {} ",item);
        return reactiveCqlTemplate
                .execute(queries.getInsertTable(),
                        item.getId()+ new Double(Math.random()).intValue(),
                        item.getAlbumId(),
                        item.getTitle(),
                        item.getUrl(),
                        item.getThumbnailUrl());
    }
}
