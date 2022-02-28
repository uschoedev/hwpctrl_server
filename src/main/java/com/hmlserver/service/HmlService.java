package com.hmlserver.service;

import com.google.gson.JsonObject;

import java.util.Map;

public interface HmlService {

    /** 감정서 로드를 위한 감정평가방법 체크 */
    JsonObject checkApprMethod(Map<String, Object> params) throws Exception;

    /** 감정서 데이터 호출 */
    JsonObject callApprData(Map<String, Object> params) throws Exception;

}
