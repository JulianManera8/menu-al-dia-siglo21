package com.menualdia.repository;

import com.menualdia.model.MenuPlato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuPlatoRepository extends JpaRepository<MenuPlato, Long> {
    List<MenuPlato> findByMenuIdOrderByPlatoNombreAsc(Long menuId);
    boolean existsByMenuIdAndPlatoId(Long menuId, Long platoId);
}
