package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.CampoDetalleDTO;
import com.utn.tesis.mapping.dto.CampoEnumerableDTO;
import com.utn.tesis.mapping.dto.CampoSiNoDTO;
import com.utn.tesis.mapping.dto.DetalleHistoriaClinicaDTO;
import com.utn.tesis.model.CampoDetalle;
import com.utn.tesis.model.CampoEnumerable;
import com.utn.tesis.model.CampoSiNo;
import com.utn.tesis.model.DetalleHistoriaClinica;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 8/05/16
 * Time: 19:41
 * To change this template use File | Settings | File Templates.
 */
@Mapper(componentModel = "cdi")
public abstract class DetalleHistoriaClinicaMapper{


    @Mapping(target = "dtype", constant = "campoDetalle")
    public abstract CampoDetalleDTO campoDetalleToDTO(CampoDetalle source);

    @Mapping(target = "dtype", constant = "campoSiNo")
    public abstract CampoSiNoDTO campoSiNoToDTO(CampoSiNo source);

    @Mapping(target = "dtype", constant = "campoEnumerable")
    public abstract CampoEnumerableDTO campoEnumerableToDTO(CampoEnumerable source);

    public abstract CampoDetalle campoDetalleFromDTO(CampoDetalleDTO souruce);

    public abstract CampoSiNo campoSiNoFromDTO(CampoSiNoDTO souruce);

    public abstract CampoEnumerable campoEnumerableFromDTO(CampoEnumerableDTO source);

    public abstract CampoDetalle updateCampoDetalleFromDTO(CampoDetalleDTO source, @MappingTarget CampoDetalle target);

    public abstract CampoSiNo updateCampoSiNoFromDTO(CampoSiNoDTO source, @MappingTarget CampoSiNo target);

    public abstract CampoEnumerable updateCampoEnumerableFromDTO(CampoEnumerableDTO source,  @MappingTarget CampoEnumerable target);

    public DetalleHistoriaClinicaDTO toDTO(DetalleHistoriaClinica source){
        if(source instanceof CampoDetalle){
            return this.campoDetalleToDTO((CampoDetalle) source);
        } else if (source instanceof CampoEnumerable){
            return campoEnumerableToDTO((CampoEnumerable) source);
        } else if (source instanceof CampoSiNo){
            return campoSiNoToDTO((CampoSiNo) source);
        }
        return null;
    }

    public DetalleHistoriaClinica fromDTO(DetalleHistoriaClinicaDTO source){
        if(source instanceof CampoDetalleDTO){
            return this.campoDetalleFromDTO((CampoDetalleDTO) source);
        } else if (source instanceof CampoEnumerableDTO){
            return campoEnumerableFromDTO((CampoEnumerableDTO) source);
        } else if (source instanceof CampoSiNoDTO){
            return campoSiNoFromDTO((CampoSiNoDTO) source);
        }
        return null;
    }

    public void updateFromDTO(DetalleHistoriaClinicaDTO source, DetalleHistoriaClinica target){
        if(source instanceof CampoDetalleDTO){
            updateCampoDetalleFromDTO((CampoDetalleDTO) source, (CampoDetalle) target);
        } else if (source instanceof CampoEnumerableDTO){
            updateCampoEnumerableFromDTO((CampoEnumerableDTO) source, (CampoEnumerable) target);
        } else if (source instanceof CampoSiNoDTO){
            updateCampoSiNoFromDTO((CampoSiNoDTO) source, (CampoSiNo) target);
        }
    }
}
