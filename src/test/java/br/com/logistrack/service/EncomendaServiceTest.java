package br.com.logistrack.service;

import br.com.logistrack.dto.encomenda.EncomendaInputDTO;
import br.com.logistrack.dto.encomenda.RastreioResponseDTO;
import br.com.logistrack.dto.encomenda.StatusUpdateDTO;
import br.com.logistrack.dto.usuario.AuthenticationDTO;
import br.com.logistrack.entity.Encomenda;
import br.com.logistrack.entity.Usuario;
import br.com.logistrack.entity.enums.StatusEncomenda;
import br.com.logistrack.entity.enums.TipoCargo;
import br.com.logistrack.exceptions.ResourceNotFoundException;
import br.com.logistrack.repository.EncomendaRepository;
import br.com.logistrack.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EncomendaServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private EncomendaRepository encomendaRepository;

    @Mock
    private  EnderecoRepository enderecoRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EncomendaService encomendaService;


    @DisplayName("Deve criar uma nova encomenda")
    @Test
    void deveCriarUmaNovaEncomenda() {
        EncomendaInputDTO dto = new EncomendaInputDTO("Remetente", "Destinatário", 3);
        EncomendaInputDTO encomendaCriada = encomendaService.create(1L, dto);

        assertNotNull(encomendaCriada);
        assertEquals("Remetente", encomendaCriada.getRemetente());
        assertEquals("Destinatário", encomendaCriada.getDestinatario());
        assertEquals(3, encomendaCriada.getPrazoEntrega());

        verify(encomendaRepository).save(any(Encomenda.class));
    }

    @DisplayName("Deve atualizar o status de uma encomenda")
    @Test
    void deveAtualizarStatusEncomenda() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@mail.com");
        usuario.setSenha("hash");
        usuario.setCargo(TipoCargo.USER);

        Encomenda encomenda = new Encomenda();
        encomenda.setId(1L);
        encomenda.setRemetente("Remetente");
        encomenda.setDestinatario("Destinatário");
        encomenda.setCodigoRastreio("123456789");
        encomenda.setDataPostagem(LocalDateTime.now());
        encomenda.setDataPrevisaoEntrega(LocalDate.now().plusDays(3));
        encomenda.setLocalizacaoAtual("Localização");
        encomenda.setStatus(StatusEncomenda.EM_PROCESSAMENTO);
        encomenda.setUsuario(usuario);

        StatusUpdateDTO dto = new StatusUpdateDTO("Nova localização", StatusEncomenda.ENTREGUE);

        AuthenticationDTO authDto = new AuthenticationDTO(usuario.getEmail(), "senha_hash"); // Ajuste conforme seu construtor

        when(encomendaRepository.findById(1L)).thenReturn(Optional.of(encomenda));
        when(encomendaRepository.save(any(Encomenda.class))).thenReturn(encomenda);

        when(objectMapper.convertValue(any(Encomenda.class), eq(StatusUpdateDTO.class)))
                .thenReturn(dto);

        when(objectMapper.convertValue(any(Usuario.class), eq(AuthenticationDTO.class)))
                .thenReturn(authDto);

        doNothing().when(emailService).sendEmail(any(), any(), any());

        StatusUpdateDTO novoStatus = encomendaService.update(1L, dto);

        assertNotNull(novoStatus);
        assertEquals("Nova localização", novoStatus.getNovaLocalizacao());

        assertEquals(StatusEncomenda.ENTREGUE, novoStatus.getNovoStatus());

        verify(encomendaRepository, times(2)).save(any(Encomenda.class));
        verify(emailService).sendEmail(any(), any(), any());
    }

    @DisplayName("Deve lançar erro ao tentar atualizar status de encomenda que não existe")
    @Test
    void deveLancarErroAoTentarAtualizarStatusEncomendaQueNaoExiste() {
        StatusUpdateDTO dto = new StatusUpdateDTO("Nova localização", StatusEncomenda.ENTREGUE);

        when(encomendaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> encomendaService.update(1L, dto));
        verify(encomendaRepository, never()).save(any(Encomenda.class));

    }

    @DisplayName("Deve deletar uma encomenda")
    @Test
    void deveDeletarEncomenda() {
        encomendaService.delete(1L);
        verify(encomendaRepository).deleteById(1L);
    }

    @DisplayName("Deve lançar erro ao tentar deletar encomenda que não existe")
    @Test
    void deveLancarErroAoTentarDeletarEncomendaQueNaoExiste() {
        when(encomendaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> encomendaService.delete(1L));
        verify(encomendaRepository, never()).deleteById(any());

    }
}