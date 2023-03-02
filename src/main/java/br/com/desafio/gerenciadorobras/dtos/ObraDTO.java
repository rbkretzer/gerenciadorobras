package br.com.desafio.gerenciadorobras.dtos;

import java.time.LocalDateTime;
import java.util.List;

import br.com.desafio.gerenciadorobras.enumerators.TipoZona;
import lombok.Data;

@Data
public class ObraDTO {

    private Long id;
    private Long numero;
    private LocalDateTime dataCadastro;
    private String descricao;
    private TipoZona zona;
    private List<ResponsavelDTO> responsaveis;
}
