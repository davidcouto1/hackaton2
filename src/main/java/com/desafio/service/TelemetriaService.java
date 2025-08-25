package com.desafio.service;

import com.desafio.model.Telemetria;
import com.desafio.repository.TelemetriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TelemetriaService {
    private static final Logger logger = LoggerFactory.getLogger(TelemetriaService.class);
    @Autowired
    private TelemetriaRepository telemetriaRepository;

    @CircuitBreaker(name = "default")
    @RateLimiter(name = "default")
    public Telemetria salvarTelemetria(Telemetria telemetria) {
        logger.info("[AUDITORIA] Salvando telemetria: {}", telemetria);
        // Salva registro individual
        return telemetriaRepository.save(telemetria);
    }

    @CircuitBreaker(name = "default")
    @RateLimiter(name = "default")
    public List<Telemetria> listarTelemetria() {
        logger.info("[AUDITORIA] Listando telemetrias");
        return telemetriaRepository.findAll();
    }

    @CircuitBreaker(name = "default")
    @RateLimiter(name = "default")
    public List<com.desafio.dto.TelemetriaDTO> buscarPorData(java.time.LocalDate data) {
        logger.info("[AUDITORIA] Listando telemetria agregada por data: {}", data);
        return telemetriaRepository.buscarPorData(data);
    }

    // Opcional: método para buscar todos agrupados (caso precise para o controller)
    public List<com.desafio.dto.TelemetriaDTO> buscarTodosAgrupados() {
        return telemetriaRepository.buscarAgregadoSemData();
    }
}
