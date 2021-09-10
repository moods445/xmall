package com.modds.xmall.gateway.admin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class XmallGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(XmallGatewayApplication.class, args);
    }

//    @Bean
//    public LogRequestPathGlobalFilter logrequestPathGlobalFilter() {
//        return new LogRequestPathGlobalFilter();
//    }
}
