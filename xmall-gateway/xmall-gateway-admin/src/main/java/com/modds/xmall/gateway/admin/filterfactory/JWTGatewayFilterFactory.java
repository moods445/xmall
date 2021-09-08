//package com.modds.xmall.gateway.admin.filterfactory;
//
//
//import com.alibaba.fastjson.JSON;
//import com.modds.xmall.admin.domain.AdminUser;
//import com.modds.xmall.common.bean.Result;
//import com.modds.xmall.common.util.JWTUserTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.codec.CodecConfigurer;
//import org.springframework.http.codec.HttpMessageReader;
//import org.springframework.http.server.RequestPath;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//
//@Component
//@Slf4j
//public class JWTGatewayFilterFactory extends AbstractGatewayFilterFactory<JWTGatewayFilterFactory.Config> {
//
//
//    private final List<HttpMessageReader<?>> messageReaders;
//
//
//    private final AntPathMatcher pathMatcher = new AntPathMatcher();
//
//    public JWTGatewayFilterFactory(CodecConfigurer codecConfigurer) {
//        super(Config.class);
//        //this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
//        this.messageReaders = codecConfigurer.getReaders();
//    }
//
//
//    @Override
//    public List<String> shortcutFieldOrder() {
//        return Arrays.asList("include", "exclude");
//    }
//
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return ((exchange, chain) -> {
//            if (!checkExclude(config, exchange) && checkInclude(config, exchange)) {
//                HttpHeaders headers = exchange.getRequest().getHeaders();
//                String token = headers.getFirst("X-Token");
//                if (StringUtils.isEmpty(token)) {
//                    return response(exchange, HttpStatus.UNAUTHORIZED, "Unauthorized Error token is empty");
//                }
//                String uid = JWTUserTemplate.getUid(token);
//                if (StringUtils.isEmpty(uid)) {
//                    return response(exchange, HttpStatus.UNAUTHORIZED, "Unauthorized Error token error");
//                }
//                AdminUser adminUser = adminUserApi.getAdminUser(Long.parseLong(uid));
//                if (adminUser == null) {
//                    return response(exchange, HttpStatus.FORBIDDEN, "Forbidden Error user not exist");
//                }
//
//                boolean verify = JWTUserTemplate.verify(token, uid, adminUser.getName());
//                if (!verify) {
//                    return response(exchange, HttpStatus.FORBIDDEN, "Forbidden Error token is error");
//                }
//            }
//            return chain.filter(exchange);
//        });
//    }
//
//
//    private Mono<Void> response(ServerWebExchange exchange, Integer code, String message, Object data) {
//        ServerHttpResponse serverHttpResponse = exchange.getResponse();
//        serverHttpResponse.setStatusCode(HttpStatus.OK);
//        serverHttpResponse.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//        String json = JSON.toJSONString(Result.error(code, message, data));
//        byte[] response = json.getBytes(StandardCharsets.UTF_8);
//        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(response);
//        return exchange.getResponse().writeWith(Flux.just(buffer));
//    }
//
//
//    private Mono<Void> response(ServerWebExchange exchange, HttpStatus httpStatus, String message) {
//        ServerHttpResponse serverHttpResponse = exchange.getResponse();
//        serverHttpResponse.setStatusCode(httpStatus);
//        serverHttpResponse.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//        String json = JSON.toJSONString(Result.error(httpStatus.value(), message));
//        byte[] response = json.getBytes(StandardCharsets.UTF_8);
//        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(response);
//        return exchange.getResponse().writeWith(Flux.just(buffer));
//    }
//
//
//    private boolean checkInclude(Config config, ServerWebExchange exchange) {
//        String include = config.getInclude();
//        RequestPath path = exchange.getRequest().getPath();
//        List<String> paths = Stream.of(include.split(",")).collect(Collectors.toList());
//        return paths.stream().anyMatch(pattern -> pathMatcher.match(pattern, path.value()));
//    }
//
//
//    private boolean checkExclude(Config config, ServerWebExchange exchange) {
//        RequestPath path = exchange.getRequest().getPath();
//        HttpMethod method = exchange.getRequest().getMethod();
//        if (StringUtils.isNotEmpty(config.getExclude())) {
//            Map<String, String> excludeMap = Stream.of(config.getExclude().split(","))
//                    .map(s -> s.split(":"))
//                    .collect(Collectors.toMap(strings -> strings[0].replaceAll("[\\t\\n\\r]", "").trim(),
//                            strings -> strings.length == 1 ? "" : strings[1].replaceAll("[\\t\\n\\r]", "").trim(), (o, o2) -> o));
//            Optional<String> first = excludeMap.keySet().stream().filter(s -> pathMatcher.match(s, path.value())).findFirst();
//            if (first.isPresent()) {
//                String httpMethod = excludeMap.get(first.get());
//                if ("".equals(httpMethod) || httpMethod.contains(method.name())) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//
//    public static class Config {
//
//
//        private String include;
//
//        private String exclude;
//
//        public String getInclude() {
//            return include;
//        }
//
//        public void setInclude(String include) {
//            this.include = include;
//        }
//
//        public String getExclude() {
//            return exclude;
//        }
//
//        public void setExclude(String exclude) {
//            this.exclude = exclude;
//        }
//    }
//}
