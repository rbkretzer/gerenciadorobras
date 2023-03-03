package br.com.desafio.gerenciadorobras.filters;

import br.com.desafio.gerenciadorobras.enumerators.TipoZona;
import lombok.Value;

@Value
public class ObraFilter {
    
    private TipoZona tipoZona;
    private String codigoResponsavel;
}
