package com.desafio.dto;

import java.math.BigDecimal;
import java.util.List;

public class SimulacaoEnvelopeDTO {
    public Long idSimulacao;
    public Integer codigoProduto;
    public String descricaoProduto;
    public BigDecimal taxaJuros;
    public java.util.List<com.desafio.dto.ResultadoSimulacaoDTO> resultadosSimulacao;
}
