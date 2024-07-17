package com.callenge.foro.datos;

import com.callenge.foro.modelos.Cursos;
import com.callenge.foro.modelos.Topico;

import java.util.List;
import java.util.stream.Collectors;

public record DatosListaTopicos(Long id, String titulo, List<DatosListaMensajes> mensajes, String estatus, Cursos cursos) {

public DatosListaTopicos(Topico topico){
    this(topico.getId(),
            topico.getTitulo(),
            topico.getMensajes().stream()
                    .map(DatosListadoMensaje::new)
                    .collect(Collectors.toList()),
            topico.getEstatus(),
            topico.getCursos());
}

}
