package com.desafio.service.amortizacao;

import com.desafio.service.amortizacao.ParcelaDTO;


import org.springframework.stereotype.Component;
import java.math.RoundingMode;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class PRICEStrategy implements AmortizacaoStrategy {
    @Override
    public List<ParcelaDTO> calcular(BigDecimal valor, int prazo, BigDecimal taxaJuros) {
        List<ParcelaDTO> parcelas = new ArrayList<>();
        BigDecimal taxa = taxaJuros;
        BigDecimal fator = taxa.add(BigDecimal.ONE).pow(prazo);
    BigDecimal prestacao = valor.multiply(taxa.multiply(fator)).divide(fator.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
        BigDecimal saldoDevedor = valor;
        for (int i = 1; i <= prazo; i++) {
            BigDecimal valorJuros = saldoDevedor.multiply(taxa).setScale(2, RoundingMode.HALF_UP);
            BigDecimal valorAmortizacao = prestacao.subtract(valorJuros).setScale(2, RoundingMode.HALF_UP);
            saldoDevedor = saldoDevedor.subtract(valorAmortizacao);
            parcelas.add(new ParcelaDTO(i, valorAmortizacao, valorJuros, prestacao));
        }
        return parcelas;
    }
}
