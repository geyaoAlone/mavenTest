package com.yxf.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CainiaoReq2 {
    private String requestId;
    private String sysCode;

    private String date;
    private String userId;
    private String pageNum;
    private String pageSize;






}
