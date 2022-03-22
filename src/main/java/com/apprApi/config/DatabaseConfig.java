package com.apprApi.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @Class : DatabaseConfig
 * @Author : uschoe
 * @Date : 2021.12.17
 */
@Configuration
@EnableTransactionManagement
@MapperScan(
        sqlSessionFactoryRef = "mssqlSessionFactory"
)
public class DatabaseConfig {

    /**
     * Database Connection을 위한 DataSource 객체 생성
     * */
    @Bean(name = "mssqlDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.mssql.datasource")
    public static DataSource mssqlDataSource(){

        DataSource dataSource = DataSourceBuilder
                                .create()
                                .build();

        return dataSource;

    }

    /**
     * DataSource 기반 SqlSession 객체 생성
     * */
    @Bean(name = "mssqlSqlSessionFactory")
    public SqlSessionFactory mssqlSessionFactory(DataSource dataSource) throws Exception {

        SqlSessionFactoryBean mssqlSessionFactoryBean = new SqlSessionFactoryBean();

        mssqlSessionFactoryBean.setDataSource(dataSource);

        Resource[] mybatisMapper = new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/*.xml");
        Resource mybatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/mybatis-config.xml");

        mssqlSessionFactoryBean.setMapperLocations(mybatisMapper);
        mssqlSessionFactoryBean.setConfigLocation(mybatisConfig);

        return mssqlSessionFactoryBean.getObject();

    }

    /**
     * SqlSessionTemplate 생성.. sqlSession을 그대로 사옹할 수 있지만..
     * template 형태로 생성하게 되면 자동으로 트랜잭션을 관리하고 세션의 생명주기를 관리한다..
     * */
    @Bean(name = "mssqlSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate mssqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}


