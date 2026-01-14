package br.com.logistrack.service;

import br.com.logistrack.client.ViaCepClient;
import br.com.logistrack.dto.encomenda.EncomendaInputDTO;
import br.com.logistrack.dto.encomenda.RastreioResponseDTO;
import br.com.logistrack.dto.encomenda.StatusUpdateDTO;
import br.com.logistrack.dto.usuario.UsuarioLoginDTO;
import br.com.logistrack.entity.Encomenda;
import br.com.logistrack.entity.Usuario;
import br.com.logistrack.entity.enums.StatusEncomenda;
import br.com.logistrack.exceptions.ResourceNotFoundException;
import br.com.logistrack.repository.EncomendaRepository;
import br.com.logistrack.repository.UsuarioRepository;
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
    private final UsuarioRepository usuarioRepository;

    public EncomendaInputDTO create (Long idUsuario, EncomendaInputDTO encomendaInputDTO) throws ResourceNotFoundException {
        Encomenda novaEncomenda = new Encomenda();
        try {
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
            
            Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
            novaEncomenda.setUsuario(usuario);

            Encomenda encomendaSalva = encomendaRepository.save(novaEncomenda);
            return objectMapper.convertValue(encomendaSalva, EncomendaInputDTO.class);
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Encomenda não cadastrada");
        }

    }

    public StatusUpdateDTO update(long id, StatusUpdateDTO statusUpdateDTO) throws ResourceNotFoundException {
        Encomenda encomenda = encomendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Encomenda com ID " + id + " não encontrada"));

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
                .orElseThrow(() -> new ResourceNotFoundException("Encomenda com código de rastreio " + codigoRastreio + " não encontrada"));
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
                .orElseThrow(() -> new ResourceNotFoundException("Encomenda com ID " + id + " não encontrada"));
        encomenda.setStatus(novoStatus);
        encomendaRepository.save(encomenda);

        Usuario usuario = encomenda.getUsuario();
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuário não associado a esta encomenda");
        }

        Map<String, Object>dados = new HashMap<>();
        dados.put("nomeCliente", encomenda.getDestinatario());
        dados.put("nomeLoja", encomenda.getRemetente());
        dados.put("codigoRastreio", encomenda.getCodigoRastreio());
        dados.put("localizacaoAtual", encomenda.getLocalizacaoAtual());
        dados.put("novoStatus", novoStatus.getDescricao());
        dados.put("dataPrevisaoEntrega", encomenda.getDataPrevisaoEntrega().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dados.put("linkRastreio", "http://localhost:8080/rastreio/" + encomenda.getCodigoRastreio());

        emailService.sendEmail("Atualização de encomenda", dados, objectMapper.convertValue(usuario, UsuarioLoginDTO.class));
    }

}
