package com.desafio.repository;

import com.desafio.model.Telemetria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TelemetriaRepositoryIntegrationTest {
    @Autowired
    private TelemetriaRepository telemetriaRepository;

    @Test
    void deveSalvarEAgregrarTelemetria() {
        Telemetria t1 = new Telemetria();
        t1.setDataReferencia(LocalDate.now());
        t1.setNomeApi("/api/teste");
        t1.setTempoRespostaMs(100);
        t1.setStatusHttp(200);
        telemetriaRepository.save(t1);

        Telemetria t2 = new Telemetria();
        t2.setDataReferencia(LocalDate.now());
        t2.setNomeApi("/api/teste");
        t2.setTempoRespostaMs(200);
        t2.setStatusHttp(500);
        telemetriaRepository.save(t2);

        var lista = telemetriaRepository.buscarPorData(LocalDate.now());
        assertThat(lista).isNotEmpty();
        assertThat(lista.get(0).getNomeApi()).isEqualTo("/api/teste");
        assertThat(lista.get(0).getQtdRequisicoes()).isEqualTo(2);
        assertThat(lista.get(0).getTempoMedio()).isEqualTo(150.0);
        assertThat(lista.get(0).getTempoMinimo()).isEqualTo(100);
        assertThat(lista.get(0).getTempoMaximo()).isEqualTo(200);
        assertThat(lista.get(0).getPercentualSucesso()).isEqualTo(0.5);
    }
}
