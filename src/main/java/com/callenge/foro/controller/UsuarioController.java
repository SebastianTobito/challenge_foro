package com.callenge.foro.controller;

import com.callenge.foro.datos.DatosNuevoUsuario;
import com.callenge.foro.modelos.Usuario;
import com.callenge.foro.servicios.ServicioUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private ServicioUsuario servicioUsuario;

    @PostMapping("/registro")
    @Operation
    public ResponseEntity<Usuario> restroUsuario(
            @Parameter(required = true)
            @Valid @RequestBody DatosNuevoUsuario datosNuevoUsuario){
        Usuario usuarioCreado = servicioUsuario.registroNuevoUsuario(datosNuevoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }
}

