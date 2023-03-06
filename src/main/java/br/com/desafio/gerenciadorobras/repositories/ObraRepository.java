package br.com.desafio.gerenciadorobras.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.desafio.gerenciadorobras.entities.Obra;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Long> {
    
    @Query(value = "SELECT o.* FROM obras o                                                                                                "
                 + "    INNER JOIN obra_responsaveis orep                                                                                  "
                 + "        ON orep.id_obra = o.id_obra                                                                                    "
                 + "    INNER JOIN responsaveis r                                                                                          "
                 + "        ON r.id_responsavel = orep.id_responsavel                                                                      "
                 + "WHERE o.tp_obra = COALESCE(CAST(CAST(:tipoObra AS CHARACTER VARYING) AS TINYINT), o.tp_obra)                           "
                 + "    AND r.cd_responsavel = COALESCE(CAST(:codigoResponsavel AS CHARACTER VARYING), r.cd_responsavel)                   "
                 + "ORDER BY o.num_obra ASC                                                                                                "
                 + "LIMIT :pageSize                                                                                                        "
                 + "OFFSET (:pageSize * :pageIndex)                                                                                        "
        ,nativeQuery = true
    )
    Optional<List<Obra>> getObras(@Param("tipoObra") Integer tipoObra,
                                  @Param("codigoResponsavel") String codigoResponsavel,
                                  @Param("pageIndex") int pageIndex,
                                  @Param("pageSize") int pageSize
    );

    @Query(value = "SELECT * FROM obras                                                                     "
                 + "WHERE tp_obra = CAST(CAST(:tipoObra AS CHARACTER VARYING) AS TINYINT)                   "
                 + "    AND num_obra = CAST(CAST(:numero AS CHARACTER VARYING) AS BIGINT)                   "
        ,nativeQuery = true
    )
    Optional<Obra> findByNumeroTipoObra(@Param("numero") Long numero,
                                        @Param("tipoObra") Integer tipoObra
    );
}
