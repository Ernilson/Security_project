package br.com.securityLogin.Jwt.Service;

import br.com.securityLogin.Jwt.Data.DetalheUsuarioData;
import br.com.securityLogin.Model.UsuarioModel;
import br.com.securityLogin.Repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DetahesUsuarioServiceImpl implements UserDetailsService {

    private final UsuarioRepository repository;

    public DetahesUsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<UsuarioModel> usuario = repository.findByLogin(username);

       if (usuario.isEmpty()){
           throw new UsernameNotFoundException("Usuario ["+ username +"] não encontrado");
       }

       return new DetalheUsuarioData(usuario);
    }
}
