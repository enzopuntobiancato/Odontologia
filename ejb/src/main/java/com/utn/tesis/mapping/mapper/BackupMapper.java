package com.utn.tesis.mapping.mapper;

import com.utn.tesis.mapping.dto.BackupDTO;
import com.utn.tesis.model.Backup;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface BackupMapper {
    BackupDTO toDTO(Backup source);
    List<BackupDTO> toDTOList(List<Backup> sourceList);
}
