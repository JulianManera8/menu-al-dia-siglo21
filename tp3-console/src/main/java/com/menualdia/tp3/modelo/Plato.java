package com.menualdia.tp3.modelo;

import java.math.BigDecimal;

/** Especialización de ElementoMenu para platos individuales. */
public final class Plato extends ElementoMenu {
    private final String categoria;
    private final String descripcion;

    public Plato(int codigo, String nombre, BigDecimal precio, String categoria, String descripcion) {
        super(codigo, nombre, precio);
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("La categoría es obligatoria.");
        }
        this.categoria = categoria.trim();
        this.descripcion = descripcion == null ? "" : descripcion.trim();
    }

    public String getCategoria() { return categoria; }
    public String getDescripcion() { return descripcion; }

    @Override
    public String getTipo() { return "Plato"; }

    @Override
    public String getDetalle() {
        return categoria + (descripcion.isEmpty() ? "" : " — " + descripcion);
    }
}
