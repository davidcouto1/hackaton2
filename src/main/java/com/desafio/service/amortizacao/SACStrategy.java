package com.desafio.service.amortizacao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SACStrategy implements AmortizacaoStrategy {
    @Override
    public List<ParcelaDTO> calcular(BigDecimal valor, int prazo, BigDecimal taxaJuros) {
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
}
