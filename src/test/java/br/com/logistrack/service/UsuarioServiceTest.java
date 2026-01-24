package br.com.logistrack.service;

import br.com.logistrack.dto.usuario.RegisterDTO;
import br.com.logistrack.entity.Endereco;
import br.com.logistrack.entity.Usuario;
import br.com.logistrack.entity.enums.TipoCargo;
import br.com.logistrack.repository.EnderecoRepository;
import br.com.logistrack.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;


    @DisplayName("Deve criar um usuário com senha criptografada e cargo padrão USER")
    @Test
    void deveCriarUsuarioComCargoPadraoUser() {
        RegisterDTO dto = new RegisterDTO("teste@mail.com", "senha123", null);

        when(passwordEncoder.encode("senha123")).thenReturn("hash");
        Usuario novoUsuario = usuarioService.create(dto);

        assertNotNull(novoUsuario);
        assertEquals("teste@mail.com", novoUsuario.getEmail());
        assertEquals("hash", novoUsuario.getSenha());
        assertEquals("USER", novoUsuario.getCargo().name());

        verify(usuarioRepository).save(any(Usuario.class));

    }

    @DisplayName("Deve criar um usuário com cargo especificado")
    @Test
    void deveCriarUsuarioComCargoAdmin() {
        RegisterDTO dto = new RegisterDTO("teste@mail.com", "senha123", TipoCargo.ADMIN);

        when(passwordEncoder.encode("senha123")).thenReturn("hash");
        Usuario novoUsuario = usuarioService.create(dto);

        assertNotNull(novoUsuario);
        assertEquals("teste@mail.com", novoUsuario.getEmail());
        assertEquals("hash", novoUsuario.getSenha());
        assertEquals("ADMIN", novoUsuario.getCargo().name());

        verify(usuarioRepository).save(any(Usuario.class));
    }

    @DisplayName("Deve atualizar um usuário")
    @Test
    void deveAtualizarUsuario() {
        RegisterDTO dto = new RegisterDTO("teste@mail.com", "senha123", TipoCargo.ADMIN);
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@mail.com");
        usuario.setSenha("hash");
        usuario.setCargo(TipoCargo.USER);

        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuario));
        when(passwordEncoder.encode("senha123")).thenReturn("hash");
        Usuario usuarioAtualizado = usuarioService.update(1L, dto);

        assertNotNull(usuarioAtualizado);
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @DisplayName("Deve deletar um usuário")
    @Test
    void deveDeletarUsuario() {
        Endereco endereco = new Endereco();
        endereco.setId(2L);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@mail.com");
        usuario.setSenha("hash");
        usuario.setCargo(TipoCargo.USER);
        usuario.setEndereco(endereco);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.delete(1L);
        verify(enderecoRepository).deleteEnderecoById(2L);
        verify(usuarioRepository).deleteById(1L);
    }

    @DisplayName("Deve lançar erro ao tentar deletar usuário que não existe")
    @Test
    void deveLancarErroAoTentarDeletarUsuarioQueNaoExiste() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> usuarioService.delete(1L));
        verify(usuarioRepository, never()).deleteById(any());
        verify(enderecoRepository, never()).deleteEnderecoById(any());
    }

    @DisplayName("Deve lançar erro ao tentar cadastrar usuário com email já cadastrado")
    @Test
    void deveLancarErroAoTentarCadastrarUsuarioComEmailJaCadastrado() {
        RegisterDTO dto = new RegisterDTO("teste@mail.com", "senha123", null);
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@mail.com");

        when(usuarioRepository.findByEmail("teste@mail.com")).thenReturn(usuario);
        assertThrows(RuntimeException.class, () -> usuarioService.create(dto));
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}

