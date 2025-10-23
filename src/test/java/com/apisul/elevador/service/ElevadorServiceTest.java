package com.apisul.elevador.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ElevadorServiceTest {

    private static ElevadorService service;

    @BeforeAll
    static void setup() {
        service = new ElevadorService();
    }

    @Test
    void testAndarMenosUtilizado() {
        List<Integer> esperado = List.of(8);
        List<Integer> atual = service.andarMenosUtilizado();
        assertEquals(esperado, atual);
    }

    @Test
    void testElevadorMaisFrequentadoEPeriodo() {
        List<Character> esperadoElev = List.of('C');
        List<Character> atualElev = service.elevadorMaisFrequentado();
        assertEquals(esperadoElev, atualElev);

        List<Character> esperadoPeriodo = List.of('M');
        List<Character> atualPeriodo = service.periodoMaiorFluxoElevadorMaisFrequentado();
        assertEquals(esperadoPeriodo, atualPeriodo);
    }

    @Test
    void testElevadorMenosFrequentadoEPeriodos() {
        List<Character> esperadoMenos = List.of('D', 'E');
        List<Character> atualMenos = service.elevadorMenosFrequentado();
        assertEquals(esperadoMenos, atualMenos);

        Map<String, List<Character>> esperadoMap = Map.of(
                "D", List.of('N', 'V'),
                "E", List.of('N', 'V')
        );
        Map<String, List<Character>> atualMap = service.periodoMenorFluxoElevadorMenosFrequentado();
        assertEquals(esperadoMap.keySet(), atualMap.keySet());
        assertEquals(esperadoMap.get("D"), atualMap.get("D"));
        assertEquals(esperadoMap.get("E"), atualMap.get("E"));
    }

    @Test
    void testPeriodoMaiorUtilizacaoConjunto() {
        List<Character> esperado = List.of('M');
        List<Character> atual = service.periodoMaiorUtilizacaoConjuntoElevadores();
        assertEquals(esperado, atual);
    }

    @Test
    void testPercentuaisIndividuais() {
        assertEquals(34.78f, service.percentualDeUsoElevadorA(), 0.001f);
        assertEquals(17.39f, service.percentualDeUsoElevadorB(), 0.001f);
        assertEquals(39.13f, service.percentualDeUsoElevadorC(), 0.001f);
        assertEquals(4.35f, service.percentualDeUsoElevadorD(), 0.001f);
        assertEquals(4.35f, service.percentualDeUsoElevadorE(), 0.001f);
    }

    @Test
    void testPeriodoMaiorEMenorPorElevador() {
        assertEquals(List.of('M'), service.periodoMaiorFluxoPorElevador("A"));

        assertEquals(List.of('N'), service.periodoMenorFluxoPorElevador("B"));
    }

    @Test
    void testPercentualDeUsoPorElevador() {
        Map<String, Float> todos = service.percentualDeUsoPorElevador(null);
        assertEquals(5, todos.size());
        assertEquals(34.78f, todos.get("A"), 0.001f);
        assertEquals(17.39f, todos.get("B"), 0.001f);
        assertEquals(39.13f, todos.get("C"), 0.001f);
        assertEquals(4.35f, todos.get("D"), 0.001f);
        assertEquals(4.35f, todos.get("E"), 0.001f);

        Map<String, Float> apenasC = service.percentualDeUsoPorElevador("C");
        assertEquals(1, apenasC.size());
        assertEquals(39.13f, apenasC.get("C"), 0.001f);
    }

}

