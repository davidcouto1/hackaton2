package com.desafio.dto;

import java.math.BigDecimal;

public class SimulacaoPorProdutoDiaDTO {
    private int codigoProduto;
    private String descricaoProduto;
    private BigDecimal taxaMediaJuro;
    private BigDecimal valorMedioPrestacaoSAC;
    private BigDecimal valorMedioPrestacaoPrice;
    private BigDecimal valorTotalDesejado;
    private BigDecimal valorTotalCreditoSac;
    private BigDecimal valorTotalCreditoPrice;

    public SimulacaoPorProdutoDiaDTO(int codigoProduto, String descricaoProduto, BigDecimal taxaMediaJuro, BigDecimal valorMedioPrestacaoSAC, BigDecimal valorMedioPrestacaoPrice, BigDecimal valorTotalDesejado, BigDecimal valorTotalCreditoSac, BigDecimal valorTotalCreditoPrice) {
        this.codigoProduto = codigoProduto;
        this.descricaoProduto = descricaoProduto;
        this.taxaMediaJuro = taxaMediaJuro;
        this.valorMedioPrestacaoSAC = valorMedioPrestacaoSAC;
        this.valorMedioPrestacaoPrice = valorMedioPrestacaoPrice;
        this.valorTotalDesejado = valorTotalDesejado;
        this.valorTotalCreditoSac = valorTotalCreditoSac;
        this.valorTotalCreditoPrice = valorTotalCreditoPrice;
    }

    public int getCodigoProduto() { return codigoProduto; }
    public String getDescricaoProduto() { return descricaoProduto; }
    public BigDecimal getTaxaMediaJuro() { return taxaMediaJuro; }
    public BigDecimal getValorMedioPrestacaoSAC() { return valorMedioPrestacaoSAC; }
    public BigDecimal getValorMedioPrestacaoPrice() { return valorMedioPrestacaoPrice; }
    public BigDecimal getValorTotalDesejado() { return valorTotalDesejado; }
    public BigDecimal getValorTotalCreditoSac() { return valorTotalCreditoSac; }
    public BigDecimal getValorTotalCreditoPrice() { return valorTotalCreditoPrice; }
}
