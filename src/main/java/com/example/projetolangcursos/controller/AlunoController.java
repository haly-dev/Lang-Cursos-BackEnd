package com.example.projetolangcursos.controller;

import com.example.projetolangcursos.model.Aluno;
import com.example.projetolangcursos.model.Curso;
import com.example.projetolangcursos.repository.AlunoRepository;
import com.example.projetolangcursos.repository.CursoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
@Tag(name = "Alunos", description = "Operações relacionadas a alunos")
@CrossOrigin(origins = "*")
public class AlunoController {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    @Operation(summary = "Obtém todos os alunos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alunos encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aluno.class)))
    })
    public List<Aluno> getAllAlunos() {
        return alunoRepository.findAll();
    }

    @GetMapping("/{matricula}")
    @Operation(summary = "Obtém um aluno por matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    public ResponseEntity<Aluno> getAlunoById(@PathVariable Long matricula) {
        return alunoRepository.findById(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cria um novo aluno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aluno criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public ResponseEntity<Aluno> createAluno(@Valid @RequestBody Aluno aluno) {
        if (aluno.getCursos() == null || aluno.getCursos().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Aluno savedAluno = alunoRepository.save(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAluno);
    }

    @PutMapping("/{matricula}")
    @Operation(summary = "Atualiza um aluno por matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    public ResponseEntity<Aluno> updateAluno(@PathVariable Long matricula, @Valid @RequestBody Aluno aluno) {
        if (!alunoRepository.existsById(matricula)) {
            return ResponseEntity.notFound().build();
        }
        aluno.setMatricula(matricula);
        Aluno updatedAluno = alunoRepository.save(aluno);
        return ResponseEntity.ok(updatedAluno);
    }

    @PatchMapping("/{matricula}/nome")
    @Operation(summary = "Atualiza o nome de um aluno por matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nome do aluno atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    public ResponseEntity<Aluno> updateAlunoNome(@PathVariable Long matricula, @RequestBody Aluno alunoAtualizado) {
        Optional<Aluno> alunoExistente = alunoRepository.findById(matricula);

        if (alunoExistente.isPresent()) {
            Aluno aluno = alunoExistente.get();
            if (alunoAtualizado.getNome() != null) {
                aluno.setNome(alunoAtualizado.getNome());
            }
            Aluno alunoSalvo = alunoRepository.save(aluno);
            return ResponseEntity.ok(alunoSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{matricula}/cursos")
    @Operation(summary = "Atualiza os cursos de um aluno por matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cursos do aluno atualizados com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Aluno.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    public ResponseEntity<Aluno> updateAlunoCursos(@PathVariable Long matricula, @RequestBody List<Long> cursoIds) {
        Optional<Aluno> alunoExistente = alunoRepository.findById(matricula);

        if (alunoExistente.isPresent()) {
            Aluno aluno = alunoExistente.get();
            List<Curso> cursos = new ArrayList<>();

            for (Long cursoId : cursoIds) {
                Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
                if (cursoOptional.isPresent()) {
                    cursos.add(cursoOptional.get());
                } else {
                    return ResponseEntity.badRequest().body(null);
                }
            }

            aluno.setCursos(cursos);
            Aluno alunoSalvo = alunoRepository.save(aluno);
            return ResponseEntity.ok(alunoSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{matricula}")
    @Operation(summary = "Exclui um aluno por matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluno excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    public ResponseEntity<Void> deleteAluno(@PathVariable Long matricula) {
        if (!alunoRepository.existsById(matricula)) {
            return ResponseEntity.notFound().build();
        }
        alunoRepository.deleteById(matricula);
        return ResponseEntity.noContent().build();
    }
}