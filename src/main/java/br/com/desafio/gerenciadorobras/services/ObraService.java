package br.com.desafio.gerenciadorobras.services;

import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.desafio.gerenciadorobras.dtos.ObraDTO;
import br.com.desafio.gerenciadorobras.entities.Obra;
import br.com.desafio.gerenciadorobras.filters.ObraFilter;
import br.com.desafio.gerenciadorobras.repository.ObraRepository;
import jakarta.persistence.NoResultException;

public class ObraService {

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private ModelMapper mapper;

    public List<ObraDTO> getObras(int pageSize, int pageIndex, ObraFilter filter) throws NoResultException, Exception {
        return obraRepository.getObras(filter.getTipoZona().ordinal(), filter.getIdResponsavel(), pageIndex , pageSize)
                    .orElseThrow(NoResultException::new)
                        .stream()
                            .map(o -> mapper.map(o, ObraDTO.class))
                                .collect(Collectors.toList());
    }

    public ObraDTO save(ObraDTO obraDTO) throws Exception {
        return mapper.map(obraRepository.save(mapper.map(obraDTO, Obra.class)), ObraDTO.class);
    }

    public ObraDTO update(Long idObra, ObraDTO obraDTO) {
        Obra obra = obraRepository.findById(idObra).orElseThrow(NoResultException::new);
        obra.setDescricao(obraDTO.getDescricao() != null ? obraDTO.getDescricao() : obra.getDescricao());
        obra.setNumero(obraDTO.getNumero() != null ? obraDTO.getNumero() : obra.getNumero());
        obra.setZona(obra.getZona() != null ? obraDTO.getZona() : obra.getZona());
        return mapper.map(obraRepository.save(obra), ObraDTO.class);
    }

    public ObraDTO getObra(Long obraId) throws NoResultException, Exception {
        return mapper.map(obraRepository.findById(obraId).orElseThrow(NoResultException::new), ObraDTO.class);
    }

    public String deleteObra(Long obraId) throws NoResultException, Exception{
        obraRepository.deleteById(obraId);
        return "Excluído com sucesso";
    }

}
