package config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;

/**
 * Created by {renhui} on 2016-06-08.
 */
@Configuration
@EnableTransactionManagement
public class DruidDataSourceConfig {
    private DruidDataSource druidDataSource;

    @Bean(initMethod = "init", destroyMethod = "close", name = "ShiHouModuleKplGuess")
    @Primary
    public DruidDataSource dataSource(@Value("${server.jdbc.url}") String url,
                                      @Value("${server.jdbc.username}") String username,
                                      @Value("${server.jdbc.password}") String password,
                                      @Value("${server.jdbc.maxActive}") int maxActive,
                                      @Value("${server.jdbc.maxWait}") int maxWait,
                                      @Value("${server.jdbc.initialSize}") int initialSize,
                                      @Value("${server.jdbc.minIdle}") int minIdle,
                                      @Value("${server.jdbc.slowSqlMillis}") int slowSqlMillis) throws SQLException {
        druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMaxWait(maxWait);//30s
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setValidationQuery("SELECT 'x'");
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setFilters("stat, wall");
        druidDataSource.setPoolPreparedStatements(true);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        return druidDataSource;
    }
}
