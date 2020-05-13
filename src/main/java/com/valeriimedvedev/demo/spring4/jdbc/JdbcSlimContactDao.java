package com.valeriimedvedev.demo.spring4.jdbc;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcSlimContactDao implements SlimContactDao, InitializingBean {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public String findFirstNameById(Long id) {
        return jdbcTemplate.queryForObject(
                "select first_name from contact where id = ?",
                new Object[] {id}, String.class);
    }

    public void afterPropertiesSet() throws Exception {
        if (dataSource == null) {
            throw new BeanCreationException("Must set dataSource on ContactDao");
        }

        if (jdbcTemplate == null) {
            throw new BeanCreationException("Null JdbcTemplate on ContactDao");
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);

        MySQLErrorCodesTranslator errorTranslator =
                new MySQLErrorCodesTranslator();

        errorTranslator.setDataSource(dataSource);

        jdbcTemplate.setExceptionTranslator(errorTranslator);

        this.jdbcTemplate = jdbcTemplate;
    }
}
