package com.abdur.testing.repository;

import com.abdur.testing.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@DataJpaTest
//If sql script resides in custom location then use below annotations
//@TestExecutionListeners(listeners = { SqlScriptsTestExecutionListener.class })
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserRepositoryTest {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Test
    @Sql("classpath:UserInfo.sql")
    public void whenInitialized_thenFind() {
        UserInfo userInfo = userInfoRepository.getOne(1L);
        assertThat(userInfo, notNullValue());
    }
}
