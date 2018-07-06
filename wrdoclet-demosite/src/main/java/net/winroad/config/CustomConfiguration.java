package net.winroad.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;

@Configuration
public class CustomConfiguration extends WebMvcConfigurerAdapter{
	

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(jsonResultHandler());
    }

    @Bean
    public JsonResultHandler jsonResultHandler() {
        return new JsonResultHandler();
    }

}
