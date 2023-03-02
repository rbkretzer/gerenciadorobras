package br.com.desafio.gerenciadorobras.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "OBRA_RESPONSAVEIS")
@NoArgsConstructor @AllArgsConstructor
public class ObraResponsavel {
    
    @Id
    @Column(name = "ID_OBRA_RESPONSAVEL")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "ID_OBRA", nullable = false, foreignKey = @ForeignKey(name = "FK_OBRA_RESPONSAVEL_OBRA"))
    private Obra obra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_RESPONSAVEL", nullable = false, foreignKey = @ForeignKey(name = "FK_OBRA_RESPONSAVEL_RESPONSAVEL"))
    private Responsavel responsavel;

}
