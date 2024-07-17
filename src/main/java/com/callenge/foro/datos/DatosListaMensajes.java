package com.callenge.foro.datos;

import com.callenge.foro.modelos.Mensaje;

import java.time.LocalDateTime;

public record DatosListaMensajes(Long id,
                                 String contenido,
                                 LocalDateTime fecha,
                                 String autor) {
    public DatosListaMensajes(Mensaje mensaje){
        this(mensaje.getId(),
                mensaje.getContenido(),
                mensaje.getFecha(),
                mensaje.getAutor());
    }

    public DatosListaMensajes(Long id, String contenido, LocalDateTime fecha, String autor){
        this.id = id;
        this.contenido = contenido;
        this.fecha = fecha;
        this.autor = autor;
    }
}
