package com.apisul.elevador.service;

import java.util.List;
import java.util.Map;

public interface IElevadorService {

    /** Deve retornar uma List contendo o(s) andar(es) menos utilizado(s). */
    List<Integer> andarMenosUtilizado();

    /** Deve retornar uma List contendo o(s) elevador(es) mais frequentado(s). */
    List<Character> elevadorMaisFrequentado();

    /** Deve retornar uma List contendo o período de maior fluxo de cada um dos elevadores mais frequentados (se houver mais de um). */
    List<Character> periodoMaiorFluxoElevadorMaisFrequentado();

    /** Deve retornar uma List contendo o(s) elevador(es) menos frequentado(s). */
    List<Character> elevadorMenosFrequentado();

    /** Deve retornar um Map elevador -> lista de períodos de menor fluxo para cada um dos elevadores menos frequentados (se houver mais de um). */
    Map<String, List<Character>> periodoMenorFluxoElevadorMenosFrequentado();

    /** Deve retornar uma List contendo o(s) periodo(s) de maior utilização do conjunto de elevadores. */
    List<Character> periodoMaiorUtilizacaoConjuntoElevadores();

    /** Deve retornar um float (duas casas decimais) contendo o percentual de uso do elevador A em relação a todos os serviços prestados. */
    float percentualDeUsoElevadorA();

    /** Deve retornar um float (duas casas decimais) contendo o percentual de uso do elevador B em relação a todos os serviços prestados. */
    float percentualDeUsoElevadorB();

    /** Deve retornar um float (duas casas decimais) contendo o percentual de uso do elevador C em relação a todos os serviços prestados. */
    float percentualDeUsoElevadorC();

    /** Deve retornar um float (duas casas decimais) contendo o percentual de uso do elevador D em relação a todos os serviços prestados. */
    float percentualDeUsoElevadorD();

    /** Deve retornar um float (duas casas decimais) contendo o percentual de uso do elevador E em relação a todos os serviços prestados. */
    float percentualDeUsoElevadorE();

    /** Retorna os períodos de maior fluxo para o elevador informado. Se elevador é null ou vazio, retorna para o(s) elevador(es) mais frequentado(s). */
    List<Character> periodoMaiorFluxoPorElevador(String elevador);

    /** Retorna os períodos de menor fluxo para o elevador informado. Se elevador é null ou vazio, retorna para o(s) elevador(es) menos frequentado(s). */
    List<Character> periodoMenorFluxoPorElevador(String elevador);

    /** Percentual de uso de um elevador (A..E). Se elevador for null, retorna o map com todos os percentuais. */
    Map<String, Float> percentualDeUsoPorElevador(String elevador);

}