package br.com.desafio.gerenciadorobras.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import br.com.desafio.gerenciadorobras.dtos.ResponsavelDTO;
import br.com.desafio.gerenciadorobras.entities.Responsavel;
import br.com.desafio.gerenciadorobras.repositories.ResponsavelRepository;
import jakarta.persistence.NoResultException;

@Service
public class ResponsavelService {
    
    @Autowired
    private ResponsavelRepository responsavelRepository;

    @Autowired
    private ModelMapper mapper;

    public List<ResponsavelDTO> getResponsaveis(int pageSize, int pageIndex) throws NoResultException {
        return responsavelRepository.getResponsaveis(pageIndex , pageSize)
            .orElseThrow(NoResultException::new)
                .stream()
                    .map(r -> mapper.map(r, ResponsavelDTO.class))
                        .collect(Collectors.toList());
    }

    public ResponsavelDTO save(ResponsavelDTO responsavelDTO) {
        validaResponsavel(responsavelDTO);
        return mapper.map(responsavelRepository.save(mapper.map(responsavelDTO, Responsavel.class)), ResponsavelDTO.class);
    }

    private void validaResponsavel(ResponsavelDTO responsavelDTO) throws NoResultException, DuplicateKeyException {
        if (responsavelDTO.getId() != null) {
            findOrThrow(responsavelDTO.getId());
        }
        responsavelRepository.findByCpf(responsavelDTO.getCpf()).ifPresent(r -> {
            if (!r.isMesmoResponsavel(r.getId())) {
                throw new DuplicateKeyException(String.format("CPF já cadastrado para o responsável %s", r.getNome()));
            }
        });
        responsavelRepository.findByCodigo(responsavelDTO.getCodigo()).ifPresent(r -> {
            if (!r.isMesmoResponsavel(r.getId())) {
                throw new DuplicateKeyException(String.format("Código já cadastrado para o responsável %s", r.getNome()));
            }
        });
    }

    public ResponsavelDTO getResponsavel(Long responsavelId) {
        return mapper.map(findOrThrow(responsavelId), ResponsavelDTO.class);
    }

    public Responsavel findOrThrow(Long responsavelId) throws NoResultException {
        return responsavelRepository.findById(responsavelId).orElseThrow(() ->  new NoResultException(String.format("Responsável com id %s não encontrado", responsavelId)));
    }

    public String deleteResponsavel(Long responsavelId) {
        responsavelRepository.delete(findOrThrow(responsavelId));
        return "Excluído com sucesso";
    }
}
