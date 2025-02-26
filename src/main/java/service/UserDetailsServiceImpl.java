package com.example.projetolangcursos.service;

import com.example.projetolangcursos.model.Usuario;
import com.example.projetolangcursos.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Carregando usuário com nome de usuário: {}", username);
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            logger.warn("Usuário não encontrado: {}", username);
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        logger.info("Usuário encontrado: {}", usuario.getUsername());
        logger.debug("Senha do usuário no banco de dados: {}", usuario.getPassword());
        return new User(usuario.getUsername(), usuario.getPassword(), new ArrayList<>());
    }
}