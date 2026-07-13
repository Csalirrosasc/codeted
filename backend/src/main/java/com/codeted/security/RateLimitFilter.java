package com.codeted.security;

import com.codeted.common.TooManyRequestsException;
import com.codeted.config.AppProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final AppProperties appProperties;
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public RateLimitFilter(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestUri = request.getRequestURI();
        int limit = resolveLimit(requestUri);

        if (limit <= 0) {
            filterChain.doFilter(request, response);
            return;
        }

        String clientKey = request.getRemoteAddr() + ":" + requestUri;
        Bucket bucket = buckets.compute(clientKey, (key, existing) -> refresh(existing));
        bucket.count++;

        if (bucket.count > limit) {
            throw new TooManyRequestsException("Demasiadas solicitudes. Intenta nuevamente en unos minutos.");
        }

        filterChain.doFilter(request, response);
    }

    private int resolveLimit(String requestUri) {
        if (requestUri.endsWith("/auth/login")) {
            return appProperties.security().rateLimit().authMaxRequestsPerMinute();
        }

        if (requestUri.endsWith("/contact/requests")) {
            return appProperties.security().rateLimit().contactMaxRequestsPerMinute();
        }

        return -1;
    }

    private Bucket refresh(Bucket bucket) {
        long now = Instant.now().getEpochSecond();

        if (bucket == null || now - bucket.windowStartEpochSeconds >= 60) {
            return new Bucket(now);
        }

        return bucket;
    }

    private static final class Bucket {
        private final long windowStartEpochSeconds;
        private int count;

        private Bucket(long windowStartEpochSeconds) {
            this.windowStartEpochSeconds = windowStartEpochSeconds;
            this.count = 0;
        }
    }
}
