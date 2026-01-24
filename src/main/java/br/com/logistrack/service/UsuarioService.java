package br.com.logistrack.service;

import br.com.logistrack.client.ViaCepClient;

import br.com.logistrack.dto.usuario.RegisterDTO;

import br.com.logistrack.entity.Usuario;
import br.com.logistrack.entity.enums.TipoCargo;

import br.com.logistrack.repository.EnderecoRepository;
import br.com.logistrack.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    
    public Usuario create (RegisterDTO registerDTO) {
        
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(registerDTO.email());
        if (usuarioRepository.findByEmail(registerDTO.email()) != null) {
            throw new RuntimeException("Email já cadastrado");
        }
        novoUsuario.setSenha(passwordEncoder.encode(registerDTO.senha()));
        novoUsuario.setCargo(registerDTO.cargo() != null ? registerDTO.cargo() : TipoCargo.USER);
        usuarioRepository.save(novoUsuario);
        return novoUsuario;
    }

    public UserDetails findByEmail (String email) {
        return usuarioRepository.findByEmail(email);
    }

    public UserDetails findById (Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public List<RegisterDTO> list() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> objectMapper.convertValue(usuario, RegisterDTO.class))
                .toList();
    }

    public Usuario update (long id, RegisterDTO registerDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.setEmail(registerDTO.email());
        usuario.setSenha(passwordEncoder.encode(registerDTO.senha()));
        usuario.setCargo(registerDTO.cargo() != null ? registerDTO.cargo() : TipoCargo.USER);
        usuarioRepository.save(usuario);
        return usuario;
    }

    public void delete (long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        enderecoRepository.deleteEnderecoById(usuario.get().getEndereco().getId());
        usuarioRepository.deleteById(id);
    }
}
