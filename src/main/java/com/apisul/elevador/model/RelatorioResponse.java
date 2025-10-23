package com.apisul.elevador.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RelatorioResponse {
    private List<Integer> andarMenosUtilizado;
    private List<Character> elevadorMaisFrequentado;
    private List<Character> periodoMaiorFluxoElevadorMaisFrequentado;
    private List<Character> elevadorMenosFrequentado;
    private Map<String, List<Character>> periodoMenorFluxoElevadorMenosFrequentado;
    private List<Character> periodoMaiorUtilizacaoConjuntoElevadores;
    private Map<String, Float> percentualUsoElevadores;

}
