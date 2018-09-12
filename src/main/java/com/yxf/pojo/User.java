package com.yxf.pojo;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Setter
@Getter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private String useid;//用户编号
    private String usename;//登录名
    private String passwd;//密码
    private String usetype;//角色
    private String nickname;//昵称


}
