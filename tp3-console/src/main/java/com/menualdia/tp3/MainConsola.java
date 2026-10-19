package com.menualdia.tp3;

import com.menualdia.tp3.modelo.ElementoMenu;
import com.menualdia.tp3.modelo.MenuDiario;
import com.menualdia.tp3.servicio.GestorMenus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Interfaz de consola para demostrar POO, objetos, control de flujo y excepciones. */
public final class MainConsola {
    private final Scanner entrada;
    private final GestorMenus gestor;

    public MainConsola(Scanner entrada, GestorMenus gestor) {
        this.entrada = entrada;
        this.gestor = gestor;
    }

    public static void main(String[] args) {
        new MainConsola(new Scanner(System.in), new GestorMenus()).ejecutar();
    }

    public void ejecutar() {
        boolean continuar = true;
        while (continuar) { // Repetición
            imprimirOpciones();
            if (!entrada.hasNextLine()) break;
            String opcion = entrada.nextLine().trim();
            try {
                switch (opcion) { // Estructura de selección
                    case "1" -> registrarPlato();
                    case "2" -> registrarCombo();
                    case "3" -> listar();
                    case "4" -> buscar();
                    case "5" -> crearMenu();
                    case "6" -> agregarAlMenu();
                    case "7" -> consultarMenu();
                    case "8" -> cambiarDisponibilidad();
                    case "9" -> publicarMenu();
                    case "0" -> { continuar = false; System.out.println("Hasta luego."); }
                    default -> System.out.println("Opción no válida. Intentá nuevamente.");
                }
            } catch (IllegalArgumentException | IllegalStateException | NoSuchElementException e) {
                System.out.println("No se pudo completar la operación: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private void imprimirOpciones() {
        System.out.println("\n=== MENÚ AL DÍA | AP3 - POO ===");
        System.out.println("1. Registrar plato");
        System.out.println("2. Crear combo");
        System.out.println("3. Listar catálogo ordenado");
        System.out.println("4. Buscar por nombre");
        System.out.println("5. Crear menú diario");
        System.out.println("6. Agregar plato o combo al menú");
        System.out.println("7. Consultar menú por fecha");
        System.out.println("8. Cambiar disponibilidad");
        System.out.println("9. Publicar menú");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    private String leerTexto(String etiqueta) {
        System.out.print(etiqueta);
        if (!entrada.hasNextLine()) throw new IllegalArgumentException("No se recibió el dato solicitado.");
        String valor = entrada.nextLine().trim();
        if (valor.isBlank()) throw new IllegalArgumentException("El campo no puede estar vacío.");
        return valor;
    }

    private int leerEntero(String etiqueta) {
        try {
            return Integer.parseInt(leerTexto(etiqueta));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ingresá un número entero válido.");
        }
    }

    private BigDecimal leerPrecio(String etiqueta) {
        try {
            return new BigDecimal(leerTexto(etiqueta).replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ingresá un precio numérico válido.");
        }
    }

    private LocalDate leerFecha() {
        String texto = leerTexto("Fecha (AAAA-MM-DD): ");
        try {
            return LocalDate.parse(texto);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Fecha inválida: usá AAAA-MM-DD.");
        }
    }

    private void registrarPlato() {
        String nombre = leerTexto("Nombre: ");
        String categoria = leerTexto("Categoría: ");
        BigDecimal precio = leerPrecio("Precio: ");
        String descripcion = leerTexto("Descripción: ");
        ElementoMenu nuevo = gestor.registrarPlato(nombre, categoria, precio, descripcion);
        System.out.println("Creado: " + nuevo);
    }

    private void registrarCombo() {
        String nombre = leerTexto("Nombre del combo: ");
        BigDecimal precio = leerPrecio("Precio: ");
        String ids = leerTexto("Códigos de platos separados por coma (mínimo 2): ");
        List<Integer> codigos;
        try {
            codigos = Arrays.stream(ids.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Los códigos deben ser números enteros.");
        }
        System.out.println("Creado: " + gestor.registrarCombo(nombre, precio, codigos));
    }

    private void listar() {
        imprimirElementos(gestor.listarCatalogo());
    }

    private void buscar() {
        imprimirElementos(gestor.buscarPorNombre(leerTexto("Nombre a buscar: ")));
    }

    private void imprimirElementos(List<ElementoMenu> elementos) {
        if (elementos.isEmpty()) {
            System.out.println("No se encontraron resultados.");
        } else {
            for (ElementoMenu elemento : elementos) { // Repetición sobre lista
                System.out.println(elemento); // Despacho polimórfico
            }
        }
    }

    private void crearMenu() {
        MenuDiario menu = gestor.crearMenu(leerFecha());
        System.out.println("Menú creado en estado " + menu.getEstado() + ".");
    }

    private void agregarAlMenu() {
        LocalDate fecha = leerFecha();
        int codigo = leerEntero("Código del plato o combo: ");
        gestor.agregarAlMenu(fecha, codigo);
        System.out.println("Elemento incorporado al menú.");
    }

    private void consultarMenu() {
        MenuDiario menu = gestor.obtenerMenu(leerFecha());
        System.out.println("Menú " + menu.getFecha() + " | " + menu.getEstado());
        imprimirElementos(menu.getElementos());
    }

    private void cambiarDisponibilidad() {
        int codigo = leerEntero("Código del elemento: ");
        String respuesta = leerTexto("¿Disponible? (s/n): ");
        if (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")) {
            throw new IllegalArgumentException("Respondé s o n.");
        }
        gestor.cambiarDisponibilidad(codigo, respuesta.equalsIgnoreCase("s"));
        System.out.println("Disponibilidad actualizada.");
    }

    private void publicarMenu() {
        LocalDate fecha = leerFecha();
        gestor.publicarMenu(fecha);
        System.out.println("Menú publicado correctamente.");
    }
}
