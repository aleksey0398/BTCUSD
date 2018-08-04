package com.bitfenix.btcusd;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResponseObjectTest {

    @Test
    public void createObjectTest(){
        String response = "[4370,7392.2,16.00032426,7392.3,32.66262378,42.459875,0.0058,7392.259875,19067.85454706,7531.4,7330.7]";
        ResponseObject responseObject = new ResponseObject(response);
        assertEquals(4370, responseObject.getChannelId());
        assertEquals(7392.2f, responseObject.getBid(), 0.001);
        assertEquals(16.00032426f, responseObject.getBidSize(), 0.001);
        assertEquals(7392.3f, responseObject.getAsk(), 0.001);
        assertEquals(32.66262378f, responseObject.getAskSize(), 0.001);
        assertEquals(42.459875f, responseObject.getDailyChange(), 0.001);
        assertEquals(0.0058f, responseObject.getDailyChangePerc(), 0.001);
        assertEquals(7392.259875f, responseObject.getLastPrice(), 0.001);
        assertEquals(19067.85454706f, responseObject.getVolume(), 0.001);
        assertEquals(7531.4f, responseObject.getHigh(), 0.001);
        assertEquals(7330.7f, responseObject.getLow(), 0.001);
    }
}
