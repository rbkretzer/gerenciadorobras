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

import br.com.desafio.gerenciadorobras.dtos.ObraDTO;
import br.com.desafio.gerenciadorobras.filters.ObraFilter;
import br.com.desafio.gerenciadorobras.services.ObraService;
import jakarta.persistence.NoResultException;

@RestController
@RequestMapping("api/obra")
public class ObraController {

    @Autowired
    private ObraService obraService;

    @GetMapping
    public ResponseEntity<?> getObras(
            @RequestParam(name = "page_size", defaultValue = "10", required = false) int pageSize, 
            @RequestParam(name = "page_index", defaultValue = "0", required = false) int pageIndex,
            ObraFilter filter) {
        try {
            return ResponseEntity.ok(obraService.getObras(pageSize, pageIndex, filter));
        } catch (NoResultException nre) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Não existem registros");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
        }
    }

    @PostMapping
    public ResponseEntity<?> saveObra(@RequestBody @Valid ObraDTO obraDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(obraService.save(obraDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
        }
    }

    @PutMapping("{obraId}")
    public ResponseEntity<?> saveObra(@PathVariable("obraId") Long obraId, @RequestBody @Valid ObraDTO obraDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(obraService.save(obraDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
        }
    }
    
    @GetMapping("{obraId}")
    public ResponseEntity<?> saveObra(@PathVariable("obraId") Long obraId) {
        try {
            return ResponseEntity.ok(obraService.getObra(obraId));
        } catch (NoResultException nre) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Obra não encontrada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
        }
    }
    
    @DeleteMapping("{obraId}")
    public ResponseEntity<?> deleteObra(@PathVariable("obraId") Long obraId) {
        try {
            return ResponseEntity.ok(obraService.deleteObra(obraId));
        } catch (NoResultException nre) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Obra não encontrada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno");
        }
    }

}