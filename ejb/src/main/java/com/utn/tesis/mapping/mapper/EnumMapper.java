package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.EnumDTO;
import com.utn.tesis.model.Cargo;
import com.utn.tesis.model.Sexo;
import com.utn.tesis.model.TipoDocumento;
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

    public EnumDTO tipoDocumentoToDTO(TipoDocumento source) {
        return source != null ? new EnumDTO(source.name(), source.toString()) : null;
    }

    public abstract List<EnumDTO> sexoListToDTOList(List<Sexo> sexoList);

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

}
