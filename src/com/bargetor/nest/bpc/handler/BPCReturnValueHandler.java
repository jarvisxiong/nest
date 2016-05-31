package com.bargetor.nest.bpc.handler;

import com.alibaba.fastjson.JSON;
import com.bargetor.nest.bpc.bean.BPCRequestBean;
import com.bargetor.nest.bpc.bean.BPCResponseBean;
import com.bargetor.nest.common.bpc.BPCUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Bargetor on 16/3/20.
 */
public class BPCReturnValueHandler {

    public void process(HttpServletRequest req, HttpServletResponse resp, BPCRequestBean requestBean, Object returnValue){
        BPCResponseBean responseBean = new BPCResponseBean();
        responseBean.setId(requestBean.getId());
        responseBean.setBpc(requestBean.getBpc());
        responseBean.setResult(returnValue);
        BPCUtil.writeResponse(resp, JSON.toJSONString(responseBean));
    }
}
