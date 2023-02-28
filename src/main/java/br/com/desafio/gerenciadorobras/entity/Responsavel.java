package br.com.desafio.gerenciadorobras.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "RESPONSAVEL")
@NoArgsConstructor @AllArgsConstructor
@SequenceGenerator(name = "GEN_ID_RESPONSAVEL", sequenceName = "SEQ_ID_RESPONSAVEL", allocationSize = 1, initialValue = 1)
public class Responsavel {
    
    @Id
    @Column(name = "ID_RESPONSAVEL")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ID_RESPONSAVEL")
    private Long id;

    @Column(name = "CD_RESPONSAVEL", length = 30)
    private String codigo;

    @Column(name = "CPF", length = 14)
    private String cpf;

    @Column(name = "NM_RESPONSAVEL", length = 100)
    private String nome;

}
