package com.desafio.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Telemetria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTelemetria;
    private LocalDate dataReferencia;
    private String nomeApi;
    private Integer qtdRequisicoes;
    private Integer tempoMedio;
    private Integer tempoMinimo;
    private Integer tempoMaximo;
    private Double percentualSucesso;

    public Long getIdTelemetria() { return idTelemetria; }
    public void setIdTelemetria(Long idTelemetria) { this.idTelemetria = idTelemetria; }
    public LocalDate getDataReferencia() { return dataReferencia; }
    public void setDataReferencia(LocalDate dataReferencia) { this.dataReferencia = dataReferencia; }
    public String getNomeApi() { return nomeApi; }
    public void setNomeApi(String nomeApi) { this.nomeApi = nomeApi; }
    public Integer getQtdRequisicoes() { return qtdRequisicoes; }
    public void setQtdRequisicoes(Integer qtdRequisicoes) { this.qtdRequisicoes = qtdRequisicoes; }
    public Integer getTempoMedio() { return tempoMedio; }
    public void setTempoMedio(Integer tempoMedio) { this.tempoMedio = tempoMedio; }
    public Integer getTempoMinimo() { return tempoMinimo; }
    public void setTempoMinimo(Integer tempoMinimo) { this.tempoMinimo = tempoMinimo; }
    public Integer getTempoMaximo() { return tempoMaximo; }
    public void setTempoMaximo(Integer tempoMaximo) { this.tempoMaximo = tempoMaximo; }
    public Double getPercentualSucesso() { return percentualSucesso; }
    public void setPercentualSucesso(Double percentualSucesso) { this.percentualSucesso = percentualSucesso; }
}
