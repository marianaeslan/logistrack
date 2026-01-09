package br.com.logistrack.service;

import br.com.logistrack.client.ViaCepClient;
import br.com.logistrack.dto.encomenda.EncomendaInputDTO;
import br.com.logistrack.dto.encomenda.RastreioResponseDTO;
import br.com.logistrack.dto.encomenda.StatusUpdateDTO;
import br.com.logistrack.dto.endereco.EnderecoResponseDTO;
import br.com.logistrack.entity.Encomenda;
import br.com.logistrack.entity.enums.StatusEncomenda;
import br.com.logistrack.exceptions.RegraDeNegocioException;
import br.com.logistrack.repository.EncomendaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
public class EncomendaService {
    private final EncomendaRepository encomendaRepository;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;
    private final ViaCepClient viaCepClient;

    public EncomendaInputDTO create (EncomendaInputDTO encomendaInputDTO) throws RegraDeNegocioException {
        Encomenda novaEncomenda = new Encomenda();
        String cep = encomendaInputDTO.getCep();
        // definindo endereço de entrega
        try {
            EnderecoResponseDTO endereco = viaCepClient.getByCep(cep);
            novaEncomenda.setLogradouro(endereco.getLogradouro());
            if (endereco.getLogradouro() == null || endereco == null || endereco.getUf() == null) {
                throw new RegraDeNegocioException("CEP inválido");
            }
            novaEncomenda.setComplemento(encomendaInputDTO.getComplemento());
            novaEncomenda.setBairro(endereco.getBairro());
            novaEncomenda.setCep(endereco.getCep());
            novaEncomenda.setUf(endereco.getUf());
            // definindo prazo de entrega
            LocalDateTime dataAgora = LocalDateTime.now();
            LocalDate dataPrevisao = dataAgora.plusDays(encomendaInputDTO.getPrazoEntrega()).toLocalDate();
            novaEncomenda.setRemetente(encomendaInputDTO.getRemetente());
            novaEncomenda.setDestinatario(encomendaInputDTO.getDestinatario());
            novaEncomenda.setCodigoRastreio(UUID.randomUUID().toString());
            novaEncomenda.setDataPostagem(dataAgora);
            novaEncomenda.setDataPrevisaoEntrega(dataPrevisao);
            novaEncomenda.setLocalizacaoAtual("Centro de Distribuição");
            novaEncomenda.setStatus(StatusEncomenda.EM_PROCESSAMENTO);
            novaEncomenda.setEmail(encomendaInputDTO.getEmail());

            Encomenda encomendaSalva = encomendaRepository.save(novaEncomenda);
            return objectMapper.convertValue(encomendaSalva, EncomendaInputDTO.class);
        } catch (RegraDeNegocioException e){
            throw new RegraDeNegocioException("CEP inválido");
        }

    }

    public StatusUpdateDTO update(long id, StatusUpdateDTO statusUpdateDTO) throws RegraDeNegocioException {
        Encomenda encomenda = encomendaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Encomenda não encontrada"));

        if (statusUpdateDTO.getNovaLocalizacao() != null) {
            encomenda.setLocalizacaoAtual(statusUpdateDTO.getNovaLocalizacao());
        }

        if (statusUpdateDTO.getNovoStatus() != null) {
            encomenda.setStatus(statusUpdateDTO.getNovoStatus());
            if (statusUpdateDTO.getNovoStatus() == StatusEncomenda.ENTREGUE) {
                encomenda.setDataEntrega(LocalDateTime.now());
            }
        }
        if (encomenda.isAtrasado()) {
            encomenda.setStatus(StatusEncomenda.ATRASADO);
        }
        encomendaRepository.save(encomenda);
        atualizarStatusEmail(id, encomenda.getStatus());

        return objectMapper.convertValue(encomenda, StatusUpdateDTO.class);
    }


    public List<Encomenda> list() {
        return encomendaRepository.findAll();
    }

    public Optional<RastreioResponseDTO> findByRastreio(String codigoRastreio) {
        Encomenda encomenda = encomendaRepository.findByCodigoRastreio(codigoRastreio)
                .orElseThrow(() -> new RegraDeNegocioException("Encomenda não encontrada"));
        RastreioResponseDTO rastreioDTO = new RastreioResponseDTO();
        rastreioDTO.setCodigoRastreio(encomenda.getCodigoRastreio());
        rastreioDTO.setStatusAtual(encomenda.getStatus().getDescricao());
        rastreioDTO.setLocalizacaoAtual(encomenda.getLocalizacaoAtual());
        rastreioDTO.setTempoEmTransito(encomenda.getTempoEmTransito());


        return Optional.of(rastreioDTO);
    }

    public void delete (long id) {
        encomendaRepository.deleteById(id);
    }

    public void atualizarStatusEmail(Long id, StatusEncomenda novoStatus) {
        Encomenda encomenda = encomendaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Encomenda não encontrada"));
        encomenda.setStatus(novoStatus);
        encomendaRepository.save(encomenda);

        Map<String, Object>dados = new HashMap<>();
        dados.put("nomeCliente", encomenda.getDestinatario());
        dados.put("nomeLoja", encomenda.getRemetente());
        dados.put("codigoRastreio", encomenda.getCodigoRastreio());
        dados.put("localizacaoAtual", encomenda.getLocalizacaoAtual());
        dados.put("novoStatus", novoStatus.getDescricao());
        dados.put("dataPrevisaoEntrega", encomenda.getDataPrevisaoEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dados.put("linkRastreio", "http://localhost:8080/rastreio/" + encomenda.getCodigoRastreio());
        dados.put("emailCliente", encomenda.getEmail());

        emailService.sendEmail("Atualização de Encomenda", dados, objectMapper.convertValue(encomenda, EncomendaInputDTO.class));
    }

}
