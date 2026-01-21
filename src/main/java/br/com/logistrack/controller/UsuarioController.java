package br.com.logistrack.controller;

import br.com.logistrack.dto.usuario.RegisterDTO;
import br.com.logistrack.entity.Usuario;
import br.com.logistrack.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDetails> findById (Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @GetMapping
    public List<RegisterDTO> list() {
        return new ResponseEntity<>(usuarioService.list(), HttpStatus.OK).getBody();
    }

    @PostMapping
    public ResponseEntity<UserDetails> create (RegisterDTO registerDTO) {
        return ResponseEntity.ok(usuarioService.create(registerDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetails> update (@PathVariable long id, RegisterDTO registerDTO) {
        Usuario usuarioAtualizado = usuarioService.update(id, registerDTO);
        return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable long id) {
        usuarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
