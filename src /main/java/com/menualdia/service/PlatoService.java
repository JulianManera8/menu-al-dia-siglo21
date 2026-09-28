package com.menualdia.service;

import com.menualdia.model.Categoria;
import com.menualdia.model.Plato;
import com.menualdia.repository.CategoriaRepository;
import com.menualdia.repository.PlatoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PlatoService {
    private final PlatoRepository platoRepository;
    private final CategoriaRepository categoriaRepository;

    public PlatoService(PlatoRepository platoRepository, CategoriaRepository categoriaRepository) {
        this.platoRepository = platoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Plato> listarActivos() {
        return platoRepository.findByActivoTrueOrderByNombreAsc();
    }

    public Plato crear(String nombre, String descripcion, BigDecimal precioBase, Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Categoría inexistente"));

        Plato plato = new Plato();
        plato.setNombre(nombre);
        plato.setDescripcion(descripcion);
        plato.setPrecioBase(precioBase);
        plato.setCategoria(categoria);
        plato.setActivo(true);
        return platoRepository.save(plato);
    }
}
