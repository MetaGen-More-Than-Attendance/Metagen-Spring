package com.hst.metagen.util.mapping;

import org.modelmapper.ModelMapper;

import java.util.List;

public interface ModelMapperService {
	
	ModelMapper forDto();
	ModelMapper forRequest();

	List<?> dtoToEntityList(List<?> dtoList, Class<?> destinationType);
	List<?> entityToDtoList(List<?> entityList, Class<?> destinationType);
	Object dtoToEntity(Object dto, Class<?> destinationType);
	Object entityToDto(Object entity, Class<?> destinationType);


}
