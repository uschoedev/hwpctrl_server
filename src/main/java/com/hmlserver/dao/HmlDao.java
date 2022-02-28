package com.hmlserver.dao;

import com.google.gson.JsonArray;
import com.hmlserver.vo.HmlParam;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Class HmlDao
 * @Author uschoe
 * @Date 2021.12.17
 */
@Repository("hmlDao")
public class HmlDao {

    private static final Logger logger = LoggerFactory.getLogger(HmlDao.class);

    /** 매퍼 통신을 위한 namespace 식별자 -> appication.properties */
    @Value("${spring.mssql.mapper.prefix}")
    private String MAPPER_PREFIX;

    /** 생성자를 통하여 Bean으로 생성된 SqlSession 정보를 자동으로 주입받는다. */
    private final SqlSession sqlSession;

    @Autowired
    public HmlDao(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }

    /** 감정서 평가방법 체크 **/
    public List<List<?>> checkApprMethod(final String apprId) {

        HmlParam hmlParam = new HmlParam.builder("", Integer.parseInt(apprId)).build();
        List<List<?>> resultList = sqlSession.selectList(MAPPER_PREFIX + ".checkApprMethod", hmlParam);

        return resultList;

    }

    /** 프로시저 ID로 프로시저를 호출하여 감정서의 장표 데이터를 전달받는다. */
    public List<List<Object>> selectApprData(final String procedureId, final String apprId) {

        /** 나라감정의 요청으로 Lombok의 빌더패턴을 활용할 수 없어서 응용해서 구현함. */
        HmlParam hmlParam = new HmlParam.builder(procedureId, Integer.parseInt(apprId)).build();
        List<List<Object>> resultList = sqlSession.selectList(MAPPER_PREFIX + "." + procedureId, hmlParam);

        return resultList;

    }

}

