package br.com.logistrack.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;


@Entity
@Table(name = "TB_ENCOMENDA")
@Data
public class Encomenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String codigoRastreio;

    String remetente;
    String destinatario;
    String localizacaoAtual;
    Integer tempoTransito;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    StatusEncomenda status;

    LocalDateTime dataPostagem;
    LocalDateTime dataEntrega;
    LocalDateTime dataPrevisaoEntrega;
    Boolean atrasado = false;

    public long getTempoEmTransito() {
        if (dataPostagem == null) return 0;
        LocalDateTime dataFinal = LocalDateTime.now();
        if (this.dataEntrega != null) {
            dataFinal = this.dataEntrega.toLocalDate().atStartOfDay();
        }
        return ChronoUnit.DAYS.between(dataPostagem, dataFinal);
    }

    public Boolean isAtrasado() {
        if (this.getDataPrevisaoEntrega() == null) {
            return false;
        }
        LocalDate diaAtual = LocalDate.now();
        if (diaAtual.isAfter(ChronoLocalDate.from(this.getDataPrevisaoEntrega()))) {
            return true;
        } else {
            return false;
        }
    }
}
