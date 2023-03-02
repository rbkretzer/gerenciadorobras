package br.com.desafio.gerenciadorobras.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "RESPONSAVEIS")
@NoArgsConstructor @AllArgsConstructor
public class Responsavel {
    
    @Id
    @Column(name = "ID_RESPONSAVEL")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "CD_RESPONSAVEL", length = 30)
    private String codigo;

    @Column(name = "CPF", length = 14)
    private String cpf;

    @Column(name = "NM_RESPONSAVEL", length = 100)
    private String nome;

}
