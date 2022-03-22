package com.apprApi.controller;

import com.apprApi.service.ApprApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Class : ApprApiController
 * @Author : uschoe
 * @Date : 2021.12.17
 */
@RestController
@CrossOrigin
public class ApprApiController {

    /**
     * 응답결과 메시지 및 코드
     * */
    private String RESPONSE_MSG = "";
    private int RESPONSE_CODE = 0;

    /**
     * 컨트롤러 호출시 자동으로 Service 객체를 의존성 주입 => @Autowired
     * */
    public final ApprApiService apprApiService;

    public ApprApiController(ApprApiService apprApiService) {
        this.apprApiService = apprApiService;
    }

    @RequestMapping(value = "/checkApprMethod", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String checkApprMethod(@RequestBody Map<String, Object> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject resultJson = new JsonObject();
        JsonObject dataJson = new JsonObject();

        try{

            /**
             * 감정서 평가방법을 가져오기 위한 Service Layer 호출
             * */
            dataJson = apprApiService.checkApprMethod(params);

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

        return gson.toJson(resultJson);

    }

    /**
     * 감정서 장표별 데이터 호출
     * @param params
     * @author uschoe
     * @Date 2021.01.19
     * @return
     */
    @RequestMapping(value = "/apprBindData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String apprBindData(@RequestBody Map<String, Object> params) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject resultJson = new JsonObject();
        JsonObject dataJson = new JsonObject();

        try{

            /**
             * 감정서 데이터를 가져오기 위한 Service Layer 호출
             **/
            dataJson = apprApiService.callApprData(params);

            /**
             * 204 - NO_CONTENT : 데이터가 없음
             * 200 - OK : 데이터 정상 호출
             **/
            if(!dataJson.isJsonNull()){
                RESPONSE_MSG = HttpStatus.OK.name();
                RESPONSE_CODE = HttpStatus.OK.value();
            } else {
                RESPONSE_MSG = HttpStatus.NO_CONTENT.name();
                RESPONSE_CODE = HttpStatus.NO_CONTENT.value();
            }

        } catch(Exception e){
            /**
             * Exception 발생 시 500 에러
             **/
            e.printStackTrace();
            RESPONSE_MSG = e.getClass().getSimpleName() + "::" + e.getLocalizedMessage();
            RESPONSE_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }

        /**
         * 결과 데이터 전달을 위한 데이터 바인딩
         **/
        resultJson.addProperty("msg", RESPONSE_MSG);
        resultJson.addProperty("code", RESPONSE_CODE);
        resultJson.addProperty("data", String.valueOf(dataJson) );

        return gson.toJson(resultJson);

    }

}

