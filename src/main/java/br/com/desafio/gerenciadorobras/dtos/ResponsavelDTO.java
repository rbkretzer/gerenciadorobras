package br.com.desafio.gerenciadorobras.dtos;

import lombok.Data;

@Data
public class ResponsavelDTO {

    private Long id;
    private String codigo;
    private String cpf;
    private String nome;

}
