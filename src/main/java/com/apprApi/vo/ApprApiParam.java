package com.apprApi.vo;

/**
 * @Class ApprApiParam
 * @Author uschoe
 * @Date 2021.12.17
 * @Description
 *   - 감정서 프로시저 호출 시 Mybatis Mapper 상에 필요한 파라미터 전달
 */
public class ApprApiParam {

    private String procId; // 프로시저 ID
    private int apprId; // 감정서 ID

    public String getProcId() {
        return procId;
    }
    public int getApprId() {
        return apprId;
    }

    private ApprApiParam(builder builder){
        this.procId = builder.procId;
        this.apprId = builder.apprId;
    }

    /** Builder Pattern */
    public static class builder{

        private String procId;
        private int apprId;

        public builder(String procId, int apprId){
            this.procId = procId;
            this.apprId = apprId;
        }

        public ApprApiParam build() {
            return new ApprApiParam(this);
        }

        @Override
        public String toString() {
            return "HmlParam.builder{" +
                    "procId='" + procId + '\'' +
                    ", apprId='" + apprId + '\'' +
                    '}';
        }

    }

}
