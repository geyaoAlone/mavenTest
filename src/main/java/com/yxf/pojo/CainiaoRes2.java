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
public class CainiaoRes2 implements Serializable {
    private String success;
    private String errorCode;
    private String errorMsg;
    private String totalSize;
    private List<CainiaoResDetail> dataList;


    private String date;
    private String userId;

    public static void main(String[] args) {

        CainiaoRes2 c = new CainiaoRes2();
                c.setUserId("cnagent01")
                .setSuccess("true")
                .setErrorCode("")
                .setErrorMsg("")
                .setDate("20180502")
                .setTotalSize("10")
                ;

        System.out.println(JSON.toJSONString(c));
    }

}
