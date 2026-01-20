package br.com.logistrack.entity;

import br.com.logistrack.entity.enums.TipoCargo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "email")
    String email;

    String senha;

    @Column(name="cargo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCargo cargo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")
    private Endereco endereco;

    public Usuario(String email, String encryptedPassword, TipoCargo cargo, String cep) {
        this.email = email;
        this.senha = encryptedPassword;
        this.cargo = cargo;

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.cargo.name()));
    }

    @Override public boolean isAccountNonExpired() {
        return true;
    }

    @Override public String getPassword() {
        return this.senha;
    }

    @Override public String getUsername() {
        return this.email;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Encomenda> encomendas;
}
