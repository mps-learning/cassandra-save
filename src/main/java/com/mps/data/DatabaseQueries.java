package com.mps.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author manvendrasingh
 * @since 2023-March-19
 * <p>
 * </p>
 **/
@Data
@Configuration
@ConfigurationProperties("data.load.cassandra.cql")
public class DatabaseQueries {
    String createKeyspace;
    String createTable;
    String insertTable;
}
