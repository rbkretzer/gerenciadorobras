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
@Table(name = "responsaveis")
@NoArgsConstructor @AllArgsConstructor
public class Responsavel {
    
    @Id
    @Column(name = "id_responsavel")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "cd_responsavel", length = 30)
    private String codigo;

    @Column(name = "cpf", length = 14)
    private String cpf;

    @Column(name = "nm_responsavel", length = 100)
    private String nome;

}
