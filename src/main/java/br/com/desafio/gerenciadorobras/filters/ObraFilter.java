package br.com.desafio.gerenciadorobras.filters;

import br.com.desafio.gerenciadorobras.enumerators.TipoObra;
import lombok.Value;

@Value
public class ObraFilter {
    
    private TipoObra tipoObra;
    private String codigoResponsavel;
}
