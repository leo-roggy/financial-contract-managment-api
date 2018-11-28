package com.leo.exercisepreconys.services;

import com.google.common.collect.ImmutableMap;
import com.leo.exercisepreconys.business.Contract;
import com.leo.exercisepreconys.business.Support;
import com.leo.exercisepreconys.persistence.DTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class ContractService {

    private DTO persistence;

    @Autowired
    public ContractService(DTO persistence) {
        this.persistence = persistence;
    }

    public void addContract(@NotNull Contract c) {
        log.debug("add Contract : " + c);

        if (c == null)
            throw new IllegalArgumentException("Contract must not be empty");
        else if (StringUtils.isEmpty(c.getName()))
            throw new IllegalArgumentException("Contract name must not me empty");
        else if (StringUtils.isEmpty(c.getEstablishment()))
            throw new IllegalArgumentException("Contract establishment must not me empty");
        else if (c.getCurrency() == null)
            throw new IllegalArgumentException("Contract currency not correct");
        else if (c.getAmount() == null)
            throw new IllegalArgumentException("Contract amount must not me empty");
        else if (c.getAmount() < 0f)
            throw new IllegalArgumentException("Contract amount must be positive");
        else if (c.getOpeningDate() == null)
            throw new IllegalArgumentException("Contract opening date must not me empty");
        else if (StringUtils.isEmpty(c.getClientMail()))
            throw new IllegalArgumentException("Contract client mail must not me empty");

        // If support parts have been set, remove incorrect isin and incorrect parts
        if (c.getSupportParts() != null && !c.getSupportParts().isEmpty()) {
            ImmutableMap.copyOf(c.getSupportParts()).forEach((isin, part) -> {
                if (!persistence.getSupportFromIsin(isin).isPresent()) {
                    c.getSupportParts().remove(isin);
                    log.info("Unknown ISIN {}, remove it", isin);
                } else if (part < 0f || part > 1f) {
                    c.getSupportParts().remove(isin);
                    log.info("incorrect part {}, remove it", part);
                }
            });
        } else {
            c.setSupportParts(new HashMap<>());
        }

        // If contract with this name already exist, remove it
        persistence.getContractFromName(c.getName())
                .ifPresent(oldContract -> persistence.removeContract(oldContract));

        persistence.addContract(c);
    }

    public List<Contract> getAllContracts() {
        log.debug("get all contracts");
        return persistence.getAllContracts();
    }

    public Optional<Contract> searchContractByName(String name) {
        log.debug("Search Contract by name : {}", name);

        if (StringUtils.isEmpty(name))
            throw new IllegalArgumentException("Contract name must not be empty");

        return persistence.getContractFromName(name);
    }

    public List<Contract> searchContractsByIsin(String isin) {
        log.debug("Search Contract by ISIN : {}", isin);

        if (StringUtils.isEmpty(isin))
            throw new IllegalArgumentException("ISIN must not be empty");

        return persistence.getContractsFromIsin(isin);
    }

    public void removeContract(String name) {
        log.debug("remove Contract : {}", name);

        if (StringUtils.isEmpty(name))
            throw new IllegalArgumentException("Contract name must not be empty");

        Optional<Contract> contractFromName = persistence.getContractFromName(name);

        if (contractFromName.isPresent()) {
            persistence.removeContract(contractFromName.get());
            log.debug("Contract removed");
        } else {
            log.info("There is no contract with name {}", name);
        }
    }


    public void createAssociation(String contractName, String isin, String part) {
        log.debug("create association between contract name {} and support ISIN {} with part {}", contractName, isin, part);

        if (StringUtils.isEmpty(contractName))
            throw new IllegalArgumentException("Contract name must not be empty");
        if (StringUtils.isEmpty(isin))
            throw new IllegalArgumentException("Support ISIN must not be empty");
        if (StringUtils.isEmpty(part))
            throw new IllegalArgumentException("part must not be empty");

        Float fPart = Float.valueOf(part);
        if (fPart < 0f || fPart > 1f)
            throw new IllegalArgumentException("part must comprised between 0 and 1");

        Optional<Contract> contractFromName = persistence.getContractFromName(contractName);
        Optional<Support> supportFromIsin = persistence.getSupportFromIsin(isin);

        if (!contractFromName.isPresent()) {
            log.info("There is no contract with name {}", contractName);
        } else if (!supportFromIsin.isPresent()) {
            log.info("There is no Support with Isin {}", isin);
        } else {
            HashMap<String, Float> map = new HashMap<>(contractFromName.get().getSupportParts());
            map.put(isin, fPart);
            contractFromName.get().setSupportParts(map);
            log.debug("contract and support associated");
        }
    }

    public void removeAssociation(String contractName, String isin) {
        log.debug("remove association between contract name {} and support ISIN {}", contractName, isin);

        if (StringUtils.isEmpty(contractName))
            throw new IllegalArgumentException("Contract name must not be empty");
        if (StringUtils.isEmpty(isin))
            throw new IllegalArgumentException("Support ISIN must not be empty");

        Optional<Contract> contractFromName = persistence.getContractFromName(contractName);
        if (!contractFromName.isPresent()) {
            log.info("There is no contract with name {}", contractName);
        } else {
            if (contractFromName.get().getSupportParts().containsKey(isin)) {

                HashMap<String, Float> map = new HashMap<>(contractFromName.get().getSupportParts());
                map.remove(isin);
                contractFromName.get().setSupportParts(map);
                log.debug("association removed");
            } else {
                log.info("contract doesn't use Support {}", isin);
            }
        }
    }
}
