package com.desafio.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AmortizacaoService {
    public List<ParcelaDTO> calcularSAC(BigDecimal valor, int prazo, BigDecimal taxaJuros) {
        List<ParcelaDTO> parcelas = new ArrayList<>();
        BigDecimal amortizacao = valor.divide(BigDecimal.valueOf(prazo), 2, BigDecimal.ROUND_HALF_UP);
        for (int i = 1; i <= prazo; i++) {
            BigDecimal saldoDevedor = valor.subtract(amortizacao.multiply(BigDecimal.valueOf(i - 1)));
            BigDecimal valorJuros = saldoDevedor.multiply(taxaJuros).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal valorPrestacao = amortizacao.add(valorJuros);
            parcelas.add(new ParcelaDTO(i, amortizacao, valorJuros, valorPrestacao));
        }
        return parcelas;
    }

    public List<ParcelaDTO> calcularPRICE(BigDecimal valor, int prazo, BigDecimal taxaJuros) {
        List<ParcelaDTO> parcelas = new ArrayList<>();
        BigDecimal taxa = taxaJuros;
        BigDecimal fator = taxa.add(BigDecimal.ONE).pow(prazo);
        BigDecimal prestacao = valor.multiply(taxa.multiply(fator)).divide(fator.subtract(BigDecimal.ONE), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal saldoDevedor = valor;
        for (int i = 1; i <= prazo; i++) {
            BigDecimal valorJuros = saldoDevedor.multiply(taxa).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal valorAmortizacao = prestacao.subtract(valorJuros).setScale(2, BigDecimal.ROUND_HALF_UP);
            saldoDevedor = saldoDevedor.subtract(valorAmortizacao);
            parcelas.add(new ParcelaDTO(i, valorAmortizacao, valorJuros, prestacao));
        }
        return parcelas;
    }

    public static class ParcelaDTO {
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
