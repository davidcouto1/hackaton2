-- View materializada para relatórios agregados por produto e dia
-- Execute no SQL Server

CREATE VIEW vw_relatorio_produto_dia AS
SELECT
    s.codigoProduto,
    s.nomeProduto,
    CAST(AVG(s.taxaJuros) AS decimal(10,4)) AS taxaMedia,
    CAST(AVG((s.valorTotalSac + s.valorTotalPrice) / 2) AS decimal(18,2)) AS valorMedio,
    SUM(s.valorDesejado) AS somaValoresDesejados,
    SUM(s.valorTotalSac + s.valorTotalPrice) AS somaValoresTotais,
    CAST(s.dataSimulacao AS date) AS dataSimulacao
FROM Simulacao s
GROUP BY s.codigoProduto, s.nomeProduto, CAST(s.dataSimulacao AS date);

-- Para atualizar periodicamente, utilize um job SQL Server ou reexecute o comando conforme necessário.
