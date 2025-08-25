package com.desafio.dto;

import java.math.BigDecimal;

public class SimulacaoPorProdutoDiaDTO {
    private int codigoProduto;
    private String descricaoProduto;
    private BigDecimal taxaMediaJuro;
    private BigDecimal valorMedioPrestacao;
    private BigDecimal valorTotalDesejado;
    private BigDecimal valorTotalCredito;

    public SimulacaoPorProdutoDiaDTO(int codigoProduto, String descricaoProduto, BigDecimal taxaMediaJuro, BigDecimal valorMedioPrestacao, BigDecimal valorTotalDesejado, BigDecimal valorTotalCredito) {
        this.codigoProduto = codigoProduto;
        this.descricaoProduto = descricaoProduto;
        this.taxaMediaJuro = taxaMediaJuro;
        this.valorMedioPrestacao = valorMedioPrestacao;
        this.valorTotalDesejado = valorTotalDesejado;
        this.valorTotalCredito = valorTotalCredito;
    }

    public int getCodigoProduto() { return codigoProduto; }
    public String getDescricaoProduto() { return descricaoProduto; }
    public BigDecimal getTaxaMediaJuro() { return taxaMediaJuro; }
    public BigDecimal getValorMedioPrestacao() { return valorMedioPrestacao; }
    public BigDecimal getValorTotalDesejado() { return valorTotalDesejado; }
    public BigDecimal getValorTotalCredito() { return valorTotalCredito; }
}
