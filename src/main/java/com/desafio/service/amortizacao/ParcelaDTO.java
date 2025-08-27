package com.desafio.service.amortizacao;

import java.math.BigDecimal;

public class ParcelaDTO {
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
