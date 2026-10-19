package com.menualdia.tp3;

import com.menualdia.tp3.modelo.*;
import com.menualdia.tp3.servicio.GestorMenus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/** Pruebas sin bibliotecas externas: requieren Java 17 o superior. */
public final class PruebasTP3 {
    private static int ejecutadas;

    public static void main(String[] args) {
        GestorMenus gestor = new GestorMenus();
        Plato milanesa = gestor.registrarPlato("Milanesa", "Principal", new BigDecimal("9800"), "Con papas");
        Plato ensalada = gestor.registrarPlato("Ensalada", "Entrada", new BigDecimal("4500"), "Mixta");

        verificar("Registro y constructor", milanesa.getCodigo() == 1 && milanesa.getNombre().equals("Milanesa"));
        verificar("Búsqueda parcial", gestor.buscarPorNombre("mila").size() == 1);
        verificar("Ordenamiento alfabético", gestor.listarCatalogo().get(0) == ensalada);
        esperarError("Precio inválido", IllegalArgumentException.class,
                () -> gestor.registrarPlato("", "Principal", new BigDecimal("200"), ""));
        esperarError("Precio negativo", IllegalArgumentException.class,
                () -> gestor.registrarPlato("Otro", "Principal", new BigDecimal("-1"), ""));

        Combo combo = gestor.registrarCombo("Combo almuerzo", new BigDecimal("12000"), List.of(1, 2));
        verificar("Herencia y polimorfismo", combo instanceof ElementoMenu && combo.getTipo().equals("Combo"));
        verificar("Composición del combo", combo.getPlatos().size() == 2);
        esperarError("Combo con platos duplicados", IllegalArgumentException.class,
                () -> gestor.registrarCombo("Duplicado", new BigDecimal("10"), List.of(1, 1)));

        LocalDate fecha = LocalDate.of(2026, 10, 19);
        MenuDiario menu = gestor.crearMenu(fecha);
        gestor.agregarAlMenu(fecha, 1);
        gestor.agregarAlMenu(fecha, combo.getCodigo());
        verificar("Menú con lista de elementos", menu.getElementos().size() == 2);
        esperarError("Sin menú duplicado", IllegalArgumentException.class,
                () -> gestor.crearMenu(fecha));
        esperarError("Sin elemento duplicado", IllegalArgumentException.class,
                () -> gestor.agregarAlMenu(fecha, 1));

        gestor.cambiarDisponibilidad(2, false);
        verificar("Polimorfismo: combo agotado si falta un plato", !combo.isDisponible());
        gestor.cambiarDisponibilidad(2, true);
        verificar("Combo disponible al restaurar plato", combo.isDisponible());
        gestor.publicarMenu(fecha);
        verificar("Publicación correcta", menu.getEstado() == EstadoMenu.PUBLICADO);
        esperarError("Bloqueo de cambios posteriores", IllegalStateException.class,
                () -> gestor.agregarAlMenu(fecha, 2));
        esperarError("Consulta código inexistente", java.util.NoSuchElementException.class,
                () -> gestor.buscarPorCodigo(12345));
        esperarError("Menú vacío no se publica", IllegalStateException.class,
                () -> gestor.crearMenu(fecha.plusDays(1)).publicar());
        esperarError("Lista interna protegida", UnsupportedOperationException.class,
                () -> combo.getPlatos().clear());
        System.out.println("OK: " + ejecutadas + " pruebas superadas.");
    }

    private static void verificar(String nombre, boolean condicion) {
        if (!condicion) throw new AssertionError("Falló: " + nombre);
        ejecutadas++;
        System.out.println("PASS - " + nombre);
    }

    private static void esperarError(String nombre, Class<? extends Throwable> tipo, Runnable bloque) {
        try {
            bloque.run();
            throw new AssertionError("Falló: " + nombre + " (no lanzó excepción)");
        } catch (Throwable e) {
            if (!tipo.isInstance(e)) throw new AssertionError("Falló: " + nombre + " (" + e + ")", e);
            ejecutadas++;
            System.out.println("PASS - " + nombre);
        }
    }
}
