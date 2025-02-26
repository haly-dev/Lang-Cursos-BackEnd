package com.example.projetolangcursos.controller;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.projetolangcursos.dto.UsuarioDTO;
import com.example.projetolangcursos.model.Usuario;
import com.example.projetolangcursos.repository.UsuarioRepository;
import com.example.projetolangcursos.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Operações relacionadas a autenticão")
@CrossOrigin(origins = "*")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    @Operation(summary = "Autentica um usuário e retorna um token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida", content = @Content(mediaType = "text/plain", schema = @Schema(type = "string"))),
            @ApiResponse(responseCode = "403", description = "Falha na autenticação", content = @Content(mediaType = "text/plain", schema = @Schema(type = "string")))
    })
    public String login(@RequestBody Usuario usuario) {
        logger.info("Requisição de login recebida para o usuário: {}", usuario.getUsername());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword()));
        if (authentication.isAuthenticated()) {
            logger.info("Autenticação bem-sucedida para o usuário: {}", usuario.getUsername());
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtService.generateToken(userDetails);
        } else {
            logger.warn("Falha na autenticação para o usuário: {}", usuario.getUsername());
            return "Falha na autenticação";
        }
    }

    @PostMapping("/usuarios")
    @Operation(summary = "Cria um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso", content = @Content(mediaType = "text/plain", schema = @Schema(type = "string")))
    })
    public ResponseEntity<String> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuarioDTO.getPassword()));
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuário criado com sucesso!");
    }
}