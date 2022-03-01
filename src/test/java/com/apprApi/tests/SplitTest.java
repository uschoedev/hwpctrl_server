package com.apprApi.tests;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.JsonObject;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class SplitTest {

    @Test
    void execute(){

        // -------------------------------------------------------------------------
        // given
        // -------------------------------------------------------------------------
        JsonObject resultJson;

        List<List<Map<String, Object>>> dataList = Arrays.asList(
            Arrays.asList(Collections.EMPTY_MAP), Arrays.asList(Collections.EMPTY_MAP)
        );

        List<String> fieldList = Arrays.asList(
            "APPR_COVER_1/BaseBasicInfoId", "APPR_COVER_1/AppraisalId", "APPR_COVER_1/Subject", "APPR_COVER_1/Reference",
            "APPR_COVER_1/Client", "APPR_COVER_1/Submitter", "APPR_COVER_1/Owner", "APPR_COVER_1/OwnerContact",
            "APPR_COVER_1/Debtor", "APPR_COVER_1/Guarantee", "APPR_COVER_1/AppraisalPurpose", "APPR_COVER_1/ReferenceTime",
            "APPR_COVER_1/ReferenceValue", "APPR_COVER_1/ResearchFromDate", "APPR_COVER_1/ResearchToDate", "APPR_COVER_1/AppraisalCondition",
            "APPR_COVER_1/IssueDate", "APPR_COVER_1/DeterminDate", "APPR_COVER_1/ItemType", "APPR_COVER_1/BaseIndex",
            "APPR_COVER_1/AppraisalMethod", "APPR_COVER_1/ICMMethod", "APPR_COVER_1/RequestDivisionID", "APPR_COVER_1/Firm",
            "APPR_COVER_1/USER_NM", "APPR_COVER_1/managingType", "APPR_COVER_1/ReceiptNo", "APPR_COVER_1/AppraisalPurposeMain",
            "APPR_COVER_1/AppraisalCode", "APPR_COVER_1/electroSign", "APPR_COVER_1/electroStamp", "APPR_COVER_1/BaseIndexInput",
            "APPR_COVER_2/FilePath", "APPR_COVER_2/CAPTION", "APPR_COVER_3/OrgnztAddr", "APPR_COVER_3/OrgnztTel", "APPR_COVER_3/OrgnztFax",
            "APPR_COVER_4/TopAppraiser", "APPR_COVER_4/ReduceReacon"
        );
        // -------------------------------------------------------------------------

        // -------------------------------------------------------------------------
        // when
        // -------------------------------------------------------------------------
        /** [[{}], [{}]] => 고차원 배열 */
        resultJson = build(dataList, fieldList);
        // -------------------------------------------------------------------------

        // -------------------------------------------------------------------------
        // then
        // -------------------------------------------------------------------------
        assertThat(resultJson);
        // -------------------------------------------------------------------------

    }

    private JsonObject build(List<List<Map<String, Object>>> dataList, List<String> fieldList){

        JsonObject resultJson = new JsonObject();

        List<?> rowList;
        Map<String, Object> rowMap;

        for(int i=0; i < dataList.size(); i++) {

            rowList = dataList.get(i);

            for(int j=0; j < rowList.size(); j++) {

                rowMap = (Map<String, Object>) rowList.get(j);

                if(!rowMap.isEmpty()){

                    for(String field : fieldList) {
                        resultJson.addProperty( field, String.valueOf(rowMap.get(field)) );
                    }

                }

            }

        }

        return resultJson;

    }


}
