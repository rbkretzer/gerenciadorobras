package br.com.desafio.gerenciadorobras.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.desafio.gerenciadorobras.entities.Obra;
import br.com.desafio.gerenciadorobras.entities.ObraResponsavel;
import br.com.desafio.gerenciadorobras.entities.Responsavel;

@Repository
public interface ObraResponsavelRepository extends JpaRepository<ObraResponsavel, Long> {

    Optional<List<ObraResponsavel>> findByObra(Obra obra);

    Optional<ObraResponsavel> findByResponsavel(Responsavel map);

    @Query(value = "SELECT * FROM obra_responsaveis                                                         "
                 + "WHERE id_obra = CAST(CAST(:idObra AS TEXT) AS BIGINT)                                   "
                 + "    AND id_responsavel = CAST(CAST(:idResponsavel AS TEXT) AS BIGINT)                   "
        ,nativeQuery = true
    )
    Optional<ObraResponsavel> findByObraResponsavel(@Param("idObra") Long idObra,
                                                    @Param("idResponsavel") Long idResponsavel
    );
}
