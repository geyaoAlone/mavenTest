package com.yxf.pojo;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CainiaoResDetail implements Serializable {
    private String bankAccountNo;
    private String tradeAmount;
    private String currency;
    private String tradeTime;
    private String tradeSeqno;
    private String handleType;

    public static void main(String[] args) {

        CainiaoResDetail c = new CainiaoResDetail();
        c.setBankAccountNo("123123123")
        .setTradeAmount("1000000")
        .setCurrency("CNY")
        .setHandleType("1")
        .setTradeSeqno("12345678")
        .setTradeTime("20180502112345")
        ;

        System.out.println(JSON.toJSONString(c));
    }


}
