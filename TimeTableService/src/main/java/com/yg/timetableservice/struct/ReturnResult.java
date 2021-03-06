package com.yg.timetableservice.struct;

import java.util.ArrayList;
import java.util.List;


public class ReturnResult {
    public static class HttpReturnResult {
        public int status = 0;
        public String sversion = "0.0.0";
        public Object data;
        public String errmsg = "";
    }
    public HttpReturnResult jsonr;
    static public ReturnResult getErrorResult(String errorMessage) {
        ReturnResult returnResult = new ReturnResult();
        HttpReturnResult httpReturnResult = new HttpReturnResult();
        httpReturnResult.data = new ArrayList<>();
        httpReturnResult.status = -1;
        httpReturnResult.errmsg = errorMessage;
        returnResult.jsonr = httpReturnResult;
        return returnResult;
    }
    static public ReturnResult getNullResult() {
        ReturnResult returnResult = new ReturnResult();
        HttpReturnResult httpReturnResult = new HttpReturnResult();
        httpReturnResult.data = new ArrayList<>();
        httpReturnResult.status = -1;
        httpReturnResult.errmsg = "";
        returnResult.jsonr = httpReturnResult;
        return returnResult;
    }
}
