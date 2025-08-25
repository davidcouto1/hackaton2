package com.desafio.repository;


import com.desafio.model.Simulacao;
import com.desafio.dto.SimulacaoPorProdutoDiaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface SimulacaoRepository extends JpaRepository<Simulacao, Long> {
	@Query("""
		SELECT new com.desafio.dto.SimulacaoPorProdutoDiaDTO(
			s.codigoProduto,
			s.nomeProduto,
			CAST(AVG(s.taxaJuros) AS java.math.BigDecimal),
			CAST(AVG((s.valorTotalSac + s.valorTotalPrice) / 2) AS java.math.BigDecimal),
			SUM(s.valorDesejado),
			SUM(s.valorTotalSac + s.valorTotalPrice)
		)
		FROM Simulacao s
		WHERE CAST(s.dataSimulacao AS date) = :data
		GROUP BY s.codigoProduto, s.nomeProduto
	""")
	List<SimulacaoPorProdutoDiaDTO> buscarPorProdutoEDia(@Param("data") LocalDate data);
}
