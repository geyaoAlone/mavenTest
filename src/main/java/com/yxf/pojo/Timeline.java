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
public class Timeline {

    private String time;
    private String title;
    private String content;
    private String author;

    public String validate(){
        if(title == null){
            return "标题不能为空";
        }
        if(content == null){
            return "内容不能为空";
        }
        return "1";

    }

}
