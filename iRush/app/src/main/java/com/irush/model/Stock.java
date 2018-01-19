package com.irush.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 莴佑黔 on 2017/10/28.
 */

public class Stock implements Serializable{
    private String stockname;
    private String stockcode;
    private String stock_name;
    private String stock_code;
    private String price;
    private Double ddje;
    private String incre_percent;
    private String stock_zdf;
    private String stock_hsl;
    private Double stock_zxj;
    private Double stock_qqgd;
    private String stock_qqgdrq;


    public Stock(String sname, String code, String price, String incre_percent){
        this.stockname=sname;
        this.stockcode=code;
        this.price=price;
        this.incre_percent=incre_percent;
    }

    public String getStockname() {
        return stockname;
    }

    public void setStockname(String stockname) {
        this.stockname = stockname;
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Double getDdje() {
        return ddje;
    }

    public void setDdje(Double ddje) {
        this.ddje = ddje;
    }

    public String getIncre_percent() {
        return incre_percent;
    }

    public void setIncre_percent(String incre_percent) {
        this.incre_percent = incre_percent;
    }

    public String getStock_zdf() {
        return stock_zdf;
    }

    public void setStock_zdf(String stock_zdf) {
        this.stock_zdf = stock_zdf;
    }

    public String getStock_hsl() {
        return stock_hsl;
    }

    public void setStock_hsl(String stock_hsl) {
        this.stock_hsl = stock_hsl;
    }

    public Double getStock_zxj() {
        return stock_zxj;
    }

    public void setStock_zxj(Double stock_zxj) {
        this.stock_zxj = stock_zxj;
    }

    public Double getStock_qqgd() {
        return stock_qqgd;
    }

    public void setStock_qqgd(Double stock_qqgd) {
        this.stock_qqgd = stock_qqgd;
    }

    public String getStock_qqgdrq() {
        return stock_qqgdrq;
    }


    public void setStock_qqgdrq(String stock_qqgdrq) {
        this.stock_qqgdrq = stock_qqgdrq;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public String getStock_code() {
        return stock_code;
    }

    public void setStock_code(String stock_code) {
        this.stock_code = stock_code;
    }
}
