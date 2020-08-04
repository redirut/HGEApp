package com.example.hgeapp.validator;

import com.example.hgeapp.exceptions.ValidatorException;
import com.example.hgeapp.repository.CityRepository;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    public void validateCityByName(String name, CityRepository repository) {
        repository.findByName(name).orElseThrow(() -> new ValidatorException("Validator Exception: City " + name + " not found"));
    }

    public boolean validateCityExistByName(String name, CityRepository repository) {
        return repository.findByName(name).isPresent();
    }

    public void validateCityById(Long id, CityRepository repository) {
        repository.findById(id).orElseThrow(() -> new ValidatorException("Validator Exception: City by " + id + " id not found"));
    }
}
