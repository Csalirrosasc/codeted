package com.codeted.health;

import com.codeted.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    private final JdbcTemplate jdbcTemplate;

    public HealthController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Map<String, String>> health() {
        jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        return ApiResponse.success("Servicio saludable", Map.of(
                "status", "UP",
                "database", "UP"
        ));
    }
}
