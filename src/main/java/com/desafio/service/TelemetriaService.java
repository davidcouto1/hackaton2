package com.desafio.service;

import com.desafio.model.Telemetria;
import com.desafio.repository.TelemetriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelemetriaService {
    @Autowired
    private TelemetriaRepository telemetriaRepository;

    public Telemetria salvarTelemetria(Telemetria telemetria) {
        return telemetriaRepository.save(telemetria);
    }

    public List<Telemetria> listarTelemetria() {
        return telemetriaRepository.findAll();
    }
}
