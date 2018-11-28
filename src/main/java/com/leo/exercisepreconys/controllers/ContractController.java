package com.leo.exercisepreconys.controllers;

import com.leo.exercisepreconys.business.Contract;
import com.leo.exercisepreconys.business.Support;
import com.leo.exercisepreconys.services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ContractController {
    private ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }


    @GetMapping("/contract")
    public List<Contract> getContracts() {
        return contractService.getAllContracts();
    }

    @GetMapping("/contract/{name}")
    public Object searchContractByName(@PathVariable("name") String name) {
        Optional<Contract> contract = contractService.searchContractByName(name);

        if(contract.isPresent())
            return contract.get();
        else
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/contract/isin/{isin}")
    public List<Contract> searchContractsByIsin(@PathVariable("isin") String isin) {
        return contractService.searchContractsByIsin(isin);
    }

    @PostMapping(value = "/contract", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postContract(@RequestBody Contract contract) {
        contractService.addContract(contract);
    }

    @DeleteMapping("/contract/{name}")
    public void deleteContract(@PathVariable("name") String name) {
        contractService.removeContract(name);
    }

    @PostMapping("/association/{contractName}/{isin}/{part}")
    public void postAssociation(
            @PathVariable("contractName") String contractName,
            @PathVariable("isin") String isin,
            @PathVariable("part") String part) {
        contractService.createAssociation(contractName, isin, part);
    }

    @DeleteMapping("/association/{contractName}/{isin}")
    public void removeAssociation(
            @PathVariable("contractName") String contractName,
            @PathVariable("isin") String isin) {
        contractService.removeAssociation(contractName, isin);
    }
}
