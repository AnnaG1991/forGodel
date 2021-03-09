package com.godeltech.gorodetskaya.task.config;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@PropertySource("classpath:db.properties")
public class DatabaseConnector {

    @Value("${db.driver}")
    private String driverName;

    @Value("${db.url}")
    private String databaseURL;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.init}")
    private String initDbSql;

    @Value("${db.keeper.opened}")
    private String keeper;

    private Server server;

    @Value("${server.tcp}")
    private String tcp;

    @Value("${server.tcpAllowOthers}")
    private String tcpAllowOthers;

    @Value("${server.tcpPort}")
    private String tcpPort;

    @Value("${server.port}")
    private String port;

    @PostConstruct
    public void init() {
        try (Connection connection = DriverManager.getConnection(databaseURL + keeper + initDbSql, username, password)) {
            Class.forName(driverName);
            server = Server.createTcpServer(tcp, tcpAllowOthers, tcpPort, port).start();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(databaseURL + keeper, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @PreDestroy
    public void destroy() {
        server.stop();
    }
}