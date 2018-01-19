package com.irush.netutil;

import com.irush.dto.NewRecordDto;
import com.irush.model.Stock;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 莴佑黔 on 2017/11/2.
 */

public class StringUtil {

    public static boolean isEmpty(String str){
        if(str==null||str==""){
            return true;
        }
      return false;
    }
    public static String mapToString(Map map){
        StringBuffer stringBuffer=new StringBuffer();
        for(Object key:map.keySet()){
            stringBuffer.append("&"+key);
            stringBuffer.append("="+map.get(key));
        }
        String str=stringBuffer.toString();
        return str.substring(1);
    }
    public static String[] getMatchedArray(String str){
        if(StringUtil.isEmpty(str)) return null;
        Pattern pattern=Pattern.compile("\".*\"");
        Matcher matcher=pattern.matcher(str);
        if(matcher.find()){
            String group=matcher.group(0);
            String[] validData= Arrays.copyOfRange(group.split(","),1,4);
            for(int i=0;i<validData.length;i++){
                validData[i]=doAccuracy(validData[i]);
            }
            return validData;
        }
        return null;
    }
    public static String doAccuracy(String value){
        BigDecimal b=new BigDecimal(Double.parseDouble(value));
        double f1=b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(f1);
    }
//    public static Double doAccuracy(Double value){
//        if(nOfdecimals(value)==1){
//            String s=String.valueOf(value)+"0";
//            return Double.parseDouble(s);
//        }
//        BigDecimal b=new BigDecimal(value);
//        return b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
//    }
//    public static List<Stock> doAccuracyForPrice(NewRecordDto newRecordDto){
//         List<Stock> stocks=newRecordDto.getResult();
//         for(Stock s:stocks){
//             s.setStock_zxj(doAccuracy(s.getStock_zxj()));
//         }
//        return stocks;
//    }
    public static int nOfdecimals(Double a){
        String s=String.valueOf(a);
        return s.length() - (s.indexOf(".") + 1);
    }
}
