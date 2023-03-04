package br.com.desafio.gerenciadorobras.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.desafio.gerenciadorobras.enumerators.TipoObra;
import br.com.desafio.gerenciadorobras.enumerators.TipoZona;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ObraDTO {

    private Long id;

    @NotNull(message = "Campo numero é obrigatório")
    private Long numero;

    @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    @JsonProperty(access = Access.READ_ONLY)
    private TipoObra tipoObra;
    
    @NotNull(message = "Campo descricao é obrigatório")
    @Size(min= 1, max = 100, message = "Campo nome deve ter entre 1 a 100 caracteres")
    private String descricao;

    private TipoZona zona;

    @NotNull(message = "Campo responsaveis é obrigatório")
    @Size(min= 1, message = "Campo responsaveis deve ter ao menos um elemento")
    private List<ResponsavelDTO> responsaveis;

    private LocalDate dataInicio;
    
    private LocalDate dataFim;

    @Min(value = 1, message = "Campo areaTotal deve ser no mínimo 1")
    private BigDecimal areaTotal;
    
}
