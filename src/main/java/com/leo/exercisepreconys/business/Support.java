package com.leo.exercisepreconys.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Support {

    private String name;
    private String isin;
    private String establishment;
    private Float value;
    private Date creationDate;

}
