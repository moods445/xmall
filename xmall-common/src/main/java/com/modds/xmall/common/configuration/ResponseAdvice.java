package com.modds.xmall.common.configuration;

import com.modds.xmall.common.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;


/**
 * 在控制器方法返回之后和响应写入之前，
 * 新的生命周期选项可用于拦截@ResponseBody和ResponseEntity方法。
 * 为了利用优势声明一个@ControllerAdvice实现的bean ResponseBodyAdvice。
 * 对@JsonViewJSONP的内置支持利用了这一点。请参见第17.4.1节“使用HandlerInterceptor拦截请求”。
 */
@RestControllerAdvice
@Slf4j
@ConditionalOnClass({ResponseBodyAdvice.class, HttpServletRequest.class})
public class ResponseAdvice implements ResponseBodyAdvice {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    @Value("${api.result.package.path:/api/}")
    private String path;
    @Value("${api.result.package.exclude:}")
    private List<String> exclude;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        HttpServletRequest currentHttpRequest = getCurrentHttpRequest();
        String requestURI = currentHttpRequest.getRequestURI();
        if (requestURI.startsWith(path)) {
            return true;
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        String path = serverHttpRequest.getURI().getPath();
        String method = serverHttpRequest.getMethod().name();
        boolean matchExclude = exclude.stream().anyMatch(s -> pathMatcher.match(s, path));
        if (body instanceof Result || !mediaType.isCompatibleWith(MediaType.APPLICATION_JSON) || matchExclude) {
            log.debug("API Response: Path={};Method={} Data={}", path, method, body);
            return body;
        }
        if (body != null && (ClassUtils.isPrimitiveOrWrapper(body.getClass()) || body instanceof Collection<?> || body instanceof String)) {
            Result result = Result.success(body);
            log.debug("API Response: Path={};Method={} Data={}", path, method, result);
            return result;
        }
        Result<Object> result = Result.success(body);
        log.debug("API Response: Path={};Method={} Data={}", path, method, result);
        return result;
    }

    public HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return request;
        }
        return null;
    }
}
