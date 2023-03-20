package com.mps.data.impl;

import com.mps.data.*;
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
public class PhotosRepoImpl implements PhotosRepo {

    private final DatabaseQueries queries;
    private final ReactiveSession reactiveSession;
    private final ReactiveCqlTemplate reactiveCqlTemplate;

    public PhotosRepoImpl(DatabaseQueries queries,
                          ReactiveSession reactiveSession) {
        this.queries = queries;
        this.reactiveSession = reactiveSession;
        this.reactiveCqlTemplate = new ReactiveCqlTemplate(reactiveSession);
    }

    @Override
    public List<Photos> getAllPhotos() {
        return null;
    }

    @Override
    public void saveAllPhotos(List<Photos> photos) {
        verifyAndSetupDB();
    }

    @Override
    public Mono<Boolean> savePhoto(Photos photo) {
        
        verifyAndSetupDB();

        return reactiveCqlTemplate
                .execute(queries.getInsertTable(),
                        photo.getId(),
                        photo.getAlbumId(),
                        photo.getTitle(),
                        photo.getUrl(),
                        photo.getThumbnailUrl());
    }


    private void verifyAndSetupDB() {
        reactiveCqlTemplate.execute(queries.getCreateKeyspace()).block();
        reactiveCqlTemplate.execute(queries.getCreateTable()).block();
    }
}
