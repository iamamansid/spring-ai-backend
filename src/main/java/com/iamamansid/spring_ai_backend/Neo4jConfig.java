package com.iamamansid.spring_ai_backend;

import org.springframework.beans.factory.annotation.Value;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Neo4jConfig {

    @Value("${neo4j.connection.uri}")
    String connectionString;

    @Value("${neo4j.connection.username}")
    String username;

    @Value("${neo4j.connection.password}")
    String password;

    @Bean
    public Driver neo4jDriver() {
        return GraphDatabase.driver(
                connectionString,
                AuthTokens.basic(username, password)
        );
    }
}

