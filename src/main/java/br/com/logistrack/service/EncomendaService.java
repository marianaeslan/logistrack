package br.com.logistrack.service;

import br.com.logistrack.dto.encomenda.EncomendaInputDTO;
import br.com.logistrack.dto.encomenda.RastreioResponseDTO;
import br.com.logistrack.entity.Encomenda;
import br.com.logistrack.entity.StatusEncomenda;
import br.com.logistrack.exceptions.RegraDeNegocioException;
import br.com.logistrack.repository.EncomendaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EncomendaService {
    private final EncomendaRepository encomendaRepository;
    private final ObjectMapper objectMapper;

    public EncomendaInputDTO create (EncomendaInputDTO encomendaInputDTO) throws RegraDeNegocioException {
        Encomenda novaEncomenda = new Encomenda();
        novaEncomenda.setRemetente(encomendaInputDTO.getRemetente());
        novaEncomenda.setDestinatario(encomendaInputDTO.getDestinatario());
        novaEncomenda.setCodigoRastreio(UUID.randomUUID().toString());
        novaEncomenda.setDataPostagem(LocalDateTime.now());
        novaEncomenda.setStatus(StatusEncomenda.EM_PROCESSAMENTO);
        encomendaRepository.save(novaEncomenda);
        return encomendaInputDTO;
    }

    public EncomendaInputDTO update (long id, EncomendaInputDTO encomendaInputDTO) throws RegraDeNegocioException {
        Encomenda encomenda = encomendaRepository.findById(id).orElseThrow (() -> new RegraDeNegocioException(
                "Encomenda não encontrada"));
        encomenda.setLocalizacaoAtual(encomenda.getLocalizacaoAtual());
        encomenda.setStatus(encomenda.getStatus());
        encomendaRepository.save(encomenda);
        return encomendaInputDTO;
    }

    public List<Encomenda> list() {
        return encomendaRepository.findAll().stream()
                .map(encomenda -> objectMapper.convertValue(encomenda, Encomenda.class))
                .toList();
    }

    public Optional<RastreioResponseDTO> findByRastreio(String codigoRastreio) {
        Encomenda encomenda = encomendaRepository.findByCodigoRastreio(codigoRastreio)
                .orElseThrow(() -> new RegraDeNegocioException("Encomenda não encontrada"));
        RastreioResponseDTO rastreioDTO = new RastreioResponseDTO();
        rastreioDTO.setCodigoRastreio(encomenda.getCodigoRastreio());
        rastreioDTO.setStatusAtual(encomenda.getStatus().toString());
        rastreioDTO.setLocalizacaoAtual(encomenda.getLocalizacaoAtual());
        rastreioDTO.setTempoEmTransito(encomenda.getTempoEmTransito());
        rastreioDTO.setAtrasado(encomenda.getDataPrevisaoEntrega().isAfter(LocalDateTime.now()));
        return Optional.of(rastreioDTO);
    }

    public void delete (long id) {
        encomendaRepository.deleteById(id);
    }

}
