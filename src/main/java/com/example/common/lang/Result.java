package com.example.common.lang;

import lombok.Data;
import org.springframework.validation.ObjectError;

@Data
public class Result {
    private String code;
    private String msg;
    private Object data;

    public static Result succ(Object data){
        Result m=new Result();
        m.setData(data);
        m.setCode("200");
        m.setMsg("操作成功");
        return m;
    }

    public static Result succ(Object data,String msg){
        Result m=new Result();
        m.setMsg(msg);
        m.setCode("200");
        m.setData(data);
        return m;
    }

    public static Result fail(Object data){
        Result m=new Result();
        m.setMsg("操作失败");
        m.setCode("501");
        m.setData(data);
        return m;
    }
    public static Result fail(Object data,String msg){
        Result m=new Result();
        m.setMsg(msg);
        m.setCode("501");
        m.setData(data);
        return m;
    }

}
