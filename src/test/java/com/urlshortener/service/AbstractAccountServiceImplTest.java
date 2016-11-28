package com.urlshortener.service;

import com.urlshortener.AppConfig;
import com.urlshortener.LocalAppConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {AppConfig.class, LocalAppConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/h2/initDB.sql")
public abstract class AbstractAccountServiceImplTest {
    static final int ACCOUNT_ID = 100;
    static final String ACCOUNT_NAME = "first";
    static final String URL = "http://test.com";

    @Autowired
    protected AccountService service;

    @Autowired
    protected UrlService urlService;

}
