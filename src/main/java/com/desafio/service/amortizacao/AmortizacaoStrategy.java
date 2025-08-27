package com.desafio.service.amortizacao;

import java.math.BigDecimal;
import java.util.List;

public interface AmortizacaoStrategy {
    java.util.List<com.desafio.service.amortizacao.ParcelaDTO> calcular(BigDecimal valor, int prazo, BigDecimal taxaJuros);
}
