package com.leo.exercisepreconys.persistence;

import com.leo.exercisepreconys.business.Contract;
import com.leo.exercisepreconys.business.Support;

import java.util.List;
import java.util.Optional;

public interface DTO {

    List<Contract> getAllContracts();

    void addContract(Contract c);

    void removeContract(Contract c);

    Optional<Contract> getContractFromName(String name);

    List<Contract> getContractsFromIsin(String isin);

    List<Support> getAllSupports();

    void addSupport(Support s);

    void removeSupport(Support s);

    Optional<Support> getSupportFromIsin(String isin);

    List<Support> getSupportsFromName(String name);

    void clear();
}
