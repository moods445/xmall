package com.modds.xmall.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@SpringCloudApplication
public class XMallOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(XMallOrderApplication.class, args);
    }
}
