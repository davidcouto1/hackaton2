package com.desafio.model;

import com.desafio.model.Simulacao;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Parcela {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParcela;

    @ManyToOne
    @JoinColumn(name = "idSimulacao")
    private Simulacao simulacao;

    private String tipo; // SAC ou PRICE
    private Integer numero;
    private BigDecimal valorAmortizacao;
    private BigDecimal valorJuros;
    private BigDecimal valorPrestacao;

    public Long getIdParcela() { return idParcela; }
    public void setIdParcela(Long idParcela) { this.idParcela = idParcela; }
    public Simulacao getSimulacao() { return simulacao; }
    public void setSimulacao(Simulacao simulacao) { this.simulacao = simulacao; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public BigDecimal getValorAmortizacao() { return valorAmortizacao; }
    public void setValorAmortizacao(BigDecimal valorAmortizacao) { this.valorAmortizacao = valorAmortizacao; }
    public BigDecimal getValorJuros() { return valorJuros; }
    public void setValorJuros(BigDecimal valorJuros) { this.valorJuros = valorJuros; }
    public BigDecimal getValorPrestacao() { return valorPrestacao; }
    public void setValorPrestacao(BigDecimal valorPrestacao) { this.valorPrestacao = valorPrestacao; }
}
