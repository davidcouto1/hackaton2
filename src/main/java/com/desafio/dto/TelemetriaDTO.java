package com.desafio.dto;

public class TelemetriaDTO {
    private String nomeApi;
    private long qtdRequisicoes;
    private double tempoMedio;
    private int tempoMinimo;
    private int tempoMaximo;
    private double percentualSucesso;

    public TelemetriaDTO(String nomeApi, long qtdRequisicoes, double tempoMedio, int tempoMinimo, int tempoMaximo, double percentualSucesso) {
        this.nomeApi = nomeApi;
        this.qtdRequisicoes = qtdRequisicoes;
        this.tempoMedio = tempoMedio;
        this.tempoMinimo = tempoMinimo;
        this.tempoMaximo = tempoMaximo;
        this.percentualSucesso = percentualSucesso;
    }

    public String getNomeApi() { return nomeApi; }
    public long getQtdRequisicoes() { return qtdRequisicoes; }
    public double getTempoMedio() { return tempoMedio; }
    public int getTempoMinimo() { return tempoMinimo; }
    public int getTempoMaximo() { return tempoMaximo; }
    public double getPercentualSucesso() { return percentualSucesso; }
}
