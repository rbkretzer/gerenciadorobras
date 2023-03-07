package br.com.desafio.gerenciadorobras.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio.gerenciadorobras.dtos.ResponsavelDTO;
import br.com.desafio.gerenciadorobras.services.ResponsavelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/responsavel")
@Tag(name = "Responsável API", description = "End points habilitados para realizar operações relacionadas aos responsáveis")
public class ResponsavelController {
    
    @Autowired
    private ResponsavelService responsavelService;

    @GetMapping
    @Operation(summary = "Listar responáveis", description = "Consulta que retorna todas os responsáveis cadastrados")
    public ResponseEntity<?> getResponsaveis(
            @RequestParam(name = "page_size", defaultValue = "10", required = false) int pageSize, 
            @RequestParam(name = "page_index", defaultValue = "0", required = false) int pageIndex) {
        return ResponseEntity.ok(responsavelService.getResponsaveis(pageSize, pageIndex));
    }

    @PostMapping
    @Operation(summary = "Cadastrar responsável", description = "Têm o objetivo de salvar um responsável. Se enviado o id, o respectivo responsável com o id será atualizada com as informações enviadas")
    public ResponseEntity<?> saveResponsavel(@RequestBody @Valid ResponsavelDTO responsavelDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(responsavelService.save(responsavelDTO));
    }
    
    @GetMapping("{responsavelId}")
    @Operation(summary = "Consultar um responável", description = "Consulta que retorna todos os dados referente ao responsável informado")
    public ResponseEntity<?> getResponsavel(@PathVariable("responsavelId") Long responsavelId) {
        return ResponseEntity.ok(responsavelService.getResponsavel(responsavelId));
    }
    
    @DeleteMapping("{responsavelId}")
    @Operation(summary = "Excluir um responável", description = "Remover o responsável informado do sistema")
    public ResponseEntity<?> deleteResponsavel(@PathVariable("responsavelId") Long responsavelId) {
        return ResponseEntity.ok(responsavelService.deleteResponsavel(responsavelId));
    }
}