package com.example.hgeapp.controllers;

import com.example.hgeapp.exceptions.ServicesException;
import com.example.hgeapp.exceptions.ValidatorException;
import com.example.hgeapp.models.City;
import com.example.hgeapp.services.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

    private final static Logger log = LoggerFactory.getLogger(CityController.class);
    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @RequestMapping(value = "/add/{cityName}/add", method = RequestMethod.POST)
    ResponseEntity<?> addNoteToExistCity(@PathVariable String cityName, @RequestBody String note) {
        try {
            cityService.addNote(cityName, note);
        } catch (ServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ValidatorException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    ResponseEntity<?> addCity(@RequestBody City city) {
        try {
            cityService.createCity(city);
        } catch (ServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ValidatorException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    ResponseEntity<?> updateCity(@RequestBody City city) {
        try {
            cityService.updateCity(city);
        } catch (ServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ValidatorException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{cityName}", method = RequestMethod.GET)
    ResponseEntity<?> getCity(@PathVariable String cityName) {
        City city;
        try {
            city = cityService.getCity(cityName);
        } catch (ServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ValidatorException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    ResponseEntity<?> getAllCity() {
        return new ResponseEntity<>(cityService.getAllCity(), HttpStatus.OK);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    ResponseEntity<?> removeCity(@RequestBody City city) {
        try {
            cityService.removeById(city);
        } catch (ServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ValidatorException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/remove/{cityName}", method = RequestMethod.GET)
    ResponseEntity<?> removeCityByName(@PathVariable String cityName) {
        try {
            cityService.removeByName(cityName);
        } catch (ServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ValidatorException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}