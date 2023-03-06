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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/obra")
@Tag(name = "Obra API", description = "End points habilitados para realizar operações relacionadas as obras")
public class ObraController {

    @Autowired
    private ObraService obraService;

    @GetMapping
    @RequestMapping
    public ResponseEntity<?> getObras(
            @RequestParam(name = "page_size", defaultValue = "10", required = false) int pageSize, 
            @RequestParam(name = "page_index", defaultValue = "0", required = false) int pageIndex,
            ObraFilter filter) {
        return ResponseEntity.ok(obraService.getObras(pageSize, pageIndex, filter));
    }

    @PostMapping
    @RequestMapping("/privada")
    public ResponseEntity<?> saveObraPrivada(@RequestBody @Valid ObraDTO obraDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(obraService.save(false, obraDTO));
    }

    @PostMapping
    @RequestMapping("/publica")
    //@Operation(summary = "Salvar uma obra pública", description = "Têm o objetivo de salvar uma obra pública. Se enviado o id, a respectiva obra com o id será atualizada com as informações enviadas")
    public ResponseEntity<?> saveObraPublica(@RequestBody @Valid ObraDTO obraDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(obraService.save(true, obraDTO));
    }
    
    @GetMapping("/{obraId}")
    public ResponseEntity<?> getObra(@PathVariable("obraId") Long obraId) {
        return ResponseEntity.ok(obraService.getObra(obraId));
    }
    
    @DeleteMapping("/{obraId}")
    public ResponseEntity<?> deleteObra(@PathVariable("obraId") Long obraId) {
        return ResponseEntity.ok(obraService.deleteObra(obraId));
    }

}