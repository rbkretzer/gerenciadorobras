package br.com.desafio.gerenciadorobras.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import br.com.desafio.gerenciadorobras.dtos.ObraDTO;
import br.com.desafio.gerenciadorobras.dtos.ResponsavelDTO;
import br.com.desafio.gerenciadorobras.entities.Obra;
import br.com.desafio.gerenciadorobras.entities.ObraResponsavel;
import br.com.desafio.gerenciadorobras.entities.Responsavel;
import br.com.desafio.gerenciadorobras.enumerators.TipoObra;
import br.com.desafio.gerenciadorobras.filters.ObraFilter;
import br.com.desafio.gerenciadorobras.repositories.ObraRepository;
import br.com.desafio.gerenciadorobras.repositories.ObraResponsavelRepository;
import jakarta.persistence.NoResultException;
import jakarta.validation.ValidationException;

@Service
public class ObraService {

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private ObraResponsavelRepository obraResponsavelRepository;

    @Autowired
    private ResponsavelService responsavelService;

    @Autowired
    private ModelMapper mapper;

    public List<ObraDTO> getObras(int pageSize, int pageIndex, ObraFilter filter) throws NoResultException {
        return obraRepository.getObras(filter.getTipoObra() != null ? filter.getTipoObra().ordinal() : null, filter.getCodigoResponsavel(), pageIndex , pageSize)
                    .orElseThrow(NoResultException::new)
                        .stream()
                            .map(o -> mapper.map(o, ObraDTO.class))
                                .collect(Collectors.toList());
    }

    public ObraDTO save(boolean isPublica, ObraDTO obraDTO) throws ValidationException, NoResultException {
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

    private List<ResponsavelDTO> saveAndRemoveResponsaveis(List<ResponsavelDTO> responsaveis, Obra obra) throws NoResultException {
        Optional<List<ObraResponsavel>> optResponsaveis = obraResponsavelRepository.findByObra(obra);
        if (optResponsaveis.isPresent()) {
            removeResponsaveis(responsaveis, optResponsaveis.get());
        }
        return addResponsaveis(responsaveis, obra);
    }

    private void removeResponsaveis(List<ResponsavelDTO> responsaveisDTO, List<ObraResponsavel> responsaveis) {
        for (ObraResponsavel or : responsaveis) {
            if (responsaveisDTO.stream().noneMatch(r -> r.getId().equals(or.getResponsavel().getId()))) {
                obraResponsavelRepository.delete(or);
            }
        }
    }
    
    private List<ResponsavelDTO> addResponsaveis(List<ResponsavelDTO> responsaveis, Obra obra) throws NoResultException {
        List<ResponsavelDTO> responsaveisObra = new ArrayList<>();
        for (ResponsavelDTO r : responsaveis) {
            Responsavel responsavel = responsavelService.findOrThrow(r.getId());
            Optional<ObraResponsavel> optOr = obraResponsavelRepository.findByObraResponsavel(obra.getId(), responsavel.getId());
            responsaveisObra.add(mapper.map(r, ResponsavelDTO.class));
            if (optOr.isPresent()) {
                continue;
            }
            obraResponsavelRepository.save(ObraResponsavel.builder().responsavel(responsavel).obra(obra).build());

        }
        return responsaveisObra;
    }

    private void validateObraPrivada(ObraDTO obraDTO) throws ValidationException {
        validaNumeracaoRepetida(obraDTO.getNumero(), TipoObra.PRIVADA);
        if (obraDTO.getZona() == null) {
            throw new ValidationException("Campo zona é obrigatório para obras privadas");
        }
        if (obraDTO.getAreaTotal() == null) {
            throw new ValidationException("Campo areaTotal é obrigatório para obras privadas");
        }
    }

    private void validateObraPublica(ObraDTO obraDTO) throws ValidationException {
        validaNumeracaoRepetida(obraDTO.getNumero(), TipoObra.PUBLICA);
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

    private void validaNumeracaoRepetida(Long numero, TipoObra tipo) {
        obraRepository.findByNumeroTipoObra(numero, tipo.ordinal()).ifPresent(o -> {
            throw new DuplicateKeyException(String.format("Número já cadastrado para uma obra %s", TipoObra.PRIVADA.equals(tipo) ? "privada" : "pública"));
        });
    }

    public ObraDTO getObra(Long obraId) throws NoResultException  {
        Obra obra = obraRepository.findById(obraId).orElseThrow(NoResultException::new);
        ObraDTO obraDTO = mapper.map(obra, ObraDTO.class);
        obraResponsavelRepository.findByObra(obra).ifPresent(l -> tranformResponsaveis(obraDTO, l));
        return obraDTO;
    }

    private void tranformResponsaveis(ObraDTO obraDTO, List<ObraResponsavel> l) {
        obraDTO.setResponsaveis(l.stream().map(or -> mapper.map(or.getResponsavel(), ResponsavelDTO.class)).collect(Collectors.toList()));
    }

    public String deleteObra(Long obraId) throws NoResultException {
        obraRepository.deleteById(obraId);
        return "Excluído com sucesso";
    }

}
