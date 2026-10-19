package com.menualdia.tp3.servicio;

import com.menualdia.tp3.modelo.Combo;
import com.menualdia.tp3.modelo.ElementoMenu;
import com.menualdia.tp3.modelo.MenuDiario;
import com.menualdia.tp3.modelo.Plato;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/** Reglas de negocio; no depende de la interfaz de consola. */
public final class GestorMenus {
    private final Map<Integer, ElementoMenu> catalogo = new LinkedHashMap<>();
    private final Map<LocalDate, MenuDiario> menus = new LinkedHashMap<>();
    private int proximoCodigo = 1;

    public Plato registrarPlato(String nombre, String categoria, BigDecimal precio, String descripcion) {
        Plato plato = new Plato(proximoCodigo, nombre, precio, categoria, descripcion);
        catalogo.put(proximoCodigo++, plato);
        return plato;
    }

    public Combo registrarCombo(String nombre, BigDecimal precio, List<Integer> codigosPlatos) {
        if (codigosPlatos == null) throw new IllegalArgumentException("Debe seleccionar platos para el combo.");
        List<Plato> platos = new ArrayList<>();
        for (int codigo : codigosPlatos) {
            ElementoMenu elemento = buscarPorCodigo(codigo);
            if (!(elemento instanceof Plato plato)) {
                throw new IllegalArgumentException("Un combo solo puede contener platos individuales.");
            }
            platos.add(plato);
        }
        Combo combo = new Combo(proximoCodigo, nombre, precio, platos);
        catalogo.put(proximoCodigo++, combo);
        return combo;
    }

    public ElementoMenu buscarPorCodigo(int codigo) {
        ElementoMenu elemento = catalogo.get(codigo);
        if (elemento == null) throw new NoSuchElementException("No existe un elemento con código " + codigo + ".");
        return elemento;
    }

    public List<ElementoMenu> buscarPorNombre(String texto) {
        if (texto == null || texto.isBlank()) throw new IllegalArgumentException("Ingrese una búsqueda.");
        String filtro = texto.trim().toLowerCase(java.util.Locale.ROOT);
        return listarCatalogo().stream()
                .filter(elemento -> elemento.getNombre().toLowerCase(java.util.Locale.ROOT).contains(filtro))
                .toList();
    }

    public List<ElementoMenu> listarCatalogo() {
        return catalogo.values().stream()
                .sorted(Comparator.comparing(ElementoMenu::getNombre, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    public MenuDiario crearMenu(LocalDate fecha) {
        if (fecha == null) throw new IllegalArgumentException("La fecha es obligatoria.");
        if (menus.containsKey(fecha)) throw new IllegalArgumentException("Ya existe un menú para esa fecha.");
        MenuDiario menu = new MenuDiario(fecha);
        menus.put(fecha, menu);
        return menu;
    }

    public MenuDiario obtenerMenu(LocalDate fecha) {
        MenuDiario menu = menus.get(fecha);
        if (menu == null) throw new NoSuchElementException("No hay menú registrado para " + fecha + ".");
        return menu;
    }

    public void agregarAlMenu(LocalDate fecha, int codigo) {
        obtenerMenu(fecha).agregar(buscarPorCodigo(codigo));
    }

    public void cambiarDisponibilidad(int codigo, boolean disponible) {
        buscarPorCodigo(codigo).setDisponible(disponible);
    }

    public void publicarMenu(LocalDate fecha) {
        obtenerMenu(fecha).publicar();
    }
}
