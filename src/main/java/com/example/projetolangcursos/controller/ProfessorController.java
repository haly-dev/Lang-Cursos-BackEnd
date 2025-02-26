package com.example.projetolangcursos.controller;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.example.projetolangcursos.model.Professor;
import com.example.projetolangcursos.repository.ProfessorRepository;
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
@RequestMapping("/professores")
@Tag(name = "Professores", description = "Operações relacionadas a professores")
@CrossOrigin(origins = "*")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping
    @Operation(summary = "Obtém todos os professores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de professores encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Professor.class)))
    })
    public List<Professor> getAllProfessores() {
        return professorRepository.findAll();
    }

    @GetMapping("/{matricula}")
    @Operation(summary = "Obtém um professor por matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Professor.class))),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    public ResponseEntity<Professor> getProfessorById(@PathVariable Long matricula) {
        return professorRepository.findById(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cria um novo professor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Professor criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Professor.class)))
    })
    public ResponseEntity<Professor> createProfessor(@RequestBody Professor professor) {
        Professor savedProfessor = professorRepository.save(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfessor);
    }

    @PutMapping("/{matricula}")
    @Operation(summary = "Atualiza um professor por matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Professor.class))),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    public ResponseEntity<Professor> updateProfessor(@PathVariable Long matricula, @RequestBody Professor professor) {
        if (professorRepository.existsById(matricula)) {
            professor.setMatricula(matricula);
            Professor updatedProfessor = professorRepository.save(professor);
            return ResponseEntity.ok(updatedProfessor);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{matricula}")
    @Operation(summary = "Atualiza o nome de um professor por matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nome do professor atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Professor.class))),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    public ResponseEntity<Professor> updateProfessorNome(@PathVariable Long matricula, @RequestBody Professor professorAtualizado) {
        Optional<Professor> professorExistente = professorRepository.findById(matricula);

        if (professorExistente.isPresent()) {
            Professor professor = professorExistente.get();
            if (professorAtualizado.getNome() != null) {
                professor.setNome(professorAtualizado.getNome());
            }
            Professor professorSalvo = professorRepository.save(professor);
            return ResponseEntity.ok(professorSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{matricula}")
    @Operation(summary = "Exclui um professor por matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Professor excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado")
    })
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long matricula) {
        if (professorRepository.existsById(matricula)) {
            professorRepository.deleteById(matricula);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}