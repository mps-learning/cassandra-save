package com.mps;

import com.mps.service.LoadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("\n !! Going to call load data !! \n");
        loadService.loadData();
    }
}
