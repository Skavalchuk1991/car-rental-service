package com.solvd.carrentalservice.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static volatile ConnectionPool instance;
    private final BlockingQueue<Connection> pool;

    private ConnectionPool(int size, String url, String username, String password) {
        pool = new ArrayBlockingQueue<>(size);
        try {
            for (int i = 0; i < size; i++) {
                pool.add(DriverManager.getConnection(url, username, password));
            }
            LOGGER.info("ConnectionPool initialized with {} connections", size);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize connection pool", e);
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    try {
                        Properties props = new Properties();
                        InputStream input = ConnectionPool.class.getClassLoader()
                                .getResourceAsStream("config.properties");
                        props.load(input);

                        String driver = props.getProperty("db.driver");
                        String url = props.getProperty("db.url");
                        String username = props.getProperty("db.username");
                        String password = props.getProperty("db.password");
                        int size = Integer.parseInt(props.getProperty("db.pool.size"));

                        Class.forName(driver);
                        instance = new ConnectionPool(size, url, username, password);
                    } catch (ClassNotFoundException | IOException e) {
                        throw new RuntimeException("Failed to load DB config", e);
                    }
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while getting connection", e);
        }
    }

    public void releaseConnection(Connection connection) {
        try {
            pool.put(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while releasing connection", e);
        }
    }
}
