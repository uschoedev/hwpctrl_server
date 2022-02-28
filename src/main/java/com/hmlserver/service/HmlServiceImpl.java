package com.hmlserver.service;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.hmlserver.dao.HmlDao;
import com.hmlserver.util.CryptoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.*;

/**
 * @Class HmlService
 * @AUthor uschoe
 * @Date 2021.12.21
 */
@Service("hmlService")
public class HmlServiceImpl implements HmlService{

    private static final Logger logger = LoggerFactory.getLogger(HmlServiceImpl.class);

    /** 생성자를 통하여 dao 및 암복호화 유틸을 자동으로 주입받는다. */
    private final HmlDao hmlDao;
    private final CryptoUtil util;

    @Autowired
    public HmlServiceImpl(HmlDao hmlDao, CryptoUtil util){
        this.hmlDao = hmlDao;
        this.util = util;
    }

    /**
     *
     * @param params
     * @return resultJson
     * @Description
     * 감정평가방법 확인
     */
    @Override
    public JsonObject checkApprMethod(Map<String, Object> params) throws Exception {

        JsonObject resultJson = new JsonObject();

        final String apprId = String.valueOf(params.get("apprId"));

        if(!apprId.isEmpty()) {

            List<List<?>> refDataList = hmlDao.checkApprMethod(apprId);

            String refDataJsonStr = String.valueOf(new Gson().toJson(refDataList.get(0)));
            JsonElement jsonElement = JsonParser.parseString(refDataJsonStr);

            resultJson = jsonElement.getAsJsonObject();
//          logger.info("checkApprMethod.resultJson :::: " + resultJson);

        }

        return resultJson;

    }

    /**
     *
     * @param params
     * @return resultJson
     * @Description
     * 감정서 데이터 호출
     *
     */
    @Override
    public JsonObject callApprData(Map<String, Object> params) throws Exception {

        JsonObject resultJson = new JsonObject();

        final String apprId = String.valueOf(params.get("apprId")).trim();
        final List<String> procedureList = (List<String>) params.get("procedureList");

        if (!procedureList.isEmpty()) {
            resultJson = selectApprData(procedureList, apprId);
        }

        logger.info("callApprData.resultJson ::: " + resultJson);

        return resultJson;

    }

    /**
     *
     * @param separator
     * @param fieldList
     * @Description
     * 감정서 프로시저 호출을 위한 필드 분리작업
     * 규칙 : 프로시저ID/필드ID
     * 분리 작업하여 프로시저ID를 기준으로 객체화함.
     * 현재는 사용하지 않아서 Deprecated 처리했지만, 추후 응용이 가능하고, 재사용시에는 Deprecated Annotation을 해제하고 사용하는 것이 문맥상 정확하다.
     * @return
     */
    @Deprecated
    private JsonObject getApprProcId(String separator, List<String> fieldList) {

        JsonObject resultJson = new JsonObject();

        JsonArray procArray = new JsonArray();
        JsonObject procJson = new JsonObject();

        String[] splitField;

//        logger.info("separator :::: " + separator);
//        logger.info("fieldList :::: " + fieldList.toString());

        /** 문서상에 매핑된 필드 규칙이 프로시저id/필드id로 되어있어서 프로시저별로 필드키를 정렬하기 위하여 아래 로직이 구성되었음. **/
        if(!fieldList.isEmpty()){

            for(String field : fieldList) {

                if(field.isEmpty()) { continue; }

                splitField = field.split(separator);

//                logger.info("splitField =========>>>> ");
//                logger.info(splitField[0] + " :::: " + splitField[1]);

                if(splitField.length > 1){

                    if(procJson.has(splitField[0])){
                        procArray = (JsonArray) procJson.get(splitField[0]);
                    } else {
                        procArray = new JsonArray();
                    }

                    procArray.add(splitField[1]);

                }

//                logger.info("procArray ============>>>> ");
//                logger.info(procArray.toString());

                if(procArray.size() > 0) resultJson.add(splitField[0], procArray);

            }

        }

        //logger.info("fieldJson ==========>>");
        //logger.info(resultJson.toString());

        return resultJson;

    }

    /**
     *
     * @param procedureList
     * @param apprId
     * @Description
     *  - 감정서 장표와 매칭된 프로시저 ID를 호출하여 데이터를 바인딩한다.
     *  - 프로시저 ID가 배열 형태로 진입한다. ( 장표별로 호출하는 프로시저가 다름. )
     * @return
     *
     */
    private JsonObject selectApprData(List<String> procedureList, String apprId) throws Exception {

        JsonObject resultJson = new JsonObject();

        /** 반복문에서 활용하기 위한 참조변수 */
        List<List<Object>> refDataList;
        JsonArray refDataArray;

        for(String procedureId : procedureList){

            refDataList = hmlDao.selectApprData(procedureId, apprId);

            if(!refDataList.isEmpty()) {

                /**
                 *  1. 프로시저 데이터가 여러 행이 나올수 있으므로 아래 로직이 구현됨...
                 *  2. JSON으로 파싱하는 과정에서 복호화 데이터는 별도로 복호화 진행을 하기 위함.
                 **/
//                logger.info("refDataList ::: " + refDataList);
//                logger.info("refDataList.size ::: " + refDataList.size());

                refDataArray = parseRowJson(refDataList);
                resultJson.add(procedureId, refDataArray);

            }

        }

        return resultJson;

    }

    /**
     * row 데이터를 꺼내서 파싱 진행 => gson
     * 아래 키의 데이터가 포함된 경우 복호화 실행
     * Owner : 소유자
     * OwnerContact : 소유자 업체?
     * Debtor : 채무자
     * @param rowDataList
     * @return
     */
    private JsonArray parseRowJson(List<List<Object>> rowDataList) {

//        logger.info("getRowJson.parseRowJson :::: " + rowDataList);

        Gson gson = new Gson();
        JsonArray dataArray = new JsonArray();

        /** 데이터 색인시 활용되는 참조변수 */
        String jsonStr;
        JsonElement jsonElement;
        JsonObject jsonObject;
        JsonArray jsonArray;

        for(List<Object> refDataList : rowDataList) {

            jsonStr = gson.toJson(refDataList);
            jsonElement = JsonParser.parseString(jsonStr);

//            logger.info("jsonElement ::: " + jsonElement);

            if(jsonElement != null) {

                /** 소유자이거나 채무자인 경우에만 암호화 실행..*/
                if (jsonElement.isJsonObject()) {

                    jsonObject = jsonElement.getAsJsonObject();

                    if(jsonObject.size() <= 0){ continue; }
                    else {

                        jsonObject = decryptJson(jsonObject);
                        dataArray.add(jsonObject);

                    }

                } else if (jsonElement.isJsonArray()) {

                    jsonArray = jsonElement.getAsJsonArray();

                    for (int j = 0; j < jsonArray.size(); j++) {

                        jsonObject = decryptJson(jsonArray.get(j).getAsJsonObject());
                        jsonElement = jsonObject;
                        dataArray.add(jsonElement);

                    }

                }

//            logger.info("jsonObject.size ::: " + jsonObject.size());
//            logger.info("jsonArray.size ::: " + jsonArray.size());

            }

        }

        return dataArray;

    }


    private JsonObject decryptJson(JsonObject dataJson) {

        List<String> decryptField = Arrays.asList("Owner", "Debtor", "OwnerContact");

        String refDataValue;
        String decryptValue;

        for(String refKey : decryptField) {

            if (dataJson.has(refKey)) {

                refDataValue = dataJson.get(refKey).getAsString();
                if(refDataValue.isEmpty()) continue;

                decryptValue = util.decSeed(refDataValue);

                //logger.info("decrypted ::: [" + refKey + "].[" + refDataValue + "] => [" + decryptValue + "]");
                dataJson.addProperty(refKey, decryptValue);

            }

        }

        return dataJson;

    }


}



