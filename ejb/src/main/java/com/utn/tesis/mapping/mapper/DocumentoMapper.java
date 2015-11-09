package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.DocumentoDTO;
import com.utn.tesis.model.Documento;
import org.mapstruct.Mapper;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 24/01/16
 * Time: 17:38
 */
@Mapper(componentModel = "cdi", uses = EnumMapper.class)
public interface DocumentoMapper {

    DocumentoDTO toDTO(Documento documento);

    Documento fromDTO(DocumentoDTO documentoDTO);
}
