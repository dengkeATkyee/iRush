package com.irush.dto;

import com.irush.model.Stock;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 莴佑黔 on 2017/10/28.
 */

public class TopFiveDto implements Serializable {
       private String retcode;
       private Result result;
       private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result implements Serializable{
          private List<Stock> asc;
          private List<Stock> desc;

          public List<Stock> getAsc() {
              return asc;
          }

          public void setAsc(List<Stock> asc) {
              this.asc = asc;
          }

          public List<Stock> getDesc() {
              return desc;
          }

          public void setDesc(List<Stock> desc) {
              this.desc = desc;
          }
      }
}
