-- Índices recomendados para alta performance nas consultas agregadas
-- Execute no SQL Server

-- Índice para agrupamento e filtro por data e produto nas simulações
CREATE INDEX IDX_Simulacao_DataProduto ON Simulacao (dataSimulacao, codigoProduto);

-- Índice para agrupamento e filtro por nomeApi e data na telemetria
CREATE INDEX IDX_Telemetria_NomeApiData ON Telemetria (nomeApi, dataReferencia);

-- Índice para busca eficiente por statusHttp na telemetria
CREATE INDEX IDX_Telemetria_StatusHttp ON Telemetria (statusHttp);

-- Índice para busca eficiente por produto nas simulações
CREATE INDEX IDX_Simulacao_CodigoProduto ON Simulacao (codigoProduto);

-- Adapte os nomes das tabelas/campos conforme necessário para o seu banco.
