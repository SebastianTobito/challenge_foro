package com.callenge.foro.repositorios;

import com.callenge.foro.modelos.Cursos;
import com.callenge.foro.modelos.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicoRepository extends JpaRepository<Topico, Long> {


    boolean existsByTituloAndMensajes_contenido(String titulo, String mensaje);


    @Query("SELECT t FROM Topico t WHERE t.status <> 'CERRADO'")
    Page<Topico> findAllActive(Pageable pageable);


    @Query("SELECT t FROM Topico t WHERE t.curso = :curso AND t.status <> 'CERRADO'")
    Page<Topico> findByCursoAndStatusNotClosed(@Param("curso") Cursos cursos, Pageable pageable);
}
