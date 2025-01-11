package com.anilkumawat.project.uberApp.services.impl;

import com.anilkumawat.project.uberApp.dto.DriverDto;
import com.anilkumawat.project.uberApp.dto.RiderDto;
import com.anilkumawat.project.uberApp.entities.Driver;
import com.anilkumawat.project.uberApp.entities.Rating;
import com.anilkumawat.project.uberApp.entities.Ride;
import com.anilkumawat.project.uberApp.entities.Rider;
import com.anilkumawat.project.uberApp.exceptions.ResourceNotFoundException;
import com.anilkumawat.project.uberApp.exceptions.RuntimeConflictException;
import com.anilkumawat.project.uberApp.repositories.DriverRepository;
import com.anilkumawat.project.uberApp.repositories.RatingRepository;
import com.anilkumawat.project.uberApp.repositories.RiderRepository;
import com.anilkumawat.project.uberApp.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final DriverRepository driverRepository;
    private final RiderRepository riderRepository;
    private final ModelMapper modelMapper;
    @Override
    public DriverDto rateDriver(Ride ride, Integer rating) {
        Rating ratingObj = ratingRepository.findByRide(ride).orElseThrow(()->new ResourceNotFoundException("Ride not found with id: "+ride.getId()));
        Driver driver = ride.getDriver();
        if(ratingObj.getDriverRating()!=null){
            throw new RuntimeConflictException("Driver has been already rated");
        }
        ratingObj.setDriverRating(rating);
        ratingRepository.save(ratingObj);
        Double newRating = ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(Rating::getDriverRating)
                .average().orElse(0.0);
        driver.setRating(newRating);
        Driver savedDriver = driverRepository.save(driver);
        return modelMapper.map(savedDriver,DriverDto.class);
    }

    @Override
    public RiderDto rateRider(Ride ride, Integer rating) {
        Rating ratingObj = ratingRepository.findByRide(ride).orElseThrow(()->new ResourceNotFoundException("Ride not found with id: "+ride.getId()));
        Rider rider = ride.getRider();
        if(ratingObj.getRiderRating()!=null){
            throw new RuntimeConflictException("Rider has been already rated");
        }
        ratingObj.setRiderRating(rating);
        ratingRepository.save(ratingObj);
        Double newRating = ratingRepository.findByRider(rider)
                .stream()
                .mapToDouble(Rating::getRiderRating)
                .average().orElse(0.0);
        rider.setRating(newRating);
        Rider savedRider = riderRepository.save(rider);

        return modelMapper.map(savedRider,RiderDto.class);
    }

    @Override
    public void createNewRating(Ride ride) {
        Rating rating = Rating.builder()
                .driver(ride.getDriver())
                .rider(ride.getRider())
                .ride(ride)
                .build();
        ratingRepository.save(rating);
    }
}
