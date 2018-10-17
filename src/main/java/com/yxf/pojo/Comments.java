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
public class Comments {

    private String commentid;
    private int type ;//1-网站留言；2-博客留言
    private String blogid;
    private String time;
    private String author;//作者
    private String content;
}
