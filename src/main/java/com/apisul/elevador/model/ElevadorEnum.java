package com.apisul.elevador.model;

/**
 * Enum para representar os elevadores válidos (A..E).
 * Usado para limitar os valores aceitos nos endpoints (ex.: /api/percentual?elevador=A)
 */
public enum ElevadorEnum {
    A, B, C, D, E;

    @Override
    public String toString() {
        return name();
    }
}

