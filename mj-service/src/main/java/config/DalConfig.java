/**
 * pajk.com Inc.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * mybatis配置
 *
 * @author pengfei
 * @version v 0.1 15/5/6 16:28 aaronyue Exp $$
 */
@Configuration
@AutoConfigureAfter({DruidDataSourceConfig.class})
@MapperScan(value = "com.shihou.module.kpl.dao", sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement
public class DalConfig {
    @Autowired
    @Qualifier("ShiHouModuleKplGuess")
    public DruidDataSource druidDataSource;
    @Value(value = "classpath:sqlmap/*.xml")
    private Resource[] mapperLocations;

    @Bean(autowire = Autowire.BY_NAME)
    public TransactionTemplate transactionTemplate() {
        TransactionTemplate template = new TransactionTemplate();
        return template;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("ShiHouModuleKplGuess") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(autowire = Autowire.BY_NAME)
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        ssfb.setDataSource(druidDataSource);
        ssfb.setMapperLocations(mapperLocations);
        ssfb.setTypeAliasesPackage("com.shihou.module.kpl.domain");
        return ssfb;
    }
}
