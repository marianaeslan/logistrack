package br.com.logistrack.entity;

import br.com.logistrack.entity.enums.TipoCargo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Table(name = "TB_USUARIO")
@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email")
    String email;

    String senha;

    @Column(name="Cargo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCargo cargo;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.cargo.name()));
    }

    @Override public boolean isAccountNonExpired() {
        return true;
    }

    @Override public String getPassword() {
        return "";
    }

    @Override public String getUsername() {
        return "";
    }

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Encomenda> encomendas;
}
