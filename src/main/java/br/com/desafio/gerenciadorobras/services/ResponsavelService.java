package br.com.desafio.gerenciadorobras.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.desafio.gerenciadorobras.dtos.ResponsavelDTO;
import br.com.desafio.gerenciadorobras.entities.Responsavel;
import br.com.desafio.gerenciadorobras.repository.ResponsavelRepository;
import jakarta.persistence.NoResultException;

public class ResponsavelService {
    
    @Autowired
    private ResponsavelRepository responsavelRepository;

    @Autowired
    private ModelMapper mapper;

    public List<ResponsavelDTO> getResponsaveis(int pageSize, int pageIndex) {
        return responsavelRepository.getResponsaveis(pageIndex , pageSize)
            .orElseThrow(NoResultException::new)
                .stream()
                    .map(r -> mapper.map(r, ResponsavelDTO.class))
                        .collect(Collectors.toList());
    }

    public ResponsavelDTO save(@Valid ResponsavelDTO responsavelDTO) {
        return mapper.map(responsavelRepository.save(mapper.map(responsavelDTO, Responsavel.class)), ResponsavelDTO.class);
    }

    public ResponsavelDTO update(Long idObra, ResponsavelDTO responsavelDTO) {
        Responsavel responsavel = responsavelRepository.findById(idObra).orElseThrow(NoResultException::new);
        responsavel.setCodigo(responsavelDTO.getCodigo() != null ? responsavelDTO.getCodigo() : responsavel.getCodigo());
        responsavel.setCpf(responsavelDTO.getCpf() != null ? responsavelDTO.getCpf() : responsavel.getCpf());
        responsavel.setNome(responsavelDTO.getNome() != null ? responsavelDTO.getNome() : responsavel.getNome());
        responsavelRepository.save(responsavel);
        return mapper.map(responsavel, ResponsavelDTO.class);
    }

    public ResponsavelDTO getResponsavel(Long responsavelId) {
        return mapper.map(responsavelRepository.findById(responsavelId).orElseThrow(NoResultException::new), ResponsavelDTO.class);
    }
}
