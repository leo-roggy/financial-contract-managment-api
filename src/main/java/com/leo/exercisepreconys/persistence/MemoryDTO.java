package com.leo.exercisepreconys.persistence;

import com.google.common.collect.ImmutableList;
import com.leo.exercisepreconys.business.Contract;
import com.leo.exercisepreconys.business.Support;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@ToString
public class MemoryDTO implements DTO {

    private List<Contract> contractList;
    private List<Support> supportList;


    private Support support0;
    private Support support1;

    private Contract contract0;
    private Contract contract1;

    private Date currentDate = new Date(System.currentTimeMillis());

    public MemoryDTO() {
        contractList = new ArrayList();
        supportList = new ArrayList();

    }

    @Override
    public List<Contract> getAllContracts() {
        return ImmutableList.copyOf(contractList);
    }

    @Override
    public void addContract(Contract c) {
        contractList.add(c);
    }

    @Override
    public void removeContract(Contract c) {
        contractList.remove(c);
    }

    @Override
    public Optional<Contract> getContractFromName(String name) {
        return contractList.stream()
                .filter(contract -> name.equals(contract.getName()))
                .findFirst();
    }

    @Override
    public List<Contract> getContractsFromIsin(String isin) {

        return contractList.stream()
                .filter(contract -> contract.getSupportParts().containsKey(isin))
                .collect(Collectors.toList());
    }

    @Override
    public List<Support> getAllSupports() {
        return ImmutableList.copyOf(supportList);
    }


    @Override
    public void addSupport(Support s) {
        supportList.add(s);
    }

    @Override
    public void removeSupport(Support s) {
        supportList.remove(s);
    }

    @Override
    public Optional<Support> getSupportFromIsin(String isin) {
        return supportList.stream()
                .filter(support -> isin.equals(support.getIsin()))
                .findFirst();
    }

    @Override
    public List<Support> getSupportsFromName(String name) {
        return supportList.stream()
                .filter(support -> name.equals(support.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        contractList.clear();
        supportList.clear();
    }


}
