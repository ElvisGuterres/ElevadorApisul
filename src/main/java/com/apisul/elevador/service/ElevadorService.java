package com.apisul.elevador.service;

import com.apisul.elevador.model.Registro;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class ElevadorService implements IElevadorService {

    private final List<Registro> registros = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(ElevadorService.class);

    public ElevadorService() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = new ClassPathResource("input/input.json").getInputStream()) {
            List<Registro> list = mapper.readValue(is, new TypeReference<>() {
            });
            registros.addAll(list);
        } catch (IOException e) {
            logger.warn("Não foi possível carregar input/input.json - mantendo lista vazia", e);
        }
    }

    private Map<Integer, Long> countsPorAndar() {
        Map<Integer, Long> counts = new HashMap<>();
        for (int i = 0; i <= 15; i++) counts.put(i, 0L);
        for (Registro r : registros) {
            counts.put(r.getAndar(), counts.getOrDefault(r.getAndar(), 0L) + 1);
        }
        return counts;
    }

    private Map<String, Long> countsPorElevador() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("A", 0L);
        counts.put("B", 0L);
        counts.put("C", 0L);
        counts.put("D", 0L);
        counts.put("E", 0L);
        for (Registro r : registros) {
            String e = r.getElevador();
            counts.put(e, counts.getOrDefault(e, 0L) + 1);
        }
        return counts;
    }

    private Map<String, Map<String, Long>> countsElevadorPorTurno() {
        Map<String, Map<String, Long>> map = new HashMap<>();
        for (String e : Arrays.asList("A", "B", "C", "D", "E")) {
            Map<String, Long> tmap = new HashMap<>();
            tmap.put("M", 0L);
            tmap.put("V", 0L);
            tmap.put("N", 0L);
            map.put(e, tmap);
        }
        for (Registro r : registros) {
            String elev = r.getElevador();
            String turno = r.getTurno();
            Map<String, Long> tmap = map.get(elev);
            if (tmap == null) {
                tmap = new HashMap<>();
                tmap.put("M", 0L);
                tmap.put("V", 0L);
                tmap.put("N", 0L);
                map.put(elev, tmap);
            }
            tmap.put(turno, tmap.getOrDefault(turno, 0L) + 1);
        }
        return map;
    }

    private Map<String, Long> countsPorTurnoConjunto() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("M", 0L);
        counts.put("V", 0L);
        counts.put("N", 0L);
        for (Registro r : registros) {
            String t = r.getTurno();
            counts.put(t, counts.getOrDefault(t, 0L) + 1);
        }
        return counts;
    }

    @Override
    public List<Integer> andarMenosUtilizado() {
        Map<Integer, Long> counts = countsPorAndar();
        long min = Collections.min(counts.values());
        return counts.entrySet().stream()
                .filter(e -> e.getValue() == min)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();
    }

    @Override
    public List<Character> elevadorMaisFrequentado() {
        Map<String, Long> counts = countsPorElevador();
        long max = Collections.max(counts.values());
        return counts.entrySet().stream()
                .filter(e -> e.getValue() == max)
                .map(e -> e.getKey().charAt(0))
                .sorted()
                .toList();
    }

    @Override
    public List<Character> periodoMaiorFluxoElevadorMaisFrequentado() {
        List<Character> mais = elevadorMaisFrequentado();
        Map<String, Map<String, Long>> byElevTurno = countsElevadorPorTurno();
        List<Character> result = new ArrayList<>();
        for (Character c : mais) {
            String elev = String.valueOf(c);
            Map<String, Long> tmap = byElevTurno.getOrDefault(elev, Collections.emptyMap());
            long max = tmap.values().stream().mapToLong(Long::longValue).max().orElse(0L);
            List<Character> periods = tmap.entrySet().stream()
                    .filter(e -> e.getValue() == max)
                    .map(e -> e.getKey().charAt(0))
                    .sorted()
                    .toList();
            result.addAll(periods);
        }
        return result;
    }

    @Override
    public List<Character> elevadorMenosFrequentado() {
        Map<String, Long> counts = countsPorElevador();
        long min = Collections.min(counts.values());
        return counts.entrySet().stream()
                .filter(e -> e.getValue() == min)
                .map(e -> e.getKey().charAt(0))
                .sorted()
                .toList();
    }

    @Override
    public Map<String, List<Character>> periodoMenorFluxoElevadorMenosFrequentado() {
        List<Character> menos = elevadorMenosFrequentado();
        Map<String, Map<String, Long>> byElevTurno = countsElevadorPorTurno();
        Map<String, List<Character>> result = new LinkedHashMap<>();
        for (Character c : menos) {
            String elev = String.valueOf(c);
            Map<String, Long> tmap = byElevTurno.getOrDefault(elev, Collections.emptyMap());
            long min = tmap.values().stream().mapToLong(Long::longValue).min().orElse(0L);
            List<Character> periods = tmap.entrySet().stream()
                    .filter(e -> e.getValue() == min)
                    .map(e -> e.getKey().charAt(0))
                    .sorted()
                    .toList();
            result.put(elev, periods);
        }
        return result;
    }

    @Override
    public List<Character> periodoMaiorUtilizacaoConjuntoElevadores() {
        Map<String, Long> counts = countsPorTurnoConjunto();
        long max = Collections.max(counts.values());
        return counts.entrySet().stream()
                .filter(e -> e.getValue() == max)
                .map(e -> e.getKey().charAt(0))
                .sorted()
                .toList();
    }

    private float percentual(Map<String, Long> counts, String elevador) {
        long total = counts.values().stream().mapToLong(Long::longValue).sum();
        if (total == 0) return 0.0f;
        long valor = counts.getOrDefault(elevador, 0L);
        BigDecimal percent = BigDecimal.valueOf(valor * 100.0 / total).setScale(2, RoundingMode.HALF_UP);
        return percent.floatValue();
    }

    @Override
    public float percentualDeUsoElevadorA() {
        return percentual(countsPorElevador(), "A");
    }

    @Override
    public float percentualDeUsoElevadorB() {
        return percentual(countsPorElevador(), "B");
    }

    @Override
    public float percentualDeUsoElevadorC() {
        return percentual(countsPorElevador(), "C");
    }

    @Override
    public float percentualDeUsoElevadorD() {
        return percentual(countsPorElevador(), "D");
    }

    @Override
    public float percentualDeUsoElevadorE() {
        return percentual(countsPorElevador(), "E");
    }

    @Override
    public List<Character> periodoMaiorFluxoPorElevador(String elevador) {
        if (elevador == null || elevador.isBlank()) {
            return periodoMaiorFluxoElevadorMaisFrequentado();
        }
        return periodosPorElevador(elevador, true);
    }

    @Override
    public List<Character> periodoMenorFluxoPorElevador(String elevador) {
        if (elevador == null || elevador.isBlank()) {
            Map<String, List<Character>> map = periodoMenorFluxoElevadorMenosFrequentado();
            return map.values().stream().flatMap(List::stream).toList();
        }
        return periodosPorElevador(elevador, false);
    }


    @Override
    public Map<String, Float> percentualDeUsoPorElevador(String elevador) {
        Map<String, Float> result = new LinkedHashMap<>();
        Map<String, Long> counts = countsPorElevador();
        if (elevador == null || elevador.isBlank()) {
            for (String key : Arrays.asList("A", "B", "C", "D", "E")) {
                result.put(key, percentual(counts, key));
            }
            return result;
        }
        String elev = elevador.trim().toUpperCase(Locale.ROOT);
        if (!counts.containsKey(elev)) return Collections.emptyMap();
        result.put(elev, percentual(counts, elev));
        return result;
    }

    private List<Character> periodosPorElevador(String elevador, boolean maiorFluxo) {
        String elev = elevador.trim().toUpperCase(Locale.ROOT);
        Map<String, Map<String, Long>> byElevTurno = countsElevadorPorTurno();
        Map<String, Long> tmap = byElevTurno.get(elev);
        if (tmap == null || tmap.isEmpty()) return Collections.emptyList();

        long ref = maiorFluxo
                ? tmap.values().stream().mapToLong(Long::longValue).max().orElse(0L)
                : tmap.values().stream().mapToLong(Long::longValue).min().orElse(0L);

        return tmap.entrySet().stream()
                .filter(e -> e.getValue() == ref)
                .map(e -> e.getKey().charAt(0))
                .sorted()
                .toList();
    }

}
