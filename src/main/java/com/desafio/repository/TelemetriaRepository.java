package com.desafio.repository;

import com.desafio.model.Telemetria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelemetriaRepository extends JpaRepository<Telemetria, Long> {
}
