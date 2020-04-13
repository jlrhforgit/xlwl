package com.jlrh.heagle.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * 
 * @author zzw
 * @desc   整合c3p0，mybits数据源。配置c3p0连接属性
 * @date :2020/4/9
 *
 */
@Configuration
@MapperScan("com.jlrh.heagle.server.mybatismapper")//使用MapperScan批量扫描所有的Mapper接口；/jlrh-1/src/main/java/com/jlrh/heagle/server/mapper/UserMapper.java
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class DataSourceConfig {

 
 @Value("${spring.datasource.driver-class-name}")
 private String jdbcDriver;
 @Value("${spring.datasource.url}")
 private String jdbcUrl;
 @Value("${spring.datasource.username}")
 private String jdbcUser;
 @Value("${spring.datasource.password}")
 private String jdbcPassword;

 @Bean(name="dataSource")
 @Qualifier(value="dataSource")//限定描述符除了能根据名字进行注入，但能进行更细粒度的控制如何选择候选者
 @Primary//用@Primary区分主数据源
 public DataSource createDataSource() throws PropertyVetoException {
     ComboPooledDataSource dataSource = new ComboPooledDataSource();

     dataSource.setDriverClass(jdbcDriver);
     dataSource.setJdbcUrl(jdbcUrl);
     dataSource.setUser(jdbcUser);
     dataSource.setPassword(jdbcPassword);
     // 最大等待时间
     // dataSource.setMaxStatements(maxStatements);
     // dataSource.setAcquireIncrement(acquireIncrement);
     // 最大空闲时间,60秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0 -->    
     //	dataSource.setMaxIdleTime(60);
     // 关闭连接后不自动提交
     /**
     <!--初始化时获取三个连接，取值应在minPoolSize与maxPoolSize之间。Default: 3 -->    
		<property name="initialPoolSize" value="3" />  
		<!--连接池中保留的最小连接数。Default: 3 -->  
		<property name="minPoolSize" value="3" />  
		<!--连接池中保留的最大连接数。Default: 15 -->   
		<property name="maxPoolSize" value="15" /> 
		*/
     dataSource.setAutoCommitOnClose(false);
     return dataSource;
 }
 
 
 
 /**
 *返回sqlSessionFactory
 * @throws PropertyVetoException 
 */
 @Bean
 public SqlSessionFactoryBean sqlSessionFactoryBean() throws PropertyVetoException{
   SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
   sqlSessionFactory.setDataSource(createDataSource());
   return sqlSessionFactory;
 }
}