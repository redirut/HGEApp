package com.example.hgeapp.services;

import com.example.hgeapp.exceptions.ServicesException;
import com.example.hgeapp.exceptions.ValidatorException;
import com.example.hgeapp.models.City;

import java.util.List;

public interface CityService {

    City createCity(City city) throws ServicesException;

    City addNote(String cityName, String note) throws ServicesException, ValidatorException;

    List<City> getAllCity();

    City getCity(String cityName) throws ServicesException, ValidatorException;

    City updateCity(City city) throws ServicesException, ValidatorException;

    void remove(City city) throws ServicesException, ValidatorException;

    void removeByName(String cityName) throws ServicesException, ValidatorException;
}
