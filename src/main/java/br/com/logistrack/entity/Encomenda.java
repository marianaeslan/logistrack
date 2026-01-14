package br.com.logistrack.entity;

import br.com.logistrack.entity.enums.StatusEncomenda;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Entity
@Table(name = "TB_ENCOMENDA")
@Getter
@Setter
public class Encomenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String codigoRastreio;

    String remetente;
    String destinatario;
    String localizacaoAtual;
    Integer tempoTransito = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    StatusEncomenda status;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime dataPostagem;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime dataEntrega;
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate dataPrevisaoEntrega;
    Boolean atrasado = false;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "encomenda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos;

    public long getTempoEmTransito() {
        if (dataPostagem == null) return 0;
        LocalDateTime diaAtual = LocalDateTime.now();
        if (this.dataEntrega != null) {
            return ChronoUnit.DAYS.between(dataPostagem, diaAtual);
        } else {
            return 0;
        }

    }

    public Boolean isAtrasado() {
        if (this.getDataPrevisaoEntrega() == null) {
            return false;
        }
        LocalDate diaAtual = LocalDate.now();
        return diaAtual.isAfter(ChronoLocalDate.from(this.getDataPrevisaoEntrega()));
    }


}
