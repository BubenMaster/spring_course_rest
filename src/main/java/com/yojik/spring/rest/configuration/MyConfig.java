package com.yojik.spring.rest.configuration;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.yojik.spring.rest")
@EnableWebMvc
@EnableTransactionManagement
public class MyConfig {

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/my_db?useSSL=false&serverTimezone=UTC");
            dataSource.setUser("bestuser");
            dataSource.setPassword("bestuser");
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(){
//        SessionFactory sessionFactory = new LocalSessionFactoryBuilder(dataSource())
//                .addPackage("com/yojik/spring/rest/entity")
//                .addProperties(properties).buildSessionFactory();
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com/yojik/spring/rest/entity");
        sessionFactory.setHibernateProperties(properties());
        return sessionFactory;
    }

    private Properties properties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.setProperty("hibernate.show_sql", "true");
        return properties;
    }

    @Bean
    public TransactionManager transactionManager(){
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

}
