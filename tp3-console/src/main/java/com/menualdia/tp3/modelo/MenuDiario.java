package com.menualdia.tp3.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Menú de una fecha: reúne polimórficamente platos y combos. */
public final class MenuDiario {
    private final LocalDate fecha;
    private EstadoMenu estado;
    private final List<ElementoMenu> elementos = new ArrayList<>();

    public MenuDiario(LocalDate fecha) {
        if (fecha == null) throw new IllegalArgumentException("La fecha es obligatoria.");
        this.fecha = fecha;
        this.estado = EstadoMenu.BORRADOR;
    }

    public LocalDate getFecha() { return fecha; }
    public EstadoMenu getEstado() { return estado; }

    public List<ElementoMenu> getElementos() {
        return elementos.stream()
                .sorted(Comparator.comparing(ElementoMenu::getNombre, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public void agregar(ElementoMenu elemento) {
        if (estado != EstadoMenu.BORRADOR) {
            throw new IllegalStateException("No se pueden agregar elementos a un menú publicado.");
        }
        if (elemento == null) throw new IllegalArgumentException("El elemento es obligatorio.");
        if (elementos.contains(elemento)) throw new IllegalArgumentException("El elemento ya está en el menú.");
        elementos.add(elemento);
    }

    public void publicar() {
        if (estado == EstadoMenu.PUBLICADO) throw new IllegalStateException("El menú ya está publicado.");
        if (elementos.isEmpty()) throw new IllegalStateException("No se puede publicar un menú vacío.");
        if (elementos.stream().noneMatch(ElementoMenu::isDisponible)) {
            throw new IllegalStateException("El menú debe tener al menos un elemento disponible.");
        }
        estado = EstadoMenu.PUBLICADO;
    }
}
