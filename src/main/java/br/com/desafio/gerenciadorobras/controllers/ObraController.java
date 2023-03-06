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

import br.com.desafio.gerenciadorobras.dtos.ObraDTO;
import br.com.desafio.gerenciadorobras.filters.ObraFilter;
import br.com.desafio.gerenciadorobras.services.ObraService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class ObraController {

    @Autowired
    private ObraService obraService;

    @GetMapping
    @RequestMapping("/obras")
    public ResponseEntity<?> getObras(
            @RequestParam(name = "page_size", defaultValue = "10", required = false) int pageSize, 
            @RequestParam(name = "page_index", defaultValue = "0", required = false) int pageIndex,
            ObraFilter filter) {
        return ResponseEntity.ok(obraService.getObras(pageSize, pageIndex, filter));
    }

    @PostMapping
    @RequestMapping("/obraprivada")
    public ResponseEntity<?> saveObraPrivada(@RequestBody @Valid ObraDTO obraDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(obraService.save(false, obraDTO));
    }

    @PostMapping
    @RequestMapping("/obrapublica")
    public ResponseEntity<?> saveObraPublica(@RequestBody @Valid ObraDTO obraDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(obraService.save(true, obraDTO));
    }
    
    @GetMapping("/obra/{obraId}")
    public ResponseEntity<?> getObra(@PathVariable("obraId") Long obraId) {
        return ResponseEntity.ok(obraService.getObra(obraId));
    }
    
    @DeleteMapping("/obra/{obraId}")
    public ResponseEntity<?> deleteObra(@PathVariable("obraId") Long obraId) {
        return ResponseEntity.ok(obraService.deleteObra(obraId));
    }

}