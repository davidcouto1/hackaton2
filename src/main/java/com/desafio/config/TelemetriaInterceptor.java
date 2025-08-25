package com.desafio.config;

import com.desafio.model.Telemetria;
import com.desafio.service.TelemetriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@Component
public class TelemetriaInterceptor implements HandlerInterceptor {
    @Autowired
    private TelemetriaService telemetriaService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("telemetriaStart", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Long start = (Long) request.getAttribute("telemetriaStart");
        if (start != null) {
            int tempoResposta = (int) (System.currentTimeMillis() - start);
            String endpoint = request.getRequestURI();
            int status = response.getStatus();
            Telemetria telemetria = new Telemetria();
            telemetria.setDataReferencia(LocalDate.now());
            telemetria.setNomeApi(endpoint);
            telemetria.setTempoRespostaMs(tempoResposta);
            telemetria.setStatusHttp(status);
            telemetriaService.salvarTelemetria(telemetria);
        }
    }
}
