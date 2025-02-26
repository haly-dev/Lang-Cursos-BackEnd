package com.example.projetolangcursos.controller;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.projetolangcursos.model.Curso;
import com.example.projetolangcursos.repository.CursoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
@Tag(name = "Cursos", description = "Operações relacionadas a cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    @Operation(summary = "Obtém todos os cursos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de cursos encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class)))
    })
    public List<Curso> getAllCursos() {
        return cursoRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtém um curso por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class))),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    public ResponseEntity<Curso> getCursoById(@PathVariable Long id) {
        return cursoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cria um novo curso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Curso criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class)))
    })
    public ResponseEntity<Curso> createCurso(@RequestBody Curso curso) {
        Curso savedCurso = cursoRepository.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCurso);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um curso por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class))),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    public ResponseEntity<Curso> updateCurso(@PathVariable Long id, @RequestBody Curso curso) {
        if (cursoRepository.existsById(id)) {
            curso.setId(id);
            Curso updatedCurso = cursoRepository.save(curso);
            return ResponseEntity.ok(updatedCurso);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza o nome de um curso por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nome do curso atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class))),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    public ResponseEntity<Curso> updateCursoNome(@PathVariable Long id, @RequestBody Curso cursoAtualizado) {
        Optional<Curso> cursoExistente = cursoRepository.findById(id);

        if (cursoExistente.isPresent()) {
            Curso curso = cursoExistente.get();
            if (cursoAtualizado.getNome() != null) {
                curso.setNome(cursoAtualizado.getNome());
            }
            Curso cursoSalvo = cursoRepository.save(curso);
            return ResponseEntity.ok(cursoSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um curso por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Curso excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        if (cursoRepository.existsById(id)) {
            cursoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}