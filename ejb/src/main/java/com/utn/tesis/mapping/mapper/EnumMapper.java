package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.EnumDTO;
import com.utn.tesis.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 24/01/16
 * Time: 17:23
 */
@Mapper(componentModel = "cdi", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public abstract class EnumMapper {

    //SEXO
    public EnumDTO sexoToDTO(Sexo source) {
        return source != null ? new EnumDTO(source.name(), source.toString()) : null;
    }

    public Sexo sexoFromDTO(EnumDTO source) {
        return source != null ? Sexo.valueOf(source.getKey()) : null;
    }

    public abstract List<EnumDTO> sexoListToDTOList(List<Sexo> sexoList);

    //TIPO_DOCUMENTO
    public EnumDTO tipoDocumentoToDTO(TipoDocumento source) {
        return source != null ? new EnumDTO(source.name(), source.toString()) : null;
    }

    public TipoDocumento tipoDocumentoFromDTO(EnumDTO source) {
        return source != null ? TipoDocumento.valueOf(source.getKey()) : null;
    }

    public abstract List<EnumDTO> tipoDocumentoListToDTOList(List<TipoDocumento> tipoDocumentoList);

    //CARGO
    public EnumDTO cargoToDTO(Cargo source) {
        return source != null ? new EnumDTO(source.name(), source.toString()) : null;
    }

    public Cargo cargoFromDTO(EnumDTO source) {
        return Cargo.valueOf(source.getKey());
    }

    public abstract List<EnumDTO> cargoListToDTOList(List<Cargo> list);

    //NIVEL_MATERIAS
    public EnumDTO nivelToDTO(Nivel source) {
        return source != null ? new EnumDTO(source.name(), source.toString()) : null;
    }

    public Nivel nivelFromDTO(EnumDTO source) {
        return source != null ? Nivel.valueOf(source.getKey()) : null;
    }

    public abstract List<EnumDTO> nivelListToDTOList(List<Nivel> list);

    //DÍAS
    public EnumDTO diaToDTO(Dia source) {
        return source != null ? new EnumDTO(source.name(), source.toString()) : null;
    }

    public Dia diaFromDTO(EnumDTO source) {
        return  source != null ? Dia.valueOf(source.getKey()) : null;
    }

    public abstract List<EnumDTO> diaListToDTOList(List<Dia> list);

    //ESTADO_CIVIL
    public EnumDTO estadoCivilToDTO(EstadoCivil source) {
        return source != null ? new EnumDTO(source.name(), source.toString()) : null;
    }

    public EstadoCivil estadoCivilFromDTO(EnumDTO source) {
        return source != null ? EstadoCivil.valueOf(source.getKey()) : null;
    }

    public abstract List<EnumDTO> estadoCivilListToDTOList(List<EstadoCivil> estadoCivilList);

    //NIVEL_ESTUDIO
    public EnumDTO nivelEstudioToDTO(NivelEstudio source) {
        return source != null ? new EnumDTO(source.name(), source.toString()) : null;
    }

    public NivelEstudio nivelEstudioFromDTO(EnumDTO source) {
        return source != null ? NivelEstudio.valueOf(source.getKey()) : null;
    }

    public abstract List<EnumDTO> nivelEstudioListToDTOList(List<NivelEstudio>nivelEstudioList);

    //NACIONALIDAD
    public EnumDTO nacionalidadToDTO(Nacionalidad source) {
        return source != null ? new EnumDTO(source.name(), source.toString()) : null;
    }

    public Nacionalidad nacionalidadFromDTO(EnumDTO source) {
        return source != null ? Nacionalidad.valueOf(source.getKey()) : null;
    }

    public abstract List<EnumDTO> nacionalidadListToDTOList(List<Nacionalidad> nacionalidadList);
}
