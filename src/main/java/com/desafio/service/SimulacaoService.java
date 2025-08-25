package com.desafio.service;

import com.desafio.model.Simulacao;
import com.desafio.model.Parcela;
import com.desafio.repository.SimulacaoRepository;
import com.desafio.repository.ParcelaRepository;
import com.desafio.service.AmortizacaoService;
import com.desafio.service.ProdutoService;
import com.desafio.service.ProdutoService.ProdutoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

import java.util.List;

@Service
public class SimulacaoService {
    @Autowired
    private SimulacaoRepository simulacaoRepository;
    @Autowired
    private ParcelaRepository parcelaRepository;

    @Autowired
    private AmortizacaoService amortizacaoService;
    @Autowired
    private ProdutoService produtoService;


    @Transactional
    @CircuitBreaker(name = "default")
    @RateLimiter(name = "default")
    public Simulacao salvarSimulacao(Simulacao simulacao) {
        // Buscar produto válido
        ProdutoDTO produtoValido = null;
        try {
            for (ProdutoDTO produto : produtoService.buscarProdutos()) {
                boolean prazoOk = simulacao.getPrazo() >= produto.getMinimoMeses() && (produto.getMaximoMeses() == null || simulacao.getPrazo() <= produto.getMaximoMeses());
                boolean valorOk = simulacao.getValorDesejado().compareTo(produto.getValorMinimo()) >= 0 && (produto.getValorMaximo() == null || simulacao.getValorDesejado().compareTo(produto.getValorMaximo()) <= 0);
                if (prazoOk && valorOk) {
                    produtoValido = produto;
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar produtos", e);
        }
        if (produtoValido == null) throw new RuntimeException("Nenhum produto válido encontrado para os parâmetros informados.");

        simulacao.setCodigoProduto(produtoValido.getCodigo());
        simulacao.setNomeProduto(produtoValido.getNome());
        simulacao.setTaxaJuros(produtoValido.getTaxaJuros());

        // Calcular SAC
        var sacParcelas = amortizacaoService.calcularSAC(simulacao.getValorDesejado(), simulacao.getPrazo(), produtoValido.getTaxaJuros());
        // Calcular PRICE
        var priceParcelas = amortizacaoService.calcularPRICE(simulacao.getValorDesejado(), simulacao.getPrazo(), produtoValido.getTaxaJuros());

        List<Parcela> parcelas = new ArrayList<>();
        BigDecimal totalSac = BigDecimal.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (AmortizacaoService.ParcelaDTO p : sacParcelas) {
            Parcela parcela = new Parcela();
            parcela.setTipo("SAC");
            parcela.setNumero(p.numero);
            parcela.setValorAmortizacao(p.valorAmortizacao);
            parcela.setValorJuros(p.valorJuros);
            parcela.setValorPrestacao(p.valorPrestacao);
            parcela.setSimulacao(simulacao);
            parcelas.add(parcela);
            totalSac = totalSac.add(p.valorPrestacao);
        }
        for (AmortizacaoService.ParcelaDTO p : priceParcelas) {
            Parcela parcela = new Parcela();
            parcela.setTipo("PRICE");
            parcela.setNumero(p.numero);
            parcela.setValorAmortizacao(p.valorAmortizacao);
            parcela.setValorJuros(p.valorJuros);
            parcela.setValorPrestacao(p.valorPrestacao);
            parcela.setSimulacao(simulacao);
            parcelas.add(parcela);
            totalPrice = totalPrice.add(p.valorPrestacao);
        }
        simulacao.setParcelas(parcelas);
        simulacao.setValorTotalSac(totalSac);
        simulacao.setValorTotalPrice(totalPrice);

        Simulacao saved = simulacaoRepository.save(simulacao);
        for (Parcela parcela : parcelas) {
            parcela.setSimulacao(saved);
            parcelaRepository.save(parcela);
        }
        return saved;
    }

    public List<Simulacao> listarSimulacoes() {
        return simulacaoRepository.findAll();
    }
}
