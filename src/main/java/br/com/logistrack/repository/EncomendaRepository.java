package br.com.logistrack.repository;

import br.com.logistrack.entity.Encomenda;
import br.com.logistrack.entity.StatusEncomenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EncomendaRepository extends JpaRepository<Encomenda, Long> {
    List<Encomenda> findByStatus(StatusEncomenda status);
    Optional<Encomenda> findByCodigoRastreio(String codigoRastreio);
    List<Encomenda> findByRemetente(String remetente);
    List<Encomenda> findByDestinatario(String destinatario);
    List<Encomenda> findByLocalizacaoAtual(String localizacaoAtual);
    List<Encomenda> findByDataPostagem(LocalDateTime dataPostagem);
    List<Encomenda> findByDataPrevisaoEntrega(LocalDateTime dataPrevisaoEntrega);
}
