package br.com.logistrack.client;

import br.com.logistrack.dto.endereco.EnderecoResponseDTO;
import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "via-cep", url = "https://viacep.com.br/ws")
@Headers("Content-Type: application/json")
public interface ViaCepClient {

    @GetMapping("/{cep}/json")
    EnderecoResponseDTO getByCep(@PathVariable String cep);


}