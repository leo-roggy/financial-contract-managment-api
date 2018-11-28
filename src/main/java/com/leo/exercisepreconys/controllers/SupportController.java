package com.leo.exercisepreconys.controllers;

import com.leo.exercisepreconys.business.Support;
import com.leo.exercisepreconys.services.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SupportController {

    private SupportService supportService;

    @Autowired
    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }


    @GetMapping("/support")
    public List<Support> getSupports() {
        return supportService.getAllSupports();
    }

    @GetMapping("/support/{isin}")
    public Object searchSupportsByIsin(@PathVariable("isin") String isin) {
        Optional<Support> support = supportService.searchSupportByIsin(isin);

        if(support.isPresent())
            return support.get();
        else
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/support/name/{name}")
    public List<Support> searchSupportsByName(@PathVariable("name") String name) {
        return supportService.searchSupportByName(name);
    }

    @PostMapping("/support")
    public void postSupport(@RequestBody Support support) {
        supportService.addSupport(support);
    }

    @DeleteMapping("/support/{isin}")
    public void deleteSupport(@PathVariable("isin") String isin) {
        supportService.removeSupport(isin);
    }
}
