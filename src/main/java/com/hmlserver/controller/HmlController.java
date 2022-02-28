package com.hmlserver.controller;

import com.google.gson.JsonObject;

import com.hmlserver.service.HmlService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Class : HmlController
 * @Author : uschoe
 * @Date : 2021.12.17
 */
@RestController
@CrossOrigin
public class HmlController {

    /**
     * 응답결과 메시지 및 코드
     * */
    private String RESPONSE_MSG = "";
    private int RESPONSE_CODE = 0;

    /**
     * 컨트롤러 호출시 자동으로 Service 객체를 의존성 주입 => @Autowired
     * */
    public final HmlService hmlService;

    @Autowired
    public HmlController(HmlService hmlService) {
        this.hmlService = hmlService;
    }

    @RequestMapping(value = "/checkApprMethod", method = RequestMethod.POST)
    @ResponseBody
    public String checkApprMethod(@RequestBody Map<String, Object> params) {

        JsonObject resultJson = new JsonObject();
        JsonObject dataJson = new JsonObject();

        try{

            /**
             * 감정서 평가방법을 가져오기 위한 Service Layer 호출
             * */
            dataJson = hmlService.checkApprMethod(params);

            /**
             * 204 - NO_CONTENT : 데이터가 없음
             * 200 - OK : 데이터 정상 호출
             * */
            if(!dataJson.isJsonNull()){

                RESPONSE_MSG = HttpStatus.OK.name();
                RESPONSE_CODE = HttpStatus.OK.value();

            } else {

                RESPONSE_MSG = HttpStatus.NO_CONTENT.name();
                RESPONSE_CODE = HttpStatus.NO_CONTENT.value();

            }

        } catch(Exception e) {

            /**
             *  Exception 발생 시 500 에러
             * */
            e.printStackTrace();
            RESPONSE_MSG = e.getClass().getSimpleName() + "::" + e.getLocalizedMessage();
            RESPONSE_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

        /**
         * 결과 데이터 전달을 위한 데이터 바인딩
         * */
        resultJson.addProperty("msg", RESPONSE_MSG);
        resultJson.addProperty("code", RESPONSE_CODE);
        resultJson.addProperty("data", String.valueOf(dataJson) );

        return resultJson.toString();

    }

    /**
     * 감정서 장표별 데이터 호출
     * @param params
     * @author uschoe
     * @Date 2021.01.19
     * @return
     */
    @RequestMapping(value = "/apprBindData", method = RequestMethod.POST)
    @ResponseBody
    public String apprBindData(@RequestBody Map<String, Object> params) {

        JsonObject resultJson = new JsonObject();
        JsonObject dataJson = new JsonObject();

        try{

            /**
             * 감정서 데이터를 가져오기 위한 Service Layer 호출
             * */
            dataJson = hmlService.callApprData(params);

            /**
             * 204 - NO_CONTENT : 데이터가 없음
             * 200 - OK : 데이터 정상 호출
             * */
            if(!dataJson.isJsonNull()){

                RESPONSE_MSG = HttpStatus.OK.name();
                RESPONSE_CODE = HttpStatus.OK.value();

            } else {

                RESPONSE_MSG = HttpStatus.NO_CONTENT.name();
                RESPONSE_CODE = HttpStatus.NO_CONTENT.value();

            }

        } catch(Exception e){

            /**
             *  Exception 발생 시 500 에러
             * */
            e.printStackTrace();
            RESPONSE_MSG = e.getClass().getSimpleName() + "::" + e.getLocalizedMessage();
            RESPONSE_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();

        }

        /**
         * 결과 데이터 전달을 위한 데이터 바인딩
         * */
        resultJson.addProperty("msg", RESPONSE_MSG);
        resultJson.addProperty("code", RESPONSE_CODE);
        resultJson.addProperty("data", String.valueOf(dataJson) );

        return resultJson.toString();

    }

}
