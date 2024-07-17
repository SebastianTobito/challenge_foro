package com.callenge.foro.datos;

import com.callenge.foro.modelos.Cursos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistoTopico(@NotBlank
                                 String titulo,
                                 @NotBlank
                                 String mensaje,
                                 @NotBlank
                                 String autor,
                                 @NotNull
                                 Cursos cursos) {
}
