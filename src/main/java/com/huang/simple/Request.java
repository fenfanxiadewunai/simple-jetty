package com.huang.simple;

import java.util.ArrayList;
import java.util.List;

public class Request {
    // 引入请求头
    private RequestHeader header;
    // 设置参数集合
    private List<Parameter> params = new ArrayList<Parameter>();
    // 设置请求参数
    public void setParameter(String param) {
        if(param == null || param.trim().equals("")) {
            return;
        }
        
        String[] result = param.split("&");
        
        for (int i = 0; i < result.length; i++) {
            Parameter parameter = new Parameter();
            parameter.setKey(result[i].split("=")[0]);
            parameter.setValue((result[i].split("=").length <= 1) ? "" : result[i].split("=")[1]);
            params.add(parameter);
        }
    }
    // 获取请求参数的值
    public String getParameterValue(String key) {
        String result = null;
        for(Parameter parameter : params) {
            if(parameter.getKey().equals(key)) {
                result =  parameter.getValue();
            }
        }
        return result;
    }

    public RequestHeader getHeader() {
        return header;
    }

    public void setHeader(RequestHeader header) {
        this.header = header;
    }
}

