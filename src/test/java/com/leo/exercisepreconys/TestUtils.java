package com.leo.exercisepreconys;

import com.google.common.collect.ImmutableMap;
import com.leo.exercisepreconys.business.Contract;
import com.leo.exercisepreconys.business.Currency;
import com.leo.exercisepreconys.business.Support;

import java.util.Date;

public class TestUtils {

    public static final Support support0;
    public static final Support support1;
    public static final Support support1prime;
    public static final Support support2;

    public static final Contract contract0;
    public static final Contract contract1;

    public static final Date currentDate = new Date(System.currentTimeMillis());

    static{
        support0 = Support.builder()
                .name("support 0")
                .isin("isin0")
                .establishment("establishment 0")
                .value(10000f)
                .creationDate(currentDate)
                .build();

        support1 = Support.builder()
                .name("support 1")
                .isin("isin1")
                .establishment("establishment 1")
                .value(20000f)
                .creationDate(currentDate)
                .build();

        support1prime = Support.builder()
                .name("support 1")
                .isin("insi1prime")
                .establishment("establishment 1")
                .value(20000f)
                .creationDate(currentDate)
                .build();

        support2 = Support.builder()
                .name("support 2")
                .isin("isin2")
                .establishment("establishment 2")
                .value(80000f)
                .creationDate(currentDate)
                .build();


        contract0 = Contract.builder()
                .name("contract 0")
                .establishment("establishment 0")
                .currency(Currency.EURO)
                .amount(1000f)
                .openingDate(currentDate)
                .clientMail("client 0")
                .supportParts(ImmutableMap.of(
                        support0.getIsin(), 0.1f,
                        support1.getIsin(), 0.2f
                ))
                .build();

        contract1 = Contract.builder()
                .name("contract 1")
                .establishment("establishment 1")
                .currency(Currency.DOLLAR)
                .amount(2000f)
                .openingDate(currentDate)
                .clientMail("client 1")
                .supportParts(ImmutableMap.of(
                        support0.getIsin(), 0.5f
                ))
                .build();
    }


    private TestUtils(){}
}
