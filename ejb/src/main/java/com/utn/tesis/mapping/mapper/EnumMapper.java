package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.EnumDTO;
import com.utn.tesis.model.*;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 24/01/16
 * Time: 17:23
 */
@Mapper(componentModel = "cdi")
public abstract class EnumMapper {

    public EnumDTO sexoToDTO(Sexo source) {
        return source != null ? new EnumDTO(source.name(), source.toString()) : null;
    }

    public Sexo sexoFromDTO(EnumDTO source) {
        return Sexo.valueOf(source.getKey());
    }

    public abstract List<EnumDTO> sexoListToDTOList(List<Sexo> sexoList);

    public EnumDTO tipoDocumentoToDTO(TipoDocumento source) {
        return source != null ? new EnumDTO(source.name(), source.toString()) : null;
    }

    public TipoDocumento tipoDocumentoFromDTO(EnumDTO enumDTO) {
        return TipoDocumento.valueOf(enumDTO.getKey());
    }

    public abstract List<EnumDTO> tipoDocumentoListToDTOList(List<TipoDocumento> tipoDocumentoList);

    public EnumDTO cargoToDTO(Cargo source) {
        return source != null ? new EnumDTO(source.name(), source.toString()) : null;
    }

    public Cargo cargoFromDTO(EnumDTO source) {
        return Cargo.valueOf(source.getKey());
    }

    public abstract List<EnumDTO> cargoListToDTOList(List<Cargo> list);

    public EnumDTO nivelToDTO(Nivel source) {
        return source != null ? new EnumDTO(source.name(), source.toString()) : null;
    }

    public Nivel nivelFromDTO(EnumDTO source) {
        return Nivel.valueOf(source.getKey());
    }

    public abstract List<EnumDTO> nivelListToDTOList(List<Nivel> list);

    public EnumDTO diaToDTO(Dia source) {
        return source != null ? new EnumDTO(source.name(), source.toString()) : null;
    }

    public Dia diaFromDTO(EnumDTO source) {
        return Dia.valueOf(source.getKey());
    }

    public abstract List<EnumDTO> diaListToDTOList(List<Dia> list);

}
