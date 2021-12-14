package com.example.interfacetest;

import com.example.DemoApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
public class BaseTest extends AbstractTestNGSpringContextTests {
}
