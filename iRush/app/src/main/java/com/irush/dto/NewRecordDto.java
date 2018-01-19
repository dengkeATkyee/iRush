package com.irush.dto;

import com.irush.model.Stock;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 莴佑黔 on 2017/11/2.
 */

public class NewRecordDto implements Serializable{
    private String retcode;
    private List<Stock> result;
    private String error;

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public List<Stock> getResult() {
        return result;
    }

    public void setResult(List<Stock> result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
