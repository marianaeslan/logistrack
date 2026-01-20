package br.com.logistrack.service;

import br.com.logistrack.client.ViaCepClient;
import br.com.logistrack.dto.endereco.EnderecoInputDTO;
import br.com.logistrack.dto.endereco.EnderecoResponseDTO;
import br.com.logistrack.entity.Endereco;
import br.com.logistrack.entity.Encomenda;
import br.com.logistrack.exceptions.ResourceNotFoundException;
import br.com.logistrack.repository.EnderecoRepository;
import br.com.logistrack.repository.EncomendaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EnderecoService {
    
    private final EnderecoRepository enderecoRepository;
    private final EncomendaRepository encomendaRepository;
    private final ViaCepClient viaCepClient;
    private final ObjectMapper objectMapper;

    public EnderecoInputDTO create (Long idEncomenda, EnderecoInputDTO enderecoInputDTO) {
        Encomenda encomenda = encomendaRepository.findById(idEncomenda)
                .orElseThrow(() -> new ResourceNotFoundException("Encomenda não encontrada"));
        
        Endereco novoEndereco = new Endereco();
        String cep = enderecoInputDTO.getCep();
        try {
            EnderecoResponseDTO enderecoResponse = viaCepClient.getByCep(cep);
            novoEndereco.setCep(enderecoResponse.getCep());
            novoEndereco.setLogradouro(enderecoResponse.getLogradouro());
            novoEndereco.setComplemento(enderecoInputDTO.getComplemento());
            novoEndereco.setBairro(enderecoResponse.getBairro());
            novoEndereco.setUf(enderecoResponse.getUf());


            Endereco enderecoSalvo = enderecoRepository.save(novoEndereco);
            return objectMapper.convertValue(enderecoSalvo, EnderecoInputDTO.class);
        } catch (Exception e) {
            throw new ResourceNotFoundException("CEP inválido");
        }
    }
    
    public List<Endereco> list() {
        return enderecoRepository.findAll();
    }
    
    public Optional<Endereco> getByCep(String cep) {
        List<Endereco> enderecos = enderecoRepository.findByCep(cep);
        if (enderecos.isEmpty()) {
            throw new ResourceNotFoundException("Endereço não encontrado");
        }
        return Optional.of(enderecos.get(0));
    }
    
    public void delete (long id) {
        enderecoRepository.deleteById(id);
    }

    public EnderecoInputDTO update (long id, EnderecoInputDTO enderecoInputDTO) {
        Encomenda encomenda = encomendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Encomenda não encontrada"));
        Endereco novoEndereco = new Endereco();
        String cep = enderecoInputDTO.getCep();
        try {
            EnderecoResponseDTO enderecoFromViaCep = viaCepClient.getByCep(cep);

            novoEndereco.setCep(enderecoFromViaCep.getCep());
            novoEndereco.setLogradouro(enderecoFromViaCep.getLogradouro());
            novoEndereco.setBairro(enderecoFromViaCep.getBairro());
            novoEndereco.setUf(enderecoFromViaCep.getUf());
            novoEndereco.setComplemento(enderecoInputDTO.getComplemento());

            Endereco enderecoSalvo = enderecoRepository.save(novoEndereco);

            encomenda.setEnderecoDestino(enderecoSalvo);
            encomendaRepository.save(encomenda);

            return objectMapper.convertValue(enderecoSalvo, EnderecoInputDTO.class);
        } catch (Exception e) {
            throw new ResourceNotFoundException("CEP inválido");
        }
    }
}
