package com.callenge.foro.datos;

import jakarta.validation.constraints.NotBlank;

public record DatosMensajeNuevo(@NotBlank String contenido,
                                @NotBlank String autor) {
}
