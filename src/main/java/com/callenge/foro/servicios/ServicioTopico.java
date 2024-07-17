package com.callenge.foro.servicios;

import com.callenge.foro.controller.TopicoController;
import com.callenge.foro.datos.DatosActualizarTopico;
import com.callenge.foro.datos.DatosListaMensajes;
import com.callenge.foro.datos.DatosListaTopicos;
import com.callenge.foro.datos.DatosRegistoTopico;
import com.callenge.foro.modelos.Cursos;
import com.callenge.foro.modelos.Mensaje;
import com.callenge.foro.modelos.Topico;
import com.callenge.foro.repositorios.MensajeRepository;
import com.callenge.foro.repositorios.TopicoRepository;
import org.hibernate.query.Page;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.parser.Entity;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioTopico {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private MensajeRepository mensajeRepository;

    public Topico registrarTopico(DatosRegistoTopico datosRegistoTopico){
        if (topicoRepository.existsByTituloAndMensajes_contenido(datosRegistoTopico.titulo(),datosRegistoTopico.mensaje())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Topico nuevoTopico = new Topico(datosRegistoTopico);
        return topicoRepository.save(nuevoTopico);
    }

    public Page<DatosListaTopicos> listarTopicos(Pageable pageable){
        return topicoRepository.findAllActive(pageable).map(DatosListaTopicos::new);
    }

    public PagedModel<EntityModel<DatosListaTopicos>> convertirAPagedModel(Page<DatosListaTopicos> topicosPage, PagedResourcesAssembler<DatosListaTopicos> pagedResourcesAssembler, Pageable pageable){
        return pagedResourcesAssembler.toModel(topicosPage,
                topico -> EntityModel.of(topico,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TopicoController.class)
                                .listadoTopicos(pageable)).withSelfRel()));
    }
    public Page<DatosListaTopicos> buscarTopicosPorCurso(String nombreCurso, Pageable pageable){
        Cursos cursos;
        try{
            cursos = Cursos.valueOf(nombreCurso.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return topicoRepository.findByCursoAndStatusNotClosed(cursos, pageable).map(DatosListaTopicos::new);
    }

    public Optional<Topico> buscarTopicoPorId(Long id){
        return topicoRepository.findById(id);
    }

    @Transactional
    public void actualizarTopico(Long id, DatosActualizarTopico datosActualizarTopico){
        Topico topico = topicoRepository.findById(id)
                .orElseThrow() -> new ResponseStatusException((HttpStatus.NOT_FOUND));

        topico.actualizarTopico(datosActualizarTopico);
        topicoRepository.save(topico);
    }
    public DatosListaMensajes obtenerUltimoMensaje(Long id){
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<Mensaje> mensajes = topico.getMensajes();
        if (!mensajes.isEmpty()){
            Mensaje ultimoMensaje = mensajes.get(mensajes.size() -1);
            return new DatosListaMensajes(
                    ultimoMensaje.getId(),
                    ultimoMensaje.getContenido(),
                    ultimoMensaje.getFecha(),
                    ultimoMensaje.getAutor()
            );
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @Transactional
    public DatosListaMensajes agregarMensaje(Long id, DatosListaMensajes datosNuevoMensajes) {

        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Mensaje nuevoMensaje = new Mensaje(datosNuevoMensajes);

        topico.agregarMensaje(nuevoMensaje);

        topicoRepository.save(topico);
        return new DatosListaMensajes(nuevoMensaje);
    }
    @Transactional
    public void cerrarTopico(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        topico.cerrarTopico();

        topicoRepository.save(topico);
    }
    @Transactional
    public void eliminarMensaje(Long idTopico, Long idMensaje) {
        Topico topico = topicoRepository.findById(idTopico)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Mensaje mensaje = topico.getMensajes().stream()
                .filter(m -> m.getId().equals(idMensaje))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        topico.getMensajes().remove(mensaje);
        topicoRepository.save(topico);

        mensajeRepository.deleteById(idMensaje);
    }
}
