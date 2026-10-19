package com.menualdia.tp3.modelo;

import java.math.BigDecimal;
import java.util.Objects;

/** Abstracción compartida por platos individuales y combos. */
public abstract class ElementoMenu {
    private final int codigo;
    private final String nombre;
    private BigDecimal precio;
    private boolean disponible;

    protected ElementoMenu(int codigo, String nombre, BigDecimal precio) {
        if (codigo <= 0) throw new IllegalArgumentException("El código debe ser positivo.");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio.");
        setPrecio(precio);
        this.codigo = codigo;
        this.nombre = nombre.trim();
        this.disponible = true;
    }

    public final int getCodigo() { return codigo; }
    public final String getNombre() { return nombre; }
    public final BigDecimal getPrecio() { return precio; }

    public final void setPrecio(BigDecimal nuevoPrecio) {
        if (nuevoPrecio == null || nuevoPrecio.signum() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero.");
        }
        this.precio = nuevoPrecio;
    }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public abstract String getTipo();
    public abstract String getDetalle();

    @Override
    public String toString() {
        return codigo + " | " + getTipo() + " | " + nombre + " | $" + precio +
                " | " + (isDisponible() ? "Disponible" : "Agotado") + " | " + getDetalle();
    }

    @Override
    public final boolean equals(Object otro) {
        if (this == otro) return true;
        if (!(otro instanceof ElementoMenu elemento)) return false;
        return codigo == elemento.codigo;
    }

    @Override
    public final int hashCode() { return Objects.hash(codigo); }
}
