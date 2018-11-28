package com.leo.exercisepreconys.controllers;

import com.leo.exercisepreconys.TestUtils;
import com.leo.exercisepreconys.application.Application;
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
public class SupportControllerTest {

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
    }


    @Test
    public void getSupports() throws Exception {
        ResponseEntity<ArrayList> resp = template.getForEntity("http://localhost:8080/support", ArrayList.class);

        Assert.assertEquals(HttpStatus.OK, resp.getStatusCode());
        Assert.assertEquals(2, resp.getBody().size());
        Assert.assertEquals("support 0", ((Map) resp.getBody().get(0)).get("name"));
        Assert.assertEquals("support 1", ((Map) resp.getBody().get(1)).get("name"));
    }

    @Test
    public void searchSupportByIsin(){
        ResponseEntity<Optional> resp = template.getForEntity("http://localhost:8080/support/"+TestUtils.support0.getIsin(), Optional.class);

        Assert.assertEquals(HttpStatus.OK, resp.getStatusCode());
        Assert.assertTrue(resp.getBody().isPresent());
        Assert.assertEquals(TestUtils.support0.getIsin(), ((Map) resp.getBody().get()).get("isin"));
    }


    @Test
    public void searchSupportsByName(){
        ResponseEntity<ArrayList> resp = template.getForEntity("http://localhost:8080/support/name/"+TestUtils.support0.getName(), ArrayList.class);

        Assert.assertEquals(HttpStatus.OK, resp.getStatusCode());
        Assert.assertEquals(1, resp.getBody().size());
        Assert.assertEquals(TestUtils.support0.getName(), ((Map) resp.getBody().get(0)).get("name"));
    }


    @Test
    public void postSupport(){
        ResponseEntity resp = template.postForEntity("http://localhost:8080/support", TestUtils.support2, void.class);

        Assert.assertEquals(HttpStatus.OK, resp.getStatusCode());
        Assert.assertTrue(memoryDTO.getSupportFromIsin(TestUtils.support2.getIsin()).isPresent());
    }


    @Test
    public void deleteSupport(){
        Assert.assertTrue(memoryDTO.getSupportFromIsin(TestUtils.support1.getIsin()).isPresent());

        template.delete("http://localhost:8080/support/" + TestUtils.support1.getIsin());

        Assert.assertFalse(memoryDTO.getSupportFromIsin(TestUtils.support1.getIsin()).isPresent());
    }

}