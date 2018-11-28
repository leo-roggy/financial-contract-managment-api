package com.leo.exercisepreconys.persistence;

import com.leo.exercisepreconys.TestUtils;
import com.leo.exercisepreconys.business.Support;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class MemoryDTOTest {

    private MemoryDTO memoryDTO;

    @Before
    public void setUp() {
        memoryDTO = new MemoryDTO();
    }

    @Test
    public void addRemoveGetSupports() {
        Assert.assertEquals(0, memoryDTO.getAllSupports().size());

        memoryDTO.addSupport(TestUtils.support0);
        Assert.assertEquals(1, memoryDTO.getAllSupports().size());
        Assert.assertEquals("support 0", memoryDTO.getAllSupports().get(0).getName());

        memoryDTO.removeSupport(TestUtils.support0);
        Assert.assertEquals(0, memoryDTO.getAllSupports().size());
    }

    @Test
    public void addRemoveGetContracts() {
        Assert.assertEquals(0, memoryDTO.getAllContracts().size());

        memoryDTO.addContract(TestUtils.contract0);
        Assert.assertEquals(1, memoryDTO.getAllContracts().size());
        Assert.assertEquals("contract 0", memoryDTO.getAllContracts().get(0).getName());

        memoryDTO.removeContract(TestUtils.contract0);
        Assert.assertEquals(0, memoryDTO.getAllContracts().size());
    }

    @Test
    public void searchSupportByIsin() {
        memoryDTO.addSupport(TestUtils.support0);
        memoryDTO.addSupport(TestUtils.support1);
        memoryDTO.addSupport(TestUtils.support1prime);

        Optional<Support> supportFromIsin = memoryDTO.getSupportFromIsin(TestUtils.support1.getIsin());

        Assert.assertTrue(supportFromIsin.isPresent());
        Assert.assertEquals(TestUtils.support1, supportFromIsin.get());
    }

    @Test
    public void searchSupportByName() {
        memoryDTO.addSupport(TestUtils.support0);
        memoryDTO.addSupport(TestUtils.support1);
        memoryDTO.addSupport(TestUtils.support1prime);

        List<Support> supportsFromName = memoryDTO.getSupportsFromName(TestUtils.support1.getName());

        Assert.assertEquals(2, supportsFromName.size());
    }
}