package com.example.blog.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class DatabaseUtil {

//    @Value("${spring.datasource.url}")
    private String url = "jdbc:postgresql://localhost:5432/blog-system";

//    @Value("${spring.datasource.username}")
    private String user = "postgres";

//    @Value("${spring.datasource.password}")
    private String password = "postgres";

   
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
       
    
    }
    
        
   
    }