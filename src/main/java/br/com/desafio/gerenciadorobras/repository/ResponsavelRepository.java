package br.com.desafio.gerenciadorobras.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.desafio.gerenciadorobras.entities.Responsavel;

@Repository
public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {

    Optional<List<Responsavel>> getResponsaveis(int pageIndex, int pageSize);
    
}
