package br.com.securityLogin.Controller;


import br.com.securityLogin.Model.UsuarioModel;
import br.com.securityLogin.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    @GetMapping("/listarTodos")
   public ResponseEntity<List<UsuarioModel>> ListaTodos(){
       return ResponseEntity.ok(usuarioRepository.findAll());
   }

   @PostMapping("/salvar")
   public ResponseEntity<UsuarioModel>Salvar(@RequestBody UsuarioModel usuarioModel){
        usuarioModel.setPassword(encoder.encode(usuarioModel.getPassword()));
        return ResponseEntity.ok(usuarioRepository.save(usuarioModel));
   }

   @GetMapping("/validaSenha")
   public ResponseEntity<Boolean> ValidaSenha(@RequestParam String login, @RequestParam String password){

       Optional<UsuarioModel> optionalUsuarioModel = usuarioRepository.findByLogin(login);
       if (optionalUsuarioModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
       }

       UsuarioModel usuario = optionalUsuarioModel.get();
       boolean valid = encoder.matches(password, usuario.getPassword());

       HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
       return ResponseEntity.status(status).body(valid);
   }
}
