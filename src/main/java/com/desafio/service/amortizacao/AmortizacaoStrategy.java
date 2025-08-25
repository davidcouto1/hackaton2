package com.desafio.service.amortizacao;

import java.math.BigDecimal;
import java.util.List;

public interface AmortizacaoStrategy {
    List<ParcelaDTO> calcular(BigDecimal valor, int prazo, BigDecimal taxaJuros);

    class ParcelaDTO {
        public int numero;
        public BigDecimal valorAmortizacao;
        public BigDecimal valorJuros;
        public BigDecimal valorPrestacao;
        public ParcelaDTO(int numero, BigDecimal valorAmortizacao, BigDecimal valorJuros, BigDecimal valorPrestacao) {
            this.numero = numero;
            this.valorAmortizacao = valorAmortizacao;
            this.valorJuros = valorJuros;
            this.valorPrestacao = valorPrestacao;
        }
    }
}
