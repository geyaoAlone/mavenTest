package com.yxf.pojo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CainiaoRes1 implements Serializable {
    private String isReady;
    private String tradeTotalNum;
    private String tradeTotalAmount;
    private String currency;
    private List<String> tradeAccountList;
    private String success;
    private String errorCode;
    private String errorMsg;

    private String date;

    public static void main(String[] args) {
        List<String> list =  new ArrayList<String>();
        list.add("cnagent01");
        list.add("cnagent02");
        list.add("cnagent03");
        CainiaoRes1 c = new CainiaoRes1();
               c.setIsReady("true")
                .setCurrency("CNY")
                .setSuccess("true")
                .setTradeTotalNum("3")
                .setTradeTotalAmount("300000000")
                .setTradeAccountList(list)
                .setErrorCode("")
                .setErrorMsg("")
                .setDate("20180502");

        System.out.println(JSON.toJSONString(c));
    }

}
