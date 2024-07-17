package com.callenge.foro.modelos;

import com.callenge.foro.datos.DatosMensajeNuevo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Mensaje")
@Table(name = "mensajes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Mensaje {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenido;
    private LocalDateTime fecha;
    private String autor;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topico_id")
    @JsonBackReference
    private Topico topico;

    public Mensaje(String contenido, LocalDateTime fecha, String autor) {
        this.contenido = contenido;
        this.fecha = fecha;
        this.autor = autor;
    }
    public Mensaje (DatosMensajeNuevo datosMensajeNuevo){}
}
