package com.callenge.foro.servicios;

import com.callenge.foro.datos.DatosNuevoUsuario;
import com.callenge.foro.modelos.Usuario;
import com.callenge.foro.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuario {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public Usuario registroNuevoUsuario(DatosNuevoUsuario datosNuevoUsuario){
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(datosNuevoUsuario.nombre());
        nuevoUsuario.setEmail(datosNuevoUsuario.email());
        nuevoUsuario.setClave(bCryptPasswordEncoder.encode(datosNuevoUsuario.clave()));
        return usuarioRepository.save(nuevoUsuario);
    }
}
