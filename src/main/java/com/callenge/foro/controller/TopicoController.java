package com.callenge.foro.controller;


import com.callenge.foro.datos.DatosActualizarTopico;
import com.callenge.foro.datos.DatosListaMensajes;
import com.callenge.foro.datos.DatosListaTopicos;
import com.callenge.foro.datos.DatosRegistoTopico;
import com.callenge.foro.modelos.Topico;
import com.callenge.foro.servicios.ServicioTopico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private ServicioTopico servicioTopico;

    @Autowired
    private PagedResourcesAssembler<DatosListaTopicos> pagedResourcesAssembler;

    @PostMapping
    @Operation
    public ResponseEntity<DatosListaTopicos> registrarTopico(
            @Valid @RequestBody DatosRegistoTopico datosRegistoTopico,
            UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = servicioTopico.registrarTopico(datosRegistoTopico);

        URI uri = uriComponentsBuilder.path("/topicos/{id}")
                .buildAndExpand(topico.getId())
                .toUri();
        return ResponseEntity.created(uri).body(datosRegistoTopico);
    }

    @GetMapping
    @Operation
    public ResponseEntity<PagedModel<EntityModel<DatosListaTopicos>>> listadoTopicos(
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<DatosListaTopicos> topicosPage = servicioTopico.listarTopicos(pageable);

        PagedModel<EntityModel<DatosListaTopicos>> pagedModel = servicioTopico.convertirAPagedModel(topicosPage,
                pagedResourcesAssembler, pageable);

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/buscar")
    @Operation
    public ResponseEntity<PagedModel<EntityModel<DatosListaTopicos>>> buscarTopicosPorCurso(
            @Parameter(required = true)
            @RequestParam(name = "curso") String nombreCurso,
            @Parameter
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.ASC) Pageable paginacion) {

        Page<DatosListaTopicos> datosListadoTopicoPage = servicioTopico.buscarTopicosPorCurso(nombreCurso, paginacion);

        PagedModel<EntityModel<DatosListaTopicos>> pagedModel = servicioTopico.convertirAPagedModel(datosListadoTopicoPage,
                pagedResourcesAssembler, paginacion);

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    @Operation
    public ResponseEntity<EntityModel<Topico>> buscarDetalleTopicoPorId(
            @Parameter(required = true) @PathVariable Long id) {
        Optional<Topico> optionalTopico = servicioTopico.buscarTopicoPorId(id);

        if (optionalTopico.isPresent()) {
            Topico topico = optionalTopico.get();
            return ResponseEntity.ok(EntityModel.of(topico));
        } else {

            return ResponseEntity.notFound().build();
        }

    }
    @PutMapping("/{id}")
    @Operation
    public ResponseEntity <DatosListaMensajes> actualizarTopico(
            @Parameter(required = true) @PathVariable Long id,
            @Valid @RequestBody DatosActualizarTopico datosActualizarTopico){

        servicioTopico.actualizarTopico(id, datosActualizarTopico);

       DatosListaMensajes datosUltimoMensaje = servicioTopico.obtenerUltimoMensaje(id);

        return ResponseEntity.ok(datosUltimoMensaje);
    }

    @PostMapping("/{id}/mensajes")
    @Operation
    public ResponseEntity<DatosListaMensajes> agregarMensaje(
            @Parameter(required = true)
            @PathVariable Long id,
            @Parameter(required = true)
            @Valid @RequestBody DatosListaMensajes datosNuevoMensaje) {
        DatosListaMensajes nuevoMensaje = servicioTopico.agregarMensaje(id, datosNuevoMensaje);
        return ResponseEntity.ok().body(nuevoMensaje);
    }
    @DeleteMapping("/{id}")
    @Operation
    @Transactional
    public ResponseEntity<String> cerrarTopico(
            @Parameter( required = true) @PathVariable Long id) {
        servicioTopico.cerrarTopico(id);
        return ResponseEntity.ok("Tópico cerrado");
    }
    @DeleteMapping("/{idTopico}/mensajes/{idMensaje}")
    @Operation
    public ResponseEntity<String> eliminarMensaje(
            @Parameter(required = true)
            @PathVariable Long idTopico,
            @Parameter( required = true)
            @PathVariable Long idMensaje) {
        servicioTopico.eliminarMensaje(idTopico, idMensaje);
        return ResponseEntity.ok("Mensaje eliminado ");
    }

}
