package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.ProvinciaDTO;
import com.utn.tesis.model.Provincia;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 4/03/16
 * Time: 23:36
 * To change this template use File | Settings | File Templates.
 */
@Mapper(componentModel = "cdi")
public interface ProvinciaMapper {

    public ProvinciaDTO toDTO(Provincia provincia);

    public Provincia fromDTO(ProvinciaDTO dto);

    public List<ProvinciaDTO> toDTOList(List<Provincia> provincias);

}
