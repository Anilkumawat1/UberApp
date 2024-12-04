package com.anilkumawat.project.uberApp.utils;

import com.anilkumawat.project.uberApp.dto.PointDto;
import org.locationtech.jts.geom.*;

public class GeometryUtil {
    public static Point createPoint(PointDto pointDto){
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(),4326);
        Coordinate coordinate = new Coordinate(pointDto.getCoordinates()[0],pointDto.getCoordinates()[1]);
        return geometryFactory.createPoint(coordinate);
    }
}
