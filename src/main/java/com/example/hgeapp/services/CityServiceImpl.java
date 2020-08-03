package com.example.hgeapp.services;

import com.example.hgeapp.exceptions.ServicesException;
import com.example.hgeapp.exceptions.ValidatorException;
import com.example.hgeapp.models.City;
import com.example.hgeapp.repository.CityRepository;
import com.example.hgeapp.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final static Logger log = LoggerFactory.getLogger(CityServiceImpl.class);
    private final CityRepository cityRepository;
    private final Validator validator;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, Validator validator) {
        this.cityRepository = cityRepository;
        this.validator = validator;
    }

    @Override
    public City createCity(City city) throws ServicesException {
        if (city == null) {
            log.debug("createCity: City or note should not be null");
            throw new ServicesException("Illegal arguments. City or note should not be null");
        }
        if (validator.validateCityExistByName(city.getName(), cityRepository)) {
            log.debug("createCity: City with name " + city.getName() + "is exist");
            throw new ServicesException("Name failure. City with name " + city.getName() + "is exist");
        }
        return cityRepository.save(city);
    }

    @Override
    public City addNote(String cityName, String note) throws ServicesException, ValidatorException {
        if (cityName == null || cityName.isEmpty() || note == null || note.isEmpty()) {
            log.debug("addNote: City name or note should not be empty");
            throw new ServicesException("Illegal arguments. City name or note should not be empty");
        }
        log.debug("addNote: start validate city with:" + cityName);
        validator.validateCityByName(cityName, cityRepository);
        City city = cityRepository.findByName(cityName).get();
        List<String> notes = city.getNotes();
        notes.add(note);
        city.setNotes(notes);
        return cityRepository.save(city);
    }

    @Override
    public List<City> getAllCity() {
        return cityRepository.findAll();
    }

    @Override
    public City getCity(String cityName) throws ServicesException, ValidatorException {
        if (cityName == null) {
            log.debug("getCity: City name should not be empty");
            throw new ServicesException("Illegal arguments. City name should not be empty");
        }
        log.debug("getCity: start validate city " + cityName);
        validator.validateCityByName(cityName, cityRepository);
        return cityRepository.findByName(cityName).get();
    }

    @Override
    public City updateCity(City city) throws ServicesException, ValidatorException {
        if (city == null) {
            log.debug("updateCity: City should not be null");
            throw new ServicesException("Illegal arguments. City should not be null");
        }
        log.debug("updateCity: start validate city with id:" + city.getId());
        validator.validateCityById(city.getId(), cityRepository);
        City updateCity = cityRepository.findById(city.getId()).get();
        updateCity.setId(city.getId());
        updateCity.setName(city.getName());
        updateCity.setNotes(city.getNotes());
        return cityRepository.save(city);
    }

    @Override
    public void remove(City city) throws ServicesException, ValidatorException {
        if (city == null) {
            log.debug("remove: City should not be null");
            throw new ServicesException("Illegal arguments. City should not be null");
        }
        log.debug("remove: start validate city " + city.getId() + " id");
        validator.validateCityById(city.getId(), cityRepository);
        cityRepository.delete(city);
    }

    @Override
    public void removeByName(String cityName) throws ServicesException, ValidatorException {
        if (cityName == null) {
            log.debug("remove: City name should not be empty");
            throw new ServicesException("Illegal arguments. City name should not be empty");
        }
        log.debug("remove: start validate city " + cityName);
        validator.validateCityByName(cityName, cityRepository);
        cityRepository.deleteById(cityRepository.findByName(cityName).get().getId());
    }
}
