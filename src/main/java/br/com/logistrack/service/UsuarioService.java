package br.com.logistrack.service;

import br.com.logistrack.client.ViaCepClient;
import br.com.logistrack.dto.endereco.EnderecoResponseDTO;
import br.com.logistrack.dto.usuario.AuthenticationDTO;
import br.com.logistrack.dto.usuario.RegisterDTO;
import br.com.logistrack.entity.Endereco;
import br.com.logistrack.entity.Usuario;
import br.com.logistrack.entity.enums.TipoCargo;
import br.com.logistrack.exceptions.ResourceNotFoundException;
import br.com.logistrack.repository.EnderecoRepository;
import br.com.logistrack.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;
    private final ViaCepClient viaCepClient;
    private final PasswordEncoder passwordEncoder;
    
    public Usuario create (RegisterDTO registerDTO) {
        
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(registerDTO.email());
        novoUsuario.setSenha(passwordEncoder.encode(registerDTO.senha()));
        novoUsuario.setCargo(registerDTO.cargo() != null ? registerDTO.cargo() : TipoCargo.USER);
        novoUsuario.setCep(registerDTO.cep());
        
        try {
            EnderecoResponseDTO enderecoResponse = viaCepClient.getByCep(registerDTO.cep());
            Endereco endereco = new Endereco();
            endereco.setCep(enderecoResponse.getCep());
            endereco.setLogradouro(enderecoResponse.getLogradouro());
            endereco.setBairro(enderecoResponse.getBairro());
            endereco.setUf(enderecoResponse.getUf());
            
            novoUsuario.setEndereco(endereco);
            
            usuarioRepository.save(novoUsuario);
            
            return novoUsuario;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResourceNotFoundException("CEP inválido");
        }
    }

    public UserDetails findByEmail (String email) {
        return usuarioRepository.findByEmail(email);
    }
}
