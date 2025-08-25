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
    private Integer tempoRespostaMs;
    private Integer statusHttp;

    public Long getIdTelemetria() { return idTelemetria; }
    public void setIdTelemetria(Long idTelemetria) { this.idTelemetria = idTelemetria; }
    public LocalDate getDataReferencia() { return dataReferencia; }
    public void setDataReferencia(LocalDate dataReferencia) { this.dataReferencia = dataReferencia; }
    public String getNomeApi() { return nomeApi; }
    public void setNomeApi(String nomeApi) { this.nomeApi = nomeApi; }
    public Integer getTempoRespostaMs() { return tempoRespostaMs; }
    public void setTempoRespostaMs(Integer tempoRespostaMs) { this.tempoRespostaMs = tempoRespostaMs; }
    public Integer getStatusHttp() { return statusHttp; }
    public void setStatusHttp(Integer statusHttp) { this.statusHttp = statusHttp; }
}
