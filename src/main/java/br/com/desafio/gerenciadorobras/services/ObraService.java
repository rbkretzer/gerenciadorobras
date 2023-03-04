package br.com.desafio.gerenciadorobras.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.desafio.gerenciadorobras.dtos.ObraDTO;
import br.com.desafio.gerenciadorobras.dtos.ResponsavelDTO;
import br.com.desafio.gerenciadorobras.entities.Obra;
import br.com.desafio.gerenciadorobras.entities.ObraResponsavel;
import br.com.desafio.gerenciadorobras.enumerators.TipoObra;
import br.com.desafio.gerenciadorobras.filters.ObraFilter;
import br.com.desafio.gerenciadorobras.repositories.ObraRepository;
import br.com.desafio.gerenciadorobras.repositories.ObraResponsavelRepository;
import jakarta.persistence.NoResultException;
import jakarta.validation.ValidationException;

public class ObraService {

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private ObraResponsavelRepository obraResponsavelRepository;

    @Autowired
    private ModelMapper mapper;

    public List<ObraDTO> getObras(int pageSize, int pageIndex, ObraFilter filter) throws NoResultException, Exception {
        return obraRepository.getObras(filter.getTipoZona().ordinal(), filter.getCodigoResponsavel(), pageIndex , pageSize)
                    .orElseThrow(NoResultException::new)
                        .stream()
                            .map(o -> mapper.map(o, ObraDTO.class))
                                .collect(Collectors.toList());
    }

    public ObraDTO save(boolean isPublica, ObraDTO obraDTO) throws Exception {
        if (isPublica) {
            obraDTO.setTipoObra(TipoObra.PUBLICA);
            validateObraPublica(obraDTO);
        } else {
            obraDTO.setTipoObra(TipoObra.PRIVADA);
            validateObraPrivada(obraDTO);
        }
        Obra obra = obraRepository.save(mapper.map(obraDTO, Obra.class));
        List<ResponsavelDTO> responsaveis = saveAndRemoveResponsaveis(obraDTO.getResponsaveis(), obra);
        ObraDTO dto = mapper.map(obra, ObraDTO.class);
        dto.setResponsaveis(responsaveis);
        return dto;
    }

    private List<ResponsavelDTO> saveAndRemoveResponsaveis(List<ResponsavelDTO> responsaveis, Obra obra) {
        Optional<List<ObraResponsavel>> optResponsaveis = obraResponsavelRepository.findByObra(obra);
        if (optResponsaveis.isPresent()) {
            removeResponsaveis(responsaveis, optResponsaveis.get());
            addResponsaveis(responsaveis, optResponsaveis.get());
        }
        return null;
    }

    private void addResponsaveis(List<ResponsavelDTO> responsaveisDTO, List<ObraResponsavel> responsaveis) {
        for (ResponsavelDTO r : responsaveisDTO) {
        }
    }

    private void removeResponsaveis(List<ResponsavelDTO> responsaveisDTO, List<ObraResponsavel> responsaveis) {
        for (ObraResponsavel or : responsaveis) {
            if (responsaveisDTO.stream().noneMatch(r -> r.getId().equals(or.getResponsavel().getId()))) {
                obraResponsavelRepository.delete(or);
            }
        }
    }

    private void validateObraPrivada(ObraDTO obraDTO) {
        if (obraDTO.getZona() == null) {
            throw new ValidationException("Campo zona é obrigatório para obras privadas");
        }
        if (obraDTO.getAreaTotal() == null) {
            throw new ValidationException("Campo areaTotal é obrigatório para obras privadas");
        }
    }

    private void validateObraPublica(ObraDTO obraDTO) {
        if (obraDTO.getDataInicio() == null) {
            throw new ValidationException("Campo dataInicio é obrigatório para obras públicas");
        }
        if (obraDTO.getDataFim() == null) {
            throw new ValidationException("Campo dataFim é obrigatório para obras públicas");
        }
        if (obraDTO.getDataInicio().isAfter(obraDTO.getDataFim())) {
            throw new ValidationException("Campo dataInicio deve ser menor ou igual a dataFim");
        }
    }

    public ObraDTO getObra(Long obraId) throws NoResultException, Exception {
        return mapper.map(obraRepository.findById(obraId).orElseThrow(NoResultException::new), ObraDTO.class);
    }

    public String deleteObra(Long obraId) throws NoResultException, Exception {
        obraRepository.deleteById(obraId);
        return "Excluído com sucesso";
    }

}
