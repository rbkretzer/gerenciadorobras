package br.com.desafio.gerenciadorobras.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.desafio.gerenciadorobras.entities.Obra;
import br.com.desafio.gerenciadorobras.entities.ObraResponsavel;

@Repository
public interface ObraResponsavelRepository extends JpaRepository<ObraResponsavel, Long> {

    Optional<List<ObraResponsavel>> findByObra(Obra obra);
}
