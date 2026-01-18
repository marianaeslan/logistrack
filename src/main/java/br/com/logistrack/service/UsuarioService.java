package br.com.logistrack.service;

import br.com.logistrack.client.ViaCepClient;
import br.com.logistrack.dto.endereco.EnderecoResponseDTO;
import br.com.logistrack.dto.usuario.UsuarioCreateDTO;
import br.com.logistrack.dto.usuario.UsuarioLoginDTO;
import br.com.logistrack.entity.Endereco;
import br.com.logistrack.entity.Usuario;
import br.com.logistrack.entity.enums.TipoCargo;
import br.com.logistrack.exceptions.ResourceNotFoundException;
import br.com.logistrack.repository.EnderecoRepository;
import br.com.logistrack.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;
    private final ViaCepClient viaCepClient;
    private final PasswordEncoder passwordEncoder;
    
    public UsuarioLoginDTO create (UsuarioCreateDTO usuarioCreateDTO) {
        
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(usuarioCreateDTO.getEmail());
        novoUsuario.setSenha(passwordEncoder.encode(usuarioCreateDTO.getSenha()));
        novoUsuario.setCargo(TipoCargo.USER);
        
        try {
            EnderecoResponseDTO enderecoResponse = viaCepClient.getByCep(usuarioCreateDTO.getCep());
            Endereco endereco = new Endereco();
            endereco.setCep(enderecoResponse.getCep());
            endereco.setLogradouro(enderecoResponse.getLogradouro());
            endereco.setBairro(enderecoResponse.getBairro());
            endereco.setUf(enderecoResponse.getUf());
            
            novoUsuario.setEndereco(endereco);
            
            usuarioRepository.save(novoUsuario);
            
            return objectMapper.convertValue(novoUsuario, UsuarioLoginDTO.class);
        } catch (Exception e) {
            throw new ResourceNotFoundException("CEP inválido");
        }
    }
}
