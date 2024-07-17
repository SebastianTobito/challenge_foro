package com.callenge.foro.repositorios;

import com.callenge.foro.modelos.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensajeRepository  extends JpaRepository<Mensaje, Long> {
    void deleteById(Long id);
}
