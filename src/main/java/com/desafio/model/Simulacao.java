package com.desafio.model;

import java.util.List;
import com.desafio.model.Parcela;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Simulacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSimulacao;
    private BigDecimal valorDesejado;
    private Integer prazo;
    private Integer codigoProduto;
    private String nomeProduto;
    private BigDecimal taxaJuros;
    private BigDecimal valorTotalSac;
    private BigDecimal valorTotalPrice;
    private LocalDateTime dataSimulacao;

    @OneToMany(mappedBy = "simulacao", cascade = CascadeType.ALL)
    private List<Parcela> parcelas;

    public Long getIdSimulacao() { return idSimulacao; }
    public void setIdSimulacao(Long idSimulacao) { this.idSimulacao = idSimulacao; }
    public BigDecimal getValorDesejado() { return valorDesejado; }
    public void setValorDesejado(BigDecimal valorDesejado) { this.valorDesejado = valorDesejado; }
    public Integer getPrazo() { return prazo; }
    public void setPrazo(Integer prazo) { this.prazo = prazo; }
    public Integer getCodigoProduto() { return codigoProduto; }
    public void setCodigoProduto(Integer codigoProduto) { this.codigoProduto = codigoProduto; }
    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    public BigDecimal getTaxaJuros() { return taxaJuros; }
    public void setTaxaJuros(BigDecimal taxaJuros) { this.taxaJuros = taxaJuros; }
    public BigDecimal getValorTotalSac() { return valorTotalSac; }
    public void setValorTotalSac(BigDecimal valorTotalSac) { this.valorTotalSac = valorTotalSac; }
    public BigDecimal getValorTotalPrice() { return valorTotalPrice; }
    public void setValorTotalPrice(BigDecimal valorTotalPrice) { this.valorTotalPrice = valorTotalPrice; }
    public LocalDateTime getDataSimulacao() { return dataSimulacao; }
    public void setDataSimulacao(LocalDateTime dataSimulacao) { this.dataSimulacao = dataSimulacao; }
    public List<Parcela> getParcelas() { return parcelas; }
    public void setParcelas(List<Parcela> parcelas) { this.parcelas = parcelas; }

    @PrePersist
    public void prePersist() {
        if (this.dataSimulacao == null) {
            this.dataSimulacao = java.time.LocalDateTime.now();
        }
    }
}
