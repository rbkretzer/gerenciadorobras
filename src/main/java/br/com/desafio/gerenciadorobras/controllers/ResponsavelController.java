package br.com.desafio.gerenciadorobras.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio.gerenciadorobras.dtos.ResponsavelDTO;
import br.com.desafio.gerenciadorobras.services.ResponsavelService;
import jakarta.persistence.NoResultException;

@RestController
@RequestMapping("/api/responsavel")
public class ResponsavelController {
    
    @Autowired
    private ResponsavelService responsavelService;

    @GetMapping
    public ResponseEntity<?> getResponsaveis(
            @RequestParam(name = "page_size", defaultValue = "10", required = false) int pageSize, 
            @RequestParam(name = "page_index", defaultValue = "0", required = false) int pageIndex) {
        try {
            return ResponseEntity.ok(responsavelService.getResponsaveis(pageSize, pageIndex));
        } catch (NoResultException nre) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Não existem registros");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
        }
    }

    @PostMapping
    public ResponseEntity<?> saveResponsavel(@RequestBody @Valid ResponsavelDTO responsavelDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(responsavelService.save(responsavelDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
        }
    }

    @PutMapping("{responsavelId}")
    public ResponseEntity<?> updateResponsavel(@PathVariable("responsavelId") Long responsavelId, @RequestBody @Valid ResponsavelDTO responsavelDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(responsavelService.update(responsavelId, responsavelDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
        }
    }
    
    @GetMapping("{responsavelId}")
    public ResponseEntity<?> saveResponsavel(@PathVariable("responsavelId") Long responsavelId) {
        try {
            return ResponseEntity.ok(responsavelService.getResponsavel(responsavelId));
        } catch (NoResultException nre) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Responsavel não encontrada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
        }
    }
    
    @DeleteMapping("{responsavelId}")
    public ResponseEntity<?> deleteResponsavel(@PathVariable("responsavelId") Long responsavelId) {
        try {
            return ResponseEntity.ok(responsavelService.getResponsavel(responsavelId));
        } catch (NoResultException nre) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Responsavel não encontrada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
        }
    }
}