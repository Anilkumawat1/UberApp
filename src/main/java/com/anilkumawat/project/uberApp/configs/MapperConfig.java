package com.anilkumawat.project.uberApp.configs;

import com.anilkumawat.project.uberApp.dto.PointDto;
import com.anilkumawat.project.uberApp.utils.GeometryUtil;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper getMapper(){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(PointDto.class, Point.class).setConverter(mappingContext -> {
            PointDto pointDto = mappingContext.getSource();
            return GeometryUtil.createPoint(pointDto);
        });
        modelMapper.typeMap(Point.class,PointDto.class).setConverter(mappingContext -> {
            Point point = mappingContext.getSource();
            double []coordinates = {point.getX(),point.getY()};
            PointDto pointDto = new PointDto(coordinates);
            return pointDto;
        });
        return modelMapper;
    }
}
