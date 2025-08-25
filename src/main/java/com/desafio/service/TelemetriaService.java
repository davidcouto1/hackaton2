package com.desafio.service;

import com.desafio.model.Telemetria;
import com.desafio.repository.TelemetriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@Service
public class TelemetriaService {
    @Autowired
    private TelemetriaRepository telemetriaRepository;

    @CircuitBreaker(name = "default")
    @RateLimiter(name = "default")
    public Telemetria salvarTelemetria(Telemetria telemetria) {
        return telemetriaRepository.save(telemetria);
    }

    @CircuitBreaker(name = "default")
    @RateLimiter(name = "default")
    public List<Telemetria> listarTelemetria() {
        return telemetriaRepository.findAll();
    }
}
