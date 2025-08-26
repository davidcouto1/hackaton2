-- Script de índices para execução automática no H2
CREATE INDEX IF NOT EXISTS IDX_Simulacao_DataProduto ON Simulacao (dataSimulacao, codigoProduto);
CREATE INDEX IF NOT EXISTS IDX_Telemetria_NomeApiData ON Telemetria (nomeApi, dataReferencia);
CREATE INDEX IF NOT EXISTS IDX_Telemetria_StatusHttp ON Telemetria (statusHttp);
CREATE INDEX IF NOT EXISTS IDX_Simulacao_CodigoProduto ON Simulacao (codigoProduto);
