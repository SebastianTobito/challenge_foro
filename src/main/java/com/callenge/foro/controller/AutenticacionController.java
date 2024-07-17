package com.callenge.foro.controller;


import com.callenge.foro.datos.DatosAutenticarUsuario;
import com.callenge.foro.modelos.Usuario;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private tokenService tokenService;

    @PostMapping
    public ResponseEntity usuarioAtenticacion(@Parameter(description ="Datps de autenticación de usuario", required = true)
                                              @RequestBody @Valid DatosAutenticarUsuario datosAutenticarUsuario){
   try{
       UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
               datosAutenticarUsuario.email(),
               datosAutenticarUsuario.clave()
       );
       var usuarioAutenticado = authenticationManager.authenticate(authenticationToken);
       var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

   }catch (AuthenticationException e){
       return ResponseEntity.status(401).body("Fallo en autenticar");

   }
    }

}
