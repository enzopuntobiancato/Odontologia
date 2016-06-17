package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.CampoDetalleDTO;
import com.utn.tesis.mapping.dto.CampoEnumerableDTO;
import com.utn.tesis.mapping.dto.CampoFechaDTO;
import com.utn.tesis.mapping.dto.CampoSiNoDTO;
import com.utn.tesis.model.CampoDetalle;
import com.utn.tesis.model.CampoEnumerable;
import com.utn.tesis.model.CampoFecha;
import com.utn.tesis.model.CampoSiNo;


/**
 * Created with IntelliJ IDEA.
 * User: Usuario
 * Date: 15/05/16
 * Time: 15:06
 * To change this template use File | Settings | File Templates.
 */
public class DetalleMapper {

    public CampoDetalleDTO campoDetalleToDTO(CampoDetalle source) {
        if (source == null) {
            return null;
        }
            CampoDetalleDTO campoDetalleDTO = new CampoDetalleDTO();

            campoDetalleDTO.setId(source.getId());
            if (source.getVersion() != null) {
                campoDetalleDTO.setVersion(source.getVersion());
            }
            campoDetalleDTO.setNombre(source.getNombre());
            if (source.getGrupo() != null) {
                campoDetalleDTO.setGrupo(source.getGrupo());
            }
            if (source.getPregunta() != null) {
                campoDetalleDTO.setPregunta(source.getPregunta());
            }

            if(source.getOnly_detalle() != null){
                campoDetalleDTO.setOnly_detalle(source.getOnly_detalle());
            }

            campoDetalleDTO.setDtype("campoDetalle");

            return campoDetalleDTO;
    }

    public CampoFechaDTO campoFechaToDTO(CampoFecha source) {
        if (source == null) {
            return null;
        }
        CampoFechaDTO campoFechaDTO = new CampoFechaDTO();

        campoFechaDTO.setId(source.getId());
        if (source.getVersion() != null) {
            campoFechaDTO.setVersion(source.getVersion());
        }
        campoFechaDTO.setNombre(source.getNombre());
        if (source.getGrupo() != null) {
            campoFechaDTO.setGrupo(source.getGrupo());
        }
        if (source.getPregunta() != null) {
            campoFechaDTO.setPregunta(source.getPregunta());
        }

        if(source.getFecha() != null){
            campoFechaDTO.setFecha(source.getFecha());
        }

        campoFechaDTO.setDtype("campoFecha");

        return campoFechaDTO;
    }


    public CampoSiNoDTO campoSiNoToDTO(CampoSiNo source) {
        if ( source == null ) {
            return null;
        }

        CampoSiNoDTO campoSiNoDTO = new CampoSiNoDTO();

        campoSiNoDTO.setId( source.getId() );
        if ( source.getVersion() != null ) {
            campoSiNoDTO.setVersion( source.getVersion() );
        }
        campoSiNoDTO.setNombre( source.getNombre() );
        if ( source.getGrupo() != null ) {
            campoSiNoDTO.setGrupo( source.getGrupo() );
        }
        if ( source.getPregunta() != null ) {
            campoSiNoDTO.setPregunta( source.getPregunta() );
        }
        campoSiNoDTO.setSiNo( source.getSiNo() );

        campoSiNoDTO.setDtype( "campoSiNo" );

        return campoSiNoDTO;
    }

    public CampoEnumerableDTO campoEnumerableToDTO(CampoEnumerable source) {
        if ( source == null ) {
            return null;
        }

        CampoEnumerableDTO campoEnumerableDTO = new CampoEnumerableDTO();

        campoEnumerableDTO.setId( source.getId() );
        if ( source.getVersion() != null ) {
            campoEnumerableDTO.setVersion( source.getVersion() );
        }
        campoEnumerableDTO.setNombre( source.getNombre() );
        if ( source.getGrupo() != null ) {
            campoEnumerableDTO.setGrupo( source.getGrupo() );
        }
        if ( source.getPregunta() != null ) {
            campoEnumerableDTO.setPregunta( source.getPregunta() );
        }
        campoEnumerableDTO.setChecked( source.getChecked() );

        campoEnumerableDTO.setDtype( "campoEnumerable" );

        return campoEnumerableDTO;
    }

    public CampoDetalle campoDetalleFromDTO(CampoDetalleDTO source) {
        if ( source == null ) {
            return null;
        }

        CampoDetalle campoDetalle = new CampoDetalle();

        campoDetalle.setVersion(source.getVersion());
        campoDetalle.setId( source.getId() );
        campoDetalle.setNombre(source.getNombre());
        campoDetalle.setGrupo( source.getGrupo() );
        campoDetalle.setPregunta(source.getPregunta());

        campoDetalle.setOnly_detalle(source.getOnly_detalle() != null
                ? source.getOnly_detalle()
                : null);

        return campoDetalle;
    }

    public CampoFecha campoFechaFromDTO(CampoFechaDTO source) {
        if ( source == null ) {
            return null;
        }

        CampoFecha campoFecha = new CampoFecha();

        campoFecha.setVersion(source.getVersion());
        campoFecha.setId(source.getId());
        campoFecha.setNombre(source.getNombre());
        campoFecha.setGrupo(source.getGrupo());
        campoFecha.setPregunta(source.getPregunta());

        campoFecha.setFecha(source.getFecha() != null
                ? source.getFecha()
                : null);

        return campoFecha;
    }

    public CampoSiNo campoSiNoFromDTO(CampoSiNoDTO source) {
        if ( source == null ) {
            return null;
        }

        CampoSiNo campoSiNo = new CampoSiNo();

        campoSiNo.setVersion(source.getVersion());
        campoSiNo.setId( source.getId() );
        campoSiNo.setNombre(source.getNombre());
        campoSiNo.setGrupo( source.getGrupo() );
        campoSiNo.setPregunta(source.getPregunta());
        campoSiNo.setSiNo( source.getSiNo() );

        return campoSiNo;
    }

    public CampoEnumerable campoEnumerableFromDTO(CampoEnumerableDTO source) {
        if ( source == null ) {
            return null;
        }

        CampoEnumerable campoEnumerable = new CampoEnumerable();

        campoEnumerable.setVersion( source.getVersion() );
        campoEnumerable.setId( source.getId() );
        campoEnumerable.setNombre( source.getNombre() );
        campoEnumerable.setGrupo( source.getGrupo() );
        campoEnumerable.setPregunta( source.getPregunta() );
        campoEnumerable.setChecked( source.getChecked() );

        return campoEnumerable;
    }

    public CampoDetalle updateCampoDetalleFromDTO(CampoDetalleDTO source, CampoDetalle target) {
        if ( source == null ) {
            return null;
        }

        target.setVersion( source.getVersion() );
        target.setId( source.getId() );
        target.setNombre( source.getNombre() );
        target.setGrupo( source.getGrupo() );
        target.setPregunta( source.getPregunta() );
        target.setOnly_detalle( source.getOnly_detalle() );

        return target;
    }

    public CampoSiNo updateCampoSiNoFromDTO(CampoSiNoDTO source, CampoSiNo target) {
        if ( source == null ) {
            return null;
        }

        target.setVersion( source.getVersion() );
        target.setId( source.getId() );
        target.setNombre( source.getNombre() );
        target.setGrupo( source.getGrupo() );
        target.setPregunta( source.getPregunta() );
        target.setSiNo( source.getSiNo() );

        return target;
    }

    public CampoEnumerable updateCampoEnumerableFromDTO(CampoEnumerableDTO source, CampoEnumerable target) {
        if ( source == null ) {
            return null;
        }

        target.setVersion( source.getVersion() );
        target.setId( source.getId() );
        target.setNombre( source.getNombre() );
        target.setGrupo( source.getGrupo() );
        target.setPregunta( source.getPregunta() );
        target.setChecked( source.getChecked() );

        return target;
    }
}
