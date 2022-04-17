package com.hst.metagen.util.mapping;

import org.modelmapper.ModelMapper;

import java.util.List;

public interface ModelMapperService {
	
	ModelMapper forDto();
	ModelMapper forRequest();

	<T> List<T> dtoToEntityList(List<?> dtoList, Class<T> destinationType);
	<T> List<T> entityToDtoList(List<?> entityList, Class<T> destinationType);
	<T> T dtoToEntity(Object dto, Class<T> destinationType);
	<T> T entityToDto(Object entity, Class<T> destinationType);


}
