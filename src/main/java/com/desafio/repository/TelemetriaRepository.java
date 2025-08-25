package com.desafio.repository;


import com.desafio.model.Telemetria;
import com.desafio.dto.TelemetriaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface TelemetriaRepository extends JpaRepository<Telemetria, Long> {
	@Query("""
		SELECT new com.desafio.dto.TelemetriaDTO(
			t.nomeApi,
			COUNT(t.idTelemetria),
			AVG(t.tempoRespostaMs),
			MIN(t.tempoRespostaMs),
			MAX(t.tempoRespostaMs),
			SUM(CASE WHEN t.statusHttp = 200 THEN 1 ELSE 0 END) * 1.0 / COUNT(t.idTelemetria)
		)
		FROM Telemetria t
		WHERE t.dataReferencia = :data
		GROUP BY t.nomeApi
	""")
	List<TelemetriaDTO> buscarPorData(@Param("data") LocalDate data);
}
