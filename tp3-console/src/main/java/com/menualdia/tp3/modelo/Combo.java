package com.menualdia.tp3.modelo;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/** Un combo es un elemento compuesto por dos o más platos. */
public final class Combo extends ElementoMenu {
    private final List<Plato> platos;

    public Combo(int codigo, String nombre, BigDecimal precio, List<Plato> platos) {
        super(codigo, nombre, precio);
        if (platos == null || platos.size() < 2 || platos.stream().distinct().count() != platos.size()) {
            throw new IllegalArgumentException("Un combo requiere dos o más platos diferentes.");
        }
        this.platos = List.copyOf(platos);
    }

    public List<Plato> getPlatos() { return platos; }

    @Override
    public boolean isDisponible() {
        return super.isDisponible() && platos.stream().allMatch(Plato::isDisponible);
    }

    @Override
    public String getTipo() { return "Combo"; }

    @Override
    public String getDetalle() {
        return platos.stream().map(Plato::getNombre).collect(Collectors.joining(" + "));
    }
}
