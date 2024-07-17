package com.callenge.foro.modelos;

import com.callenge.foro.datos.DatosActualizarTopico;
import com.callenge.foro.datos.DatosRegistoTopico;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Mensaje> mensajes = new ArrayList<>();
    private LocalDateTime fecha;
    private String estatus;
    private String autor;
    @Enumerated(EnumType.STRING)
    private Cursos cursos;

    public Topico(DatosRegistoTopico datosRegistoTopico) {
        this.fecha = LocalDateTime.now();
        this.titulo = datosRegistoTopico.titulo();
        this.mensajes = new ArrayList<>();
        Mensaje mensaje = new Mensaje(datosRegistoTopico.mensaje(), this.autor);
        this.agregarMensaje(mensaje);
        this.estatus = "Abierto";
        this.autor = datosRegistoTopico.autor();
        this.cursos = datosRegistoTopico.cursos();
    }
    public void agregarMensaje(Mensaje mensaje){
        mensajes.add(mensaje);
        mensaje.setTopico(this);
    }
    public void actualizarTopico(DatosActualizarTopico datosActualizarTopico){
        if(datosActualizarTopico.mensaje() !=null){
            Mensaje mensaje = new Mensaje(datosActualizarTopico.mensaje(), datosActualizarTopico.autor());
            this.agregarMensaje(mensaje);
        }
        this.fecha = LocalDateTime.now();
        this.estatus = "Actualizado";
    }
    public void cerrarTopico(){
        this.estatus = "Cerrado";
    }
}
