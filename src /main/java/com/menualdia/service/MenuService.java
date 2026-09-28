package com.menualdia.service;

import com.menualdia.model.*;
import com.menualdia.repository.MenuPlatoRepository;
import com.menualdia.repository.MenuRepository;
import com.menualdia.repository.PlatoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuPlatoRepository menuPlatoRepository;
    private final PlatoRepository platoRepository;

    public MenuService(MenuRepository menuRepository,
                       MenuPlatoRepository menuPlatoRepository,
                       PlatoRepository platoRepository) {
        this.menuRepository = menuRepository;
        this.menuPlatoRepository = menuPlatoRepository;
        this.platoRepository = platoRepository;
    }

    public Menu obtenerOCrear(LocalDate fecha) {
        return menuRepository.findByFecha(fecha).orElseGet(() -> {
            Menu menu = new Menu();
            menu.setFecha(fecha);
            menu.setEstado(EstadoMenu.BORRADOR);
            return menuRepository.save(menu);
        });
    }

    public List<MenuPlato> items(Long menuId) {
        return menuPlatoRepository.findByMenuIdOrderByPlatoNombreAsc(menuId);
    }

    @Transactional
    public void agregarPlato(Long menuId, Long platoId, BigDecimal precioDia) {
        if (menuPlatoRepository.existsByMenuIdAndPlatoId(menuId, platoId)) {
            throw new IllegalArgumentException("El plato ya forma parte del menú");
        }

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menú inexistente"));
        Plato plato = platoRepository.findById(platoId)
                .orElseThrow(() -> new IllegalArgumentException("Plato inexistente"));

        MenuPlato item = new MenuPlato();
        item.setMenu(menu);
        item.setPlato(plato);
        item.setPrecioDia(precioDia);
        item.setDisponible(true);
        menuPlatoRepository.save(item);
    }

    @Transactional
    public void cambiarDisponibilidad(Long itemId) {
        MenuPlato item = menuPlatoRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Ítem inexistente"));
        item.setDisponible(!item.isDisponible());
        menuPlatoRepository.save(item);
    }

    @Transactional
    public void publicar(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menú inexistente"));

        if (menuPlatoRepository.findByMenuIdOrderByPlatoNombreAsc(menuId).isEmpty()) {
            throw new IllegalStateException("No se puede publicar un menú sin platos");
        }

        menu.setEstado(EstadoMenu.PUBLICADO);
        menu.setFechaPublicacion(LocalDateTime.now());
        menuRepository.save(menu);
    }
}
