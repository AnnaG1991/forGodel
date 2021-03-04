package com.godeltech.gorodetskaya.task.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("com.godeltech.gorodetskaya.task")
@EnableWebMvc
public class Config {
}
