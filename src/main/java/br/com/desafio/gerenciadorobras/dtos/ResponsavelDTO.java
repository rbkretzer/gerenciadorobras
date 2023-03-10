package br.com.desafio.gerenciadorobras.dtos;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResponsavelDTO {

    @JsonView({ Views.Privada.class, Views.Publica.class })
    private Long id;
    
    @NotNull(message = "Campo codigo é obrigatório")
    @Size(min= 1, max = 30, message = "Campo codigo deve ter entre 1 a 30 caracteres")
    private String codigo;
    
    @NotNull(message = "Campo cpf é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "Campo cpf deve ter o seguinte formato: 000.000.000-00")
    private String cpf;

    @NotNull(message = "Campo nome é obrigatório")
    @Size(min= 1, max = 100, message = "Campo nome deve ter entre 1 a 100 caracteres")
    private String nome;

}
