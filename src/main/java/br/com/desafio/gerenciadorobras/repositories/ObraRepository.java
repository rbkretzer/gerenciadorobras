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
    
    @Query(value = "SELECT o.* FROM obras o                                                                                 "
                 + "    INNER JOIN obra_responsaveis or                                                                     " 
                 + "        ON or.id_obra = or.id_obra                                                                      "
                 + "    INNER JOIN responsavei r                                                                            " 
                 + "        ON r.id_responsavel = or.id_responsavel                                                         "
                 + "WHERE o.tipo_obra = COALESCE(CAST(CAST(:tipoObra AS TEXT) AS INTEGER), o.tipo_obra)                     "
                 + "    AND r.cd_responsavel = COALESCE(CAST(:codigoResponsavel AS TEXT), r.id_responsavel)                 "
                 + "LIMIT :pageSize                                                                                         "
                 + "OFFSET (:pageSize * :pageIndex)                                                                         "
        ,nativeQuery = true
    )
    Optional<List<Obra>> getObras(@Param("tipoObra") Integer tipoObra,
                                  @Param("codigoResponsavel") String codigoResponsavel,
                                  @Param("pageIndex") int pageIndex,
                                  @Param("pageSize") int pageSize
    );
}