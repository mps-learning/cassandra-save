package com.mps;

import com.mps.service.LoadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.time.Instant;

/**
 * Hello world!
 *
 */
@Slf4j
@SpringBootApplication
public class App implements CommandLineRunner {


    private final LoadService loadService;

    public App(LoadService loadService) {
        this.loadService = loadService;
    }


    public static void main( String[] args ){
        Instant start = Instant.now();
        SpringApplication.run(App.class).close();

        log.info("Now existing main(), Total time taken {}" ,
                Duration.between(Instant.now(),start));
    }

    @Override
    public void run(String... args)  {
        log.info("\n !! Going to call load data !! \n");
        loadService.loadData();
    }
}
