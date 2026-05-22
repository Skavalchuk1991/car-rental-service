package com.solvd.carrentalservice.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisSessionFactory {

    private static final Logger LOGGER = LogManager.getLogger(MyBatisSessionFactory.class);
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            LOGGER.info("MyBatis SqlSessionFactory initialized");
        } catch (IOException e) {
            LOGGER.error("Failed to initialize MyBatis: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize MyBatis", e);
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
