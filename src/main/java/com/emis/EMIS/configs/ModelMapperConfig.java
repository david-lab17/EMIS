package com.emis.EMIS.configs;

import com.emis.EMIS.models.TeacherEntity;
import com.emis.EMIS.wrappers.responseDTOs.TeacherDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        PropertyMap<TeacherEntity, TeacherDTO> teacherEntityTeacherDTOPropertyMap = new PropertyMap<TeacherEntity, TeacherDTO>() {
            protected void configure() {
                map().setFirstName(source.getUser().getFirstName());
                map().setMiddleName(source.getUser().getMiddleName());
                map().setLastName(source.getUser().getLastName());
                map().setEmail(source.getUser().getEmail());
                map().setPhoneNo(source.getUser().getPhoneNo());
                map().setNationalId(source.getUser().getNationalId());
                map().setGender(source.getUser().getGender());
                map().setNationality(source.getUser().getNationality());
                map().setDateOfBirth(source.getUser().getDateOfBirth());
            }
        };

        modelMapper.addMappings(teacherEntityTeacherDTOPropertyMap);
        return modelMapper;
    }
}
