package com.menualdia.controller;

import com.menualdia.model.Menu;
import com.menualdia.repository.CategoriaRepository;
import com.menualdia.service.MenuService;
import com.menualdia.service.PlatoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class MenuController {
    private final PlatoService platoService;
    private final MenuService menuService;
    private final CategoriaRepository categoriaRepository;

    public MenuController(PlatoService platoService,
                          MenuService menuService,
                          CategoriaRepository categoriaRepository) {
        this.platoService = platoService;
        this.menuService = menuService;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/")
    public String inicio(@RequestParam(required = false) String fecha, Model model) {
        LocalDate fechaSeleccionada = (fecha == null || fecha.isBlank())
                ? LocalDate.now()
                : LocalDate.parse(fecha);

        Menu menu = menuService.obtenerOCrear(fechaSeleccionada);
        model.addAttribute("fecha", fechaSeleccionada);
        model.addAttribute("menu", menu);
        model.addAttribute("items", menuService.items(menu.getId()));
        model.addAttribute("platos", platoService.listarActivos());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "index";
    }

    @PostMapping("/platos")
    public String crearPlato(@RequestParam String nombre,
                             @RequestParam(required = false) String descripcion,
                             @RequestParam BigDecimal precioBase,
                             @RequestParam Long categoriaId) {
        platoService.crear(nombre, descripcion, precioBase, categoriaId);
        return "redirect:/";
    }

    @PostMapping("/menu/{menuId}/platos")
    public String agregarPlato(@PathVariable Long menuId,
                               @RequestParam Long platoId,
                               @RequestParam BigDecimal precioDia,
                               @RequestParam String fecha) {
        menuService.agregarPlato(menuId, platoId, precioDia);
        return "redirect:/?fecha=" + fecha;
    }

    @PostMapping("/menu/items/{itemId}/disponibilidad")
    public String cambiarDisponibilidad(@PathVariable Long itemId,
                                        @RequestParam String fecha) {
        menuService.cambiarDisponibilidad(itemId);
        return "redirect:/?fecha=" + fecha;
    }

    @PostMapping("/menu/{menuId}/publicar")
    public String publicar(@PathVariable Long menuId,
                           @RequestParam String fecha) {
        menuService.publicar(menuId);
        return "redirect:/?fecha=" + fecha;
    }
}
