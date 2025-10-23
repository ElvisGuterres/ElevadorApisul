package com.apisul.elevador.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Registro {

    private int andar;
    private String elevador;
    private String turno;

    public Registro(@JsonProperty("andar") int andar,
                    @JsonProperty("elevador") String elevador,
                    @JsonProperty("turno") String turno) {
        this.andar = andar;
        this.elevador = elevador;
        this.turno = turno;
    }
}

