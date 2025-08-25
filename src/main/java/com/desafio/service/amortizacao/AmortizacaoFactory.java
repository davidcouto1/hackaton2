package com.desafio.service.amortizacao;

import java.util.HashMap;
import java.util.Map;

public class AmortizacaoFactory {
    private static final Map<TipoAmortizacao, AmortizacaoStrategy> strategies = new HashMap<>();
    static {
        strategies.put(TipoAmortizacao.SAC, new SACStrategy());
        strategies.put(TipoAmortizacao.PRICE, new PRICEStrategy());
    }
    public static AmortizacaoStrategy getStrategy(TipoAmortizacao tipo) {
        return strategies.get(tipo);
    }
}
