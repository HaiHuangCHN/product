package com.product.model.reqeust;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.product.model.request.AddProductItemReq;

public class TestAddProductItemReq {

    /**
     * Before override Object.equals(), the following would return false even if
     * have same value
     */
    @Test
    public void beforeOverrideEqualWouldReturnFalse() {
        AddProductItemReq object1 = new AddProductItemReq();
        object1.setCatalogueId(1L);
        object1.setChannel("CHANNEL_1");
        AddProductItemReq object2 = new AddProductItemReq();
        object2.setCatalogueId(1L);
        object2.setChannel("CHANNEL_1");
        assertFalse(object1.equals(object2));
    }

    /**
     * Before override Object.hashCode(), equal object would return different hash
     * code
     */
    @Test
    public void differentHashCodeReturnedOfTwoObjectsWithSameValue() {
        AddProductItemReq object1 = new AddProductItemReq();
        object1.setCatalogueId(1L);
        object1.setChannel("CHANNEL_1");
        AddProductItemReq object2 = new AddProductItemReq();
        object2.setCatalogueId(1L);
        object2.setChannel("CHANNEL_1");
        System.out.println(object1.hashCode());
        System.out.println(object2.hashCode());
        assertTrue(object1.hashCode() != object2.hashCode());
    }

    /**
     * After override Object.equals(), the following would return true
     */
    @Test
    public void afterOverrideEqualWouldReturnFalse() {
        AddProductItemReq object1 = new AddProductItemReq();
        object1.setCatalogueId(1L);
        object1.setChannel("CHANNEL_1");
        AddProductItemReq object2 = new AddProductItemReq();
        object2.setCatalogueId(1L);
        object2.setChannel("CHANNEL_1");
        assertTrue(object1.equals(object2));
    }

    /**
     * After override Object.hashCode(), equal object would return same hash code
     */
    @Test
    public void sameHashCodeReturnedOfTwoObjectsWithSameValue() {
        AddProductItemReq object1 = new AddProductItemReq();
        object1.setCatalogueId(1L);
        object1.setChannel("CHANNEL_1");
        AddProductItemReq object2 = new AddProductItemReq();
        object2.setCatalogueId(1L);
        object2.setChannel("CHANNEL_1");

//        System.out.println(object1.hashCode());
//        System.out.println(object2.hashCode());
        assertFalse(object1.hashCode() != object2.hashCode());
    }
}
