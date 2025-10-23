package com.apisul.elevador.controller;

import com.apisul.elevador.model.RelatorioResponse;
import com.apisul.elevador.model.ElevadorEnum;
import com.apisul.elevador.service.IElevadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Elevador API", description = "Endpoints para gerar relatório dos elevadores")
public class ElevadorController {

    private final IElevadorService service;

    public ElevadorController(IElevadorService service) {
        this.service = service;
    }

    @GetMapping(value = "/relatorio", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Gera o relatório completo com todas as métricas solicitadas")
    public RelatorioResponse relatorio() {
        RelatorioResponse resp = new RelatorioResponse();
        resp.setAndarMenosUtilizado(service.andarMenosUtilizado());
        resp.setElevadorMaisFrequentado(service.elevadorMaisFrequentado());
        resp.setPeriodoMaiorFluxoElevadorMaisFrequentado(service.periodoMaiorFluxoElevadorMaisFrequentado());
        resp.setElevadorMenosFrequentado(service.elevadorMenosFrequentado());
        resp.setPeriodoMenorFluxoElevadorMenosFrequentado(service.periodoMenorFluxoElevadorMenosFrequentado());
        resp.setPeriodoMaiorUtilizacaoConjuntoElevadores(service.periodoMaiorUtilizacaoConjuntoElevadores());
        Map<String, Float> pct = new HashMap<>();
        pct.put("A", service.percentualDeUsoElevadorA());
        pct.put("B", service.percentualDeUsoElevadorB());
        pct.put("C", service.percentualDeUsoElevadorC());
        pct.put("D", service.percentualDeUsoElevadorD());
        pct.put("E", service.percentualDeUsoElevadorE());
        resp.setPercentualUsoElevadores(pct);
        return resp;
    }

    @GetMapping(value = "/andar/menos", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retorna o(s) andar(es) menos utilizado(s)")
    public List<Integer> andarMenosUtilizado() {
        return service.andarMenosUtilizado();
    }

    @GetMapping(value = "/elevador/mais", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retorna o(s) elevador(es) mais frequentado(s)")
    public List<Character> elevadorMaisFrequentado() {
        return service.elevadorMaisFrequentado();
    }

    @GetMapping(value = "/elevador/mais/periodos", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retorna o(s) período(s) de maior fluxo para um elevador (opcional: elevador = A). Se não informado, retorna para os elevadores mais frequentados")
    public List<Character> periodoMaiorFluxoPorElevador(@RequestParam(required = false) ElevadorEnum elevador) {
        if (elevador == null) return service.periodoMaiorFluxoPorElevador(null);
        return service.periodoMaiorFluxoPorElevador(elevador.name());
    }

    @GetMapping(value = "/elevador/menos", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retorna o(s) elevador(es) menos frequentado(s)")
    public List<Character> elevadorMenosFrequentado() {
        return service.elevadorMenosFrequentado();
    }

    @GetMapping(value = "/elevador/menos/periodos", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retorna o(s) período(s) de menor fluxo para um elevador (opcional: elevador = A). Se não informado, retorna para os elevadores menos frequentados")
    public Object periodoMenorFluxoPorElevador(@RequestParam(required = false) ElevadorEnum elevador) {
        if (elevador == null) {
            return service.periodoMenorFluxoElevadorMenosFrequentado();
        }
        return service.periodoMenorFluxoPorElevador(elevador.name());
    }

    @GetMapping(value = "/periodo/mais", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retorna o(s) período(s) de maior utilização do conjunto de elevadores")
    public List<Character> periodoMaiorUtilizacaoConjuntoElevadores() {
        return service.periodoMaiorUtilizacaoConjuntoElevadores();
    }

    @GetMapping(value = "/percentual", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retorna o percentual de uso por elevador. Use 'elevador = A' para um elevador ou sem param para todos")
    public Map<String, Float> percentualDeUsoPorElevador(@RequestParam(required = false) ElevadorEnum elevador) {
        if (elevador == null) {
            return service.percentualDeUsoPorElevador(null);
        }
        return service.percentualDeUsoPorElevador(elevador.name());
    }
}
