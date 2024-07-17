package com.callenge.foro.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Campo obligatorio")
    private String nombre;
    @NotNull(message = "Campo obligatorio")
    @Email(message = "Campo con direccion de correo valida")
    private String email;
    @NotNull
    @Size(min = 6, max = 10, message = "Contraseña entre 6 y 10 caracteres")
    private String clave;
    @CreationTimestamp
    @Column(name = "fecha_creado",  nullable = false, updatable = false)
    private LocalDateTime fechaCreado;
    @UpdateTimestamp
    @Column(name = "fecha_actualizado")
    private LocalDateTime fechaActualizado;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String obtenerClave(){
        return clave;
    }
    @Override
    public String obtenerUsuario(){
        return email;
    }
    @Override
    public boolean usuarioNoExpirado(){
        return true;
    }
    @Override
    public boolean usuarioNoBloqueado(){
        return true;
    }
    @Override
    public boolean credencialesNoExpiradas(){
        return true;
    }
    @Override
    public boolean activada(){
        return true;
    }

}
