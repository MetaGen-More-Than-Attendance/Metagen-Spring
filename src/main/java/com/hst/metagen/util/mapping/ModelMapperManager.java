package com.hst.metagen.util.mapping;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelMapperManager implements ModelMapperService{
	
	private ModelMapper modelMapper;

	@Autowired
	public ModelMapperManager(ModelMapper modelMapper) {
		super();
		this.modelMapper = modelMapper;
	}

	@Override
	public ModelMapper forDto() {
		//this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper;
	}

	@Override
	public ModelMapper forRequest() {
		//this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.STANDARD);
		return modelMapper;
	}

	@Override
	public <T> List<T> dtoToEntityList(List<?> dtoList, Class<T> destinationType) {
		return dtoList.stream()
				.map(dto -> forRequest().map(dto, destinationType)).collect(Collectors.toList());
	}

	@Override
	public <T> List<T> entityToDtoList(List<?> entityList, Class<T> destinationType) {
		return entityList.stream()
				.map(entity -> forDto().map(entity, destinationType)).collect(Collectors.toList());
	}

	@Override
	public <T> T dtoToEntity(Object dto, Class<T> destinationType) {
		return forRequest().map(dto, destinationType);
	}

	@Override
	public <T> T entityToDto(Object entity, Class<T> destinationType) {
		return forDto().map(entity, destinationType);
	}

}
