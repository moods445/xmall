package com.modds.xmall.gateway.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class XMallGatewayApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(XMallGatewayApiApplication.class, args);
    }
}
