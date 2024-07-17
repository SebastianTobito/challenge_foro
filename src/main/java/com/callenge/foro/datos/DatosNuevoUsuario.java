package com.callenge.foro.datos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DatosNuevoUsuario(@NotBlank
                                String nombre,
                                @NotBlank
                                @Email
                                String email,
                                @NotBlank
                                @Size(min = 6, max = 10, message = "Debe tener de 6 a 10 caracteres")
                                String clave){
}
