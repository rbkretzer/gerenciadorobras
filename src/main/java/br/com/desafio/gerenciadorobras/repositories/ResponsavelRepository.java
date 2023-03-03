package br.com.desafio.gerenciadorobras.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.desafio.gerenciadorobras.entities.Responsavel;

@Repository
public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {

        @Query(value = "SELECT * FROM responsaveis      "
                     + "LIMIT :pageSize                 "
                     + "OFFSET (:pageSize * :pageIndex) "
        ,nativeQuery = true
    )
    Optional<List<Responsavel>> getResponsaveis(@Param("pageIndex") int pageIndex,
                                                @Param("pageSize") int pageSize
    );

    Optional<Responsavel> findByCpf(String cpf);

    Optional<Responsavel> findByCodigo(String codigo);
    
}
