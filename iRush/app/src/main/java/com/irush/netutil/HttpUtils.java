package com.irush.netutil;


import android.content.Context;
import android.widget.Toast;
import com.google.gson.Gson;
import com.irush.dto.TopFiveDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


/**
 * Created by 莴佑黔 on 2017/10/28.
 */

public class HttpUtils {
    private Context context;
    private Gson gson;
    private StringBuffer paramstr=new StringBuffer("?");
    private StringBuffer absoluteUrl=new StringBuffer();
    public HttpUtils(Context context){
        this.context=context;
    }
    private HttpURLConnection initUrlConnection(String endpoint, String method,String params) throws Exception{
         URL url=new URL(absoluteUrl.append(endpoint).append(method).append(params).toString());
         HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
         httpURLConnection.setConnectTimeout(3000);
         httpURLConnection.setUseCaches(false);
         httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
         httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setUseCaches(false);
        return httpURLConnection;
    }
    public synchronized Object doGet(Class cla,String endpoint, String method, Map<String,Object> params)throws Exception{
              Object dto=null;
            HttpURLConnection httpURLConnection=null;
            InputStreamReader inputStreamReader=null;
              if(params!=null){paramstr.append(StringUtil.mapToString(params));}
              try {
                  httpURLConnection=initUrlConnection(endpoint,method,paramstr.toString());
                  inputStreamReader=new InputStreamReader(httpURLConnection.getInputStream());
                  BufferedReader buffer = new BufferedReader(inputStreamReader);
                  String inputLine = null;
                  StringBuffer result=new StringBuffer("");
                  if(httpURLConnection.getResponseCode()==200){
                      while ((inputLine=buffer.readLine())!=null)
                      {result.append(inputLine);}
                      gson=new Gson();
                      dto = gson.fromJson(result.toString(), cla);
                  }
              } catch (Exception e) {
                   Object error=cla.newInstance();
                   cla.getMethod("setError",String.class).invoke(error,e.getMessage());
                  return error;
              }finally {
                  inputStreamReader.close();
                  httpURLConnection.disconnect();
                  absoluteUrl.delete(0,absoluteUrl.toString().length());
                  paramstr.delete(1,paramstr.toString().length());
              }
              return dto;
          }
         public synchronized  String doGet(String endpoint, String method, String params) throws Exception{
             StringBuffer result=new StringBuffer("");
             HttpURLConnection httpURLConnection=null;
             InputStreamReader inputStreamReader=null;
             try {
                 httpURLConnection=initUrlConnection(endpoint,method,params);
                 inputStreamReader=new InputStreamReader(httpURLConnection.getInputStream());
                 BufferedReader buffer = new BufferedReader(inputStreamReader);
                 String inputLine = null;
                 if(httpURLConnection.getResponseCode()==200){
                     while ((inputLine=buffer.readLine())!=null)
                     result.append(inputLine);
                 }
             }catch (Exception e){
                   e.printStackTrace();
             }finally {
                 inputStreamReader.close();
                 httpURLConnection.disconnect();
                 absoluteUrl.delete(0,absoluteUrl.toString().length());
             }
            return result.toString();
         }
    
}
