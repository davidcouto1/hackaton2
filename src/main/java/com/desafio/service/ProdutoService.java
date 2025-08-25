package com.desafio.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProdutoService {
    private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);
    @Value("${sqlserver.datasource.url}")
    private String url;
    @Value("${sqlserver.datasource.username}")
    private String username;
    @Value("${sqlserver.datasource.password}")
    private String password;

    @CircuitBreaker(name = "default")
    @RateLimiter(name = "default")
    public List<ProdutoDTO> buscarProdutos() throws Exception {
        logger.info("[AUDITORIA] Buscando produtos no SQL Server");
        List<ProdutoDTO> produtos = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT CO_PRODUTO, NO_PRODUTO, PC_TAXA_JUROS, NU_MINIMO_MESES, NU_MAXIMO_MESES, VR_MINIMO, VR_MAXIMO FROM dbo.PRODUTO";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ProdutoDTO dto = new ProdutoDTO();
                    dto.setCodigo(rs.getInt("CO_PRODUTO"));
                    dto.setNome(rs.getString("NO_PRODUTO"));
                    dto.setTaxaJuros(rs.getBigDecimal("PC_TAXA_JUROS"));
                    dto.setMinimoMeses(rs.getInt("NU_MINIMO_MESES"));
                    dto.setMaximoMeses(rs.getObject("NU_MAXIMO_MESES") != null ? rs.getInt("NU_MAXIMO_MESES") : null);
                    dto.setValorMinimo(rs.getBigDecimal("VR_MINIMO"));
                    dto.setValorMaximo(rs.getObject("VR_MAXIMO") != null ? rs.getBigDecimal("VR_MAXIMO") : null);
                    produtos.add(dto);
                }
            }
        } catch (Exception e) {
            logger.error("[ERRO] Falha ao buscar produtos: " + e.getMessage(), e);
            throw e;
        }
        return produtos;
    }

    public static class ProdutoDTO {
        private int codigo;
        private String nome;
        private BigDecimal taxaJuros;
        private int minimoMeses;
        private Integer maximoMeses;
        private BigDecimal valorMinimo;
        private BigDecimal valorMaximo;

        public int getCodigo() { return codigo; }
        public void setCodigo(int codigo) { this.codigo = codigo; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public BigDecimal getTaxaJuros() { return taxaJuros; }
        public void setTaxaJuros(BigDecimal taxaJuros) { this.taxaJuros = taxaJuros; }
        public int getMinimoMeses() { return minimoMeses; }
        public void setMinimoMeses(int minimoMeses) { this.minimoMeses = minimoMeses; }
        public Integer getMaximoMeses() { return maximoMeses; }
        public void setMaximoMeses(Integer maximoMeses) { this.maximoMeses = maximoMeses; }
        public BigDecimal getValorMinimo() { return valorMinimo; }
        public void setValorMinimo(BigDecimal valorMinimo) { this.valorMinimo = valorMinimo; }
        public BigDecimal getValorMaximo() { return valorMaximo; }
        public void setValorMaximo(BigDecimal valorMaximo) { this.valorMaximo = valorMaximo; }
    }
}
