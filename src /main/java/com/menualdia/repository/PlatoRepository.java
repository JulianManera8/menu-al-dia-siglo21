package com.menualdia.repository;

import com.menualdia.model.Plato;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlatoRepository extends JpaRepository<Plato, Long> {
    List<Plato> findByActivoTrueOrderByNombreAsc();
}
