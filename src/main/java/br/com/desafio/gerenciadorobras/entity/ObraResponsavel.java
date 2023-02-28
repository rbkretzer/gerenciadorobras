package br.com.desafio.gerenciadorobras.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "OBRA_RESPONSAVEL")
@NoArgsConstructor @AllArgsConstructor
@SequenceGenerator(name = "GEN_ID_OBRA_RESPONSAVEL", sequenceName = "SEQ_ID_OBRA_RESPONSAVEL", allocationSize = 1, initialValue = 1)
public class ObraResponsavel {
    
    @Id
    @Column(name = "ID_OBRA_RESPONSAVEL")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ID_OBRA_RESPONSAVEL")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name= "ID_OBRA", nullable = false, foreignKey = @ForeignKey(name = "FK_OBRA_RESPONSAVEL_OBRA"))
    private Obra obra;

    @ManyToOne
    @JoinColumn(name = "ID_RESPONSAVEL", nullable = false, foreignKey = @ForeignKey(name = "FK_OBRA_RESPONSAVEL_RESPONSAVEL"))
    private Responsavel responsavel;

}
