package br.com.logistrack.service;

import br.com.logistrack.client.ViaCepClient;
import br.com.logistrack.dto.endereco.EnderecoInputDTO;
import br.com.logistrack.dto.endereco.EnderecoResponseDTO;
import br.com.logistrack.entity.Endereco;
import br.com.logistrack.exceptions.RegraDeNegocioException;
import br.com.logistrack.repository.EnderecoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EnderecoService {
    
    private final EnderecoRepository enderecoRepository;
    private final ViaCepClient viaCepClient;
    private final ObjectMapper objectMapper;

    public EnderecoInputDTO create (EnderecoInputDTO enderecoInputDTO) {
        Endereco novoEndereco = new Endereco();
        String cep = enderecoInputDTO.getCep();
        try {
            EnderecoResponseDTO endereco = viaCepClient.getByCep(cep);
            novoEndereco.setCep(endereco.getCep());
            novoEndereco.setLogradouro(endereco.getLogradouro());
            novoEndereco.setComplemento(enderecoInputDTO.getComplemento());
            novoEndereco.setBairro(endereco.getBairro());
            novoEndereco.setUf(endereco.getUf());
            Endereco enderecoSalvo = enderecoRepository.save(novoEndereco);
            return objectMapper.convertValue(enderecoSalvo, EnderecoInputDTO.class);
        } catch (Exception e) {
            throw new RegraDeNegocioException("CEP inválido");
        }
    }
    
    public List<Endereco> list() {
        return enderecoRepository.findAll();
    }
    
    public Optional<Endereco> getByCep(String cep) {
        List<Endereco> enderecos = enderecoRepository.findByCep(cep);
        if (enderecos.isEmpty()) {
            throw new RegraDeNegocioException("Endereço não encontrado");
        }
        return Optional.of(enderecos.get(0));
    }
    
    public void delete (long id) {
        enderecoRepository.deleteById(id);
    }
    
    public List<Endereco> findEnderecoByEncomendaId (Long id) {
        return enderecoRepository.findEnderecoByEncomendaId(id);
    }
    
    public EnderecoInputDTO update (long id, EnderecoInputDTO enderecoInputDTO) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado"));
        String cep = enderecoInputDTO.getCep();
        try {
            EnderecoResponseDTO enderecoFromViaCep = viaCepClient.getByCep(cep);

            endereco.setCep(enderecoFromViaCep.getCep());
            endereco.setLogradouro(enderecoFromViaCep.getLogradouro());
            endereco.setBairro(enderecoFromViaCep.getBairro());
            endereco.setUf(enderecoFromViaCep.getUf());
            endereco.setComplemento(enderecoInputDTO.getComplemento());

            Endereco enderecoSalvo = enderecoRepository.save(endereco);

            return objectMapper.convertValue(enderecoSalvo, EnderecoInputDTO.class);
        } catch (Exception e) {
            throw new RegraDeNegocioException("CEP inválido");
        }
    }
}
