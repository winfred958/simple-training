package com.winfred.elastic.base;

import com.winfred.elastic.app.ElasticApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ElasticApplication.class)
@AutoConfigureMockMvc
public class BaseTest {


}
