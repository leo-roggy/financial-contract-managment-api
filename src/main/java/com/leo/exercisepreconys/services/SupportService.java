package com.leo.exercisepreconys.services;

import com.leo.exercisepreconys.business.Support;
import com.leo.exercisepreconys.persistence.DTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class SupportService {

    private DTO persistence;

    @Autowired
    public SupportService(DTO persistence) {
        this.persistence = persistence;
    }

    public void addSupport(Support s) {
        log.debug("add Support : " + s);

        if (s == null)
            throw new IllegalArgumentException("Support must not be empty");
        else if (s.getName() == null)
            throw new IllegalArgumentException("Support name must not me empty");
        else if (s.getIsin() == null)
            throw new IllegalArgumentException("Support ISIN must not me empty");
        else if (s.getEstablishment() == null)
            throw new IllegalArgumentException("Support establishment must not me empty");
        else if (s.getValue() == null)
            throw new IllegalArgumentException("Support value must not me empty");
        else if (s.getCreationDate() == null)
            throw new IllegalArgumentException("Support creation must not me empty");

        // If support with this Isin already exist, remove it
        persistence.getSupportFromIsin(s.getIsin())
                .ifPresent(oldSupport -> persistence.removeSupport(oldSupport));

        persistence.addSupport(s);
    }

    public void removeSupport(String isin) {
        log.debug("remove Support : " + isin);

        if (StringUtils.isEmpty(isin))
            throw new IllegalArgumentException("Support ISIN must not be empty");

        Optional<Support> supportFromIsin = persistence.getSupportFromIsin(isin);

        if (supportFromIsin.isPresent()) {
            persistence.removeSupport(supportFromIsin.get());
            log.debug("Support removed");
        } else {
            log.info("There is no Support with ISIN {}", isin);
        }
    }

    public List<Support> getAllSupports() {
        log.debug("get all supports");
        return persistence.getAllSupports();
    }

    public Optional<Support> searchSupportByIsin(String isin) {
        log.debug("Search Support by Isin : " + isin);

        if (StringUtils.isEmpty(isin))
            throw new IllegalArgumentException("Support ISIN must not be empty");

        return persistence.getSupportFromIsin(isin);
    }

    public List<Support> searchSupportByName(String name) {
        log.debug("Search Support by Name : " + name);

        if (StringUtils.isEmpty(name))
            throw new IllegalArgumentException("Support name must not be empty");

        return persistence.getSupportsFromName(name);
    }
}
