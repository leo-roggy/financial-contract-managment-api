package com.leo.exercisepreconys.controllers;

import com.leo.exercisepreconys.TestUtils;
import com.leo.exercisepreconys.application.Application;
import com.leo.exercisepreconys.business.Contract;
import com.leo.exercisepreconys.persistence.MemoryDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ContractControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private MemoryDTO memoryDTO;

    @Before
    public void setUp() {
        memoryDTO.clear();

        memoryDTO.addContract(TestUtils.contract0);

        memoryDTO.addSupport(TestUtils.support0);
        memoryDTO.addSupport(TestUtils.support1);
        memoryDTO.addSupport(TestUtils.support2);
    }

    @Test
    public void getContracts(){
        ResponseEntity<ArrayList> resp = template.getForEntity("http://localhost:8080/contract", ArrayList.class);

        Assert.assertEquals(HttpStatus.OK, resp.getStatusCode());
        Assert.assertEquals(1, resp.getBody().size());
        Assert.assertEquals(TestUtils.contract0.getName(), ((Map) resp.getBody().get(0)).get("name"));
    }

    @Test
    public void searchContractByName(){
        ResponseEntity<Optional> resp = template.getForEntity("http://localhost:8080/contract/" + TestUtils.contract0.getName(), Optional.class);

        Assert.assertEquals(HttpStatus.OK, resp.getStatusCode());
        Assert.assertTrue(resp.getBody().isPresent());
        Assert.assertEquals(TestUtils.contract0.getName(), ((Map) resp.getBody().get()).get("name"));

        resp = template.getForEntity("http://localhost:8080/contract/" + TestUtils.contract1.getName(), Optional.class);

        Assert.assertEquals(HttpStatus.OK, resp.getStatusCode());
        Assert.assertFalse(resp.getBody().isPresent());
    }


    @Test
    public void searchContractByIsin(){
        ResponseEntity<ArrayList> resp = template.getForEntity("http://localhost:8080/contract/isin/" + TestUtils.support0.getIsin(), ArrayList.class);

        Assert.assertEquals(HttpStatus.OK, resp.getStatusCode());
        Assert.assertEquals(1, resp.getBody().size());
        Assert.assertEquals(TestUtils.contract0.getName(), ((Map) resp.getBody().get(0)).get("name"));

        resp = template.getForEntity("http://localhost:8080/contract/isin/qsdf", ArrayList.class);

        Assert.assertEquals(HttpStatus.OK, resp.getStatusCode());
        Assert.assertEquals(0, resp.getBody().size());
    }

    @Test
    public void postContract(){
        ResponseEntity resp = template.postForEntity("http://localhost:8080/contract", TestUtils.contract1, void.class);

        Assert.assertEquals(HttpStatus.OK, resp.getStatusCode());
        Assert.assertTrue(memoryDTO.getContractFromName(TestUtils.contract1.getName()).isPresent());
    }

    @Test
    public void deleteContract(){
        Assert.assertTrue(memoryDTO.getContractFromName(TestUtils.contract0.getName()).isPresent());

        template.delete("http://localhost:8080/contract/" + TestUtils.contract0.getName());

        Assert.assertFalse(memoryDTO.getContractFromName(TestUtils.contract0.getName()).isPresent());
    }

    @Test
    public void postAndDeleteAssociation(){
        Assert.assertEquals(0, memoryDTO.getContractsFromIsin(TestUtils.support2.getIsin()).size());

        ResponseEntity resp = template.postForEntity(
                String.format("http://localhost:8080/association/%s/%s/%s",
                        TestUtils.contract0.getName(),
                        TestUtils.support2.getIsin(),
                        "0.125"),
                null, void.class);

        Assert.assertEquals(HttpStatus.OK, resp.getStatusCode());

        Assert.assertEquals(1, memoryDTO.getContractsFromIsin(TestUtils.support2.getIsin()).size());

        Contract contract = memoryDTO.getContractsFromIsin(TestUtils.support2.getIsin()).get(0);
        Assert.assertEquals(TestUtils.contract0.getName(), contract.getName());
        Assert.assertEquals(new Float(0.125f), contract.getSupportParts().get(TestUtils.support2.getIsin()));

        template.delete(String.format("http://localhost:8080/association/%s/%s",
                TestUtils.contract0.getName(),
                TestUtils.support2.getIsin()));

        Assert.assertEquals(0, memoryDTO.getContractsFromIsin(TestUtils.support2.getIsin()).size());
    }

}