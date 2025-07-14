package com.ama.don.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
public class DBTest {

    @Autowired
    DataSource dataSource;

    @GetMapping("/db-test")
    public String testDB() {
        try (Connection conn = dataSource.getConnection()) {
            return "DB 연결 완료: " + conn.getMetaData().getURL();
        } catch (Exception e) {
            return "DB 연결 실패: " + e.getMessage();
        }
    }
}
