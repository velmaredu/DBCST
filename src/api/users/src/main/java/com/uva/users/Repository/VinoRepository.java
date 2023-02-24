package com.uva.users.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.uva.users.Model.Vino;

public interface VinoRepository extends JpaRepository<Vino, Integer> {
    Optional<Vino> findByNombreComercial(String nombre);

    List<Vino> findByPrecioBetween(Float precio1, Float precio2);

    boolean existsVinoByDenominacionAndCategoria(String denomiacion, String categoria);

    @Transactional
    // Long deleteByDenominacionAndCategoria(String denominacion, String categoria);
    List<Vino> deleteByDenominacionAndCategoria(String denominacion, String categoria);

    int countByDenominacion(String denominacion);

    int countByDenominacionNot(String denominacion);
}
