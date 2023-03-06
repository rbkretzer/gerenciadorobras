package br.com.desafio.gerenciadorobras.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.desafio.gerenciadorobras.enumerators.TipoObra;
import br.com.desafio.gerenciadorobras.enumerators.TipoZona;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "obras")
@NoArgsConstructor @AllArgsConstructor
public class Obra {
    
    @Id
    @Column(name = "id_obra")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "num_obra", nullable = false, precision = 12, scale = 0)
    private Long numero;
    
    @Column(name = "dt_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "ds_obra", nullable = false)
    private String descricao;

    @Enumerated
    @Column(name = "tp_obra", columnDefinition = "TINYINT", nullable = false)
    private TipoObra tipo;

    @Enumerated
    @Column(name = "tp_zona", columnDefinition = "TINYINT")
    private TipoZona zona;

    @Column(name = "dt_inicio")
    private LocalDate dataInicio;
    
    @Column(name = "dt_fim")
    private LocalDate dataFim;

    @Column(name = "vl_area_total", precision = 12, scale = 2)
    private BigDecimal areaTotal;

    @PrePersist
    public void createDate() {
        this.dataCadastro = LocalDateTime.now();
    }
}
