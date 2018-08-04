package com.bitfenix.btcusd;

import android.support.annotation.NonNull;

/**
 * @author Aleksey Mitkin
 * Класс для создания обекта с результатами от WebSocket
 * пример входных данных: [ 2, 236.62, 9.0029, 236.88, 7.1138, -1.02, 0, 236.52, 5191.36754297, 250.01, 220.05 ]
 * https://docs.bitfinex.com/v1/reference#ws-public-ticker
 */

public class ResponseObject {

    private int channelId;          // Channel ID
    private float bid;              // Price of last highest bid
    private float bidSize;          // Size of the last highest bid
    private float ask;              // Price of last lowest ask
    private float askSize;          // Size of the last lowest ask
    private float dailyChange;      // Amount that the last price has changed since yesterday
    private float dailyChangePerc;  // Amount that the price has changed expressed in percentage terms
    private float lastPrice;        // Price of the last trade.
    private float volume;           // Daily volume
    private float high;             // Daily high
    private float low;              // Daily low

    public ResponseObject(@NonNull String result) {

        String clearResult = result.substring(1, result.length() - 1);
        String[] results = clearResult.split(",");

        this.channelId = Integer.parseInt(results[0]);
        this.bid = Float.parseFloat(results[1]);
        this.bidSize = Float.parseFloat(results[2]);
        this.ask = Float.parseFloat(results[3]);
        this.askSize = Float.parseFloat(results[4]);
        this.dailyChange = Float.parseFloat(results[5]);
        this.dailyChangePerc = Float.parseFloat(results[6]);
        this.lastPrice = Float.parseFloat(results[7]);
        this.volume = Float.parseFloat(results[8]);
        this.high = Float.parseFloat(results[9]);
        this.low = Float.parseFloat(results[10]);

    }

    /**
     * Делаем проверку сможем ли мы из набора полученных данных собрать объект
     *
     * @return true, если смогли создать и false, если на каком-то из этапов произошла ошибка
     */
    public static boolean canCreateObject(String response) {
        try {
            new ResponseObject(response);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public float getBid() {
        return bid;
    }

    public void setBid(float bid) {
        this.bid = bid;
    }

    public float getBidSize() {
        return bidSize;
    }

    public void setBidSize(float bidSize) {
        this.bidSize = bidSize;
    }

    public float getAsk() {
        return ask;
    }

    public void setAsk(float ask) {
        this.ask = ask;
    }

    public float getAskSize() {
        return askSize;
    }

    public void setAskSize(float askSize) {
        this.askSize = askSize;
    }

    public float getDailyChange() {
        return dailyChange;
    }

    public void setDailyChange(float dailyChange) {
        this.dailyChange = dailyChange;
    }

    public float getDailyChangePerc() {
        return dailyChangePerc;
    }

    public void setDailyChangePerc(float dailyChagePerc) {
        this.dailyChangePerc = dailyChagePerc;
    }

    public float getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(float lastPrice) {
        this.lastPrice = lastPrice;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getHigh() {
        return high;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public float getLow() {
        return low;
    }

    public void setLow(float low) {
        this.low = low;
    }

    @Override
    public String toString() {
        return "ResponseObject{" +
                "channelId=" + channelId +
                ", bid=" + bid +
                ", bidSize=" + bidSize +
                ", ask=" + ask +
                ", askSize=" + askSize +
                ", dailyChange=" + dailyChange +
                ", dailyChangePerc=" + dailyChangePerc +
                ", lastPrice=" + lastPrice +
                ", volume=" + volume +
                ", high=" + high +
                ", low=" + low +
                '}';
    }
}
