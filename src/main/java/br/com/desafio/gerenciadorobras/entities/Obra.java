package br.com.desafio.gerenciadorobras.entities;

import java.time.LocalDateTime;

import br.com.desafio.gerenciadorobras.enumerators.TipoZona;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "OBRAS")
@NoArgsConstructor @AllArgsConstructor
@SequenceGenerator(name = "GEN_ID_OBRA", sequenceName = "SEQ_ID_OBRA", allocationSize = 1, initialValue = 1)
public class Obra {
    
    @Id
    @Column(name = "ID_OBRA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ID_OBRA")
    private Long id;

    @Column(name = "NUM_OBRA", nullable = false, precision = 12, scale = 0)
    private Long numero;
    
    @Column(name = "DT_CADASTRO", nullable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "DS_OBRA", nullable = false)
    private String descricao;

    @Enumerated // poderia ser adotado um converter, mas entendo que é preferivel utilizar a ordem natural do enum
    @Column(name = "TP_ZONA", columnDefinition = "TINYINT", nullable = false)
    private TipoZona zona;

    @PrePersist
    public void createDate() {
        this.dataCadastro = LocalDateTime.now();
    }
}
