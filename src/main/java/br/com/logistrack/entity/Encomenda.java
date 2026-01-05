package br.com.logistrack.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

// TODO -> Criar Classe encomenda com os seguintes atributos:
// TODO -> Long id (PK, Gerada automaticamente).
// TODO -> String codigoRastreio (Ex: "BR123456").
// TODO -> String remetente (Quem mandou).
// TODO -> String destinatario (Quem recebe).
// TODO -> String localizacaoAtual (Ex: "CD São Paulo").
// TODO -> String status (Ex: "POSTADO", "EM_TRANSITO", "ENTREGUE").
// TODO -> LocalDateTime dataPostagem (Data que entrou no sistema).
// TODO -> LocalDateTime dataPrevisaoEntrega (Data esperada).

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")

    StatusEncomenda status;
    LocalDateTime dataPostagem;
    LocalDateTime dataEntrega;
    LocalDateTime dataPrevisaoEntrega;

    public long getTempoEmTransito() {
        if (dataPostagem == null) return 0;

        LocalDateTime dataFinal = LocalDateTime.now();

        if (this.dataEntrega != null) {
            dataFinal = this.dataEntrega.toLocalDate().atStartOfDay();
        }

        return ChronoUnit.DAYS.between(dataPostagem, dataFinal);
    }
}
