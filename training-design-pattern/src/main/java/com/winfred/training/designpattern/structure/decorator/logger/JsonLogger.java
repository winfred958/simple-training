package com.winfred.training.designpattern.structure.decorator.logger;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;

import java.util.Arrays;

public class JsonLogger extends LoggerDecorator {

    public JsonLogger(Logger logger) {
        super(logger);
    }

    @Override
    public void info(String msg) {
        JSONObject jsonObject = newJsonObject();
        jsonObject.put("message", msg);
        logger.info(jsonObject.toJSONString());
    }

    @Override
    public void error(String msg) {
        JSONObject jsonObject = newJsonObject();
        jsonObject.put("message", msg);
        logger.info(jsonObject.toJSONString());
    }

    public void error(String msg, Exception e) {
        JSONObject jsonObject = newJsonObject();
        jsonObject.put("message", msg);
        jsonObject.put("stack_trace", Arrays.toString(e.getStackTrace()));
        logger.error("{}", jsonObject.toJSONString());
    }

    private JSONObject newJsonObject() {
        return new JSONObject();
    }
}
