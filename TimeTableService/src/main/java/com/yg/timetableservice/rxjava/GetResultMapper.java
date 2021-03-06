package com.yg.timetableservice.rxjava;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yg.timetableservice.struct.HISResult;
import com.yg.timetableservice.struct.PositionTurn;
import com.yg.timetableservice.struct.ReturnResult;
import com.yg.timetableservice.util.LogUtil;
import com.yg.timetableservice.util.TimeUtil;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;

public class GetResultMapper implements Func1<JSONArray, ReturnResult> {
    @Override
    public ReturnResult call(JSONArray allturns) {
        List<HISResult> data = new ArrayList<>();

        for (Object o : allturns) {
            String arriveTime = ((PositionTurn)o).turn.arriveTime;
            TimeUtil timeUtil = new TimeUtil(arriveTime);
            String dateStr = timeUtil.getHour() + ":" + timeUtil.getMinute();
            HISResult hisResult = new HISResult();
            hisResult.maxArrivalTime = hisResult.minArrivalTime = hisResult.bestArrivalTime = dateStr;
            hisResult.accuracy = ((PositionTurn)o).turn.accuracy;
            int type = ((PositionTurn)o).turn.dataSource;
            switch (type) {
                case 0:
                    hisResult.dataSource = "history";
                    break;
                case 1:
                    hisResult.dataSource = "forge";
                    break;
                case 2:
                    hisResult.dataSource = "history";
                    break;
                default:
                    hisResult.dataSource = "forge";
            }
            if (timeUtil.ifNextDay()) {
                hisResult.nextDayFlag = 1;
            } else {
                hisResult.nextDayFlag = ((PositionTurn)o).nextDayFlag;
            }
            hisResult.nextStopOrder = ((PositionTurn)o).nextStopOrder;
            data.add(hisResult);
        }
        ReturnResult returnResult = new ReturnResult();
        ReturnResult.HttpReturnResult httpReturnResult = new ReturnResult.HttpReturnResult();
        httpReturnResult.data = data;
        returnResult.jsonr = httpReturnResult;
        LogUtil.asyncDebug(new Object[]{"result", JSONObject.toJSONString(returnResult)});
        //LogUtil.debug("get result mapper:" + Thread.currentThread().getName());
        return returnResult;
    }
}
