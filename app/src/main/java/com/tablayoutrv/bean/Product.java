package com.tablayoutrv.bean;

import java.math.BigDecimal;

/**
 * Created by HARRY on 2018/4/24 0024.
 */

public class Product implements Cloneable{
    /**
     * id : 629475
     * itemNo : IP8P8872B
     * logo : IP8P8872B.jpg
     * mPrice : 16.3700
     * title : ROCK W3 DC 5V 2A / 9V 1.67A 10W Max Output Qi Standard Dual Coil Wireless Charger Holder, For iPhone X & 8 & 8 Plus, Galaxy S8 & S8+,  LG G3 & G2 & G10, Nokia Lumia 820 & Lumia 830 / Lumia 920 & Lumia 930, Google Nexus 6 & 5 & 4 and Other QI Standard Smartphones(Black)
     */

    private int id;
    private String itemNo;
    private String logo;
    private BigDecimal price;
    private BigDecimal exPrice;
    private String title;
    private BigDecimal appPrice;
    private BigDecimal exAppPrice;

    public void setExPrice(BigDecimal exPrice) {
        this.exPrice = exPrice;
    }

    public BigDecimal getExPrice() {
        return exPrice;
    }

    public void setAppPrice(BigDecimal appPrice) {
        this.appPrice = appPrice;
    }

    public void setExAppPrice(BigDecimal exAppPrice) {
        this.exAppPrice = exAppPrice;
    }

    public BigDecimal getAppPrice() {
        return appPrice;
    }

    public BigDecimal getExAppPrice() {
        return exAppPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
