package com.mm.homeworks.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mm.homeworks.model.entity.Role;

@Configuration
public class ApplicationBeanConfiguration {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setAmbiguityIgnored(true);

		Converter<Role, String> toAuthorityString = new Converter<Role, String>() {
			public String convert(MappingContext<Role, String> context) {
				return context.getSource() == null ? null
						: context.getSource().getAuthority().substring("ROLE_".length());
			}
		};

		mapper.addConverter(toAuthorityString);
		return mapper;
	}
}
