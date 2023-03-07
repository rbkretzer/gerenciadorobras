package br.com.desafio.gerenciadorobras.controllers;

import org.springdoc.core.annotations.ParameterObject;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.desafio.gerenciadorobras.dtos.ObraDTO;
import br.com.desafio.gerenciadorobras.dtos.Views;
import br.com.desafio.gerenciadorobras.filters.ObraFilter;
import br.com.desafio.gerenciadorobras.services.ObraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1")
@Tag(name = "Obra API", description = "End points habilitados para realizar operações relacionadas as obras")
public class ObraController {

    @Autowired
    private ObraService obraService;

    @GetMapping("/obras")
    @Operation(summary = "Listar todas as obras", description = "Consulta que retorna todas as obras cadastradas, ordenadas pelo número."
                                                              + "Permite listar apenas obras públicas ou obras privadas (parâmetro tipoObra)."
                                                              + "Permite listar todas as obras relacionadas a um responsável (parâmetro codigoResponsavel)")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getObras(
            @RequestParam(name = "page_size", defaultValue = "10", required = false) int pageSize, 
            @RequestParam(name = "page_index", defaultValue = "0", required = false) int pageIndex,
            @ParameterObject ObraFilter filter) {
        return ResponseEntity.ok(obraService.getObras(pageSize, pageIndex, filter));
    }

    @PostMapping("/obraprivada")
    @Operation(summary = "Cadastrar obra privada", description = "Têm o objetivo de salvar uma obra privada. Se enviado o id, a respectiva obra com o id será atualizada com as informações enviadas")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveObraPrivada(@RequestBody @JsonView(Views.Privada.class) @Valid ObraDTO obraDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(obraService.save(false, obraDTO));
    }

    @PostMapping("/obrapublica")
    @Operation(summary = "Cadastrar obra pública", description = "Têm o objetivo de salvar uma obra pública. Se enviado o id, a respectiva obra com o id será atualizada com as informações enviadas")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveObraPublica(@RequestBody @JsonView(Views.Publica.class) @Valid ObraDTO obraDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(obraService.save(true, obraDTO));
    }
    
    @GetMapping("/obra/{obraId}")
    @Operation(summary = "Consultar uma obra", description = "Têm como objetivo trazer todas as informaçõee referentes àquela obra")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getObra(@PathVariable("obraId") Long obraId) {
        return ResponseEntity.ok(obraService.getObra(obraId));
    }
    
    @DeleteMapping("/obra/{obraId}")
    @Operation(summary = "Excluir uma obra", description = "Remover a obra informada do sistema")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteObra(@PathVariable("obraId") Long obraId) {
        return ResponseEntity.ok(obraService.deleteObra(obraId));
    }

}