package com.modds.xmall.gateway.admin;

import com.modds.xmall.gateway.admin.filterfactory.LogRequestPathGlobalFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class XmallGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(XmallGatewayApplication.class, args);
    }

    @Bean
    public LogRequestPathGlobalFilter logrequestPathGlobalFilter() {
        return new LogRequestPathGlobalFilter();
    }
}
