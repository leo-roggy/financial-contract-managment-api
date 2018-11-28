package com.leo.exercisepreconys.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contract {

    private String name;
    private String establishment;
    private Currency currency;
    private Float amount;
    private Date openingDate;
    private String clientMail;
    private Map<String, Float> supportParts;


}
