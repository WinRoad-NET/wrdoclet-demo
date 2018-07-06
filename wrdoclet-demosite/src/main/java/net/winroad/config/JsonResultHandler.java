package net.winroad.config;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

public class JsonResultHandler implements HandlerMethodReturnValueHandler, BeanPostProcessor{
    private List<ResponseBodyAdvice<Object>> advices = new ArrayList<>();

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ResponseBodyAdvice) {
            advices.add((ResponseBodyAdvice<Object>) bean);
        } else if (bean instanceof RequestMappingHandlerAdapter) {
            RequestMappingHandlerAdapter handlerAdapter = (RequestMappingHandlerAdapter) bean;
            List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>(handlerAdapter.getReturnValueHandlers());

            JsonResultHandler jsonHandler = null;
            for (int i = handlers.size() - 1; i >= 0; i--) {
                HandlerMethodReturnValueHandler handler = handlers.get(i);
                if (handler instanceof JsonResultHandler) {
                    jsonHandler = (JsonResultHandler) handlers.remove(i);
                    break;
                }
            }

            // 调整 JSON Handler 顺序
            if (jsonHandler != null) {
                handlers.add(0, jsonHandler);
                handlerAdapter.setReturnValueHandlers(handlers);
            }
        }
        return bean;
    }

	@Override
	public Object postProcessBeforeInitialization(Object arg0, String arg1) throws BeansException {
		return arg0;
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
        return returnType.getMethodAnnotation(JsonResult.class) != null;
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
        mavContainer.setRequestHandled(true);
        for (ResponseBodyAdvice<Object> advice : advices) {
            if (advice.supports(returnType, null)) {
                returnValue = advice.beforeBodyWrite(returnValue, returnType, MediaType.APPLICATION_JSON_UTF8, null,
                        new ServletServerHttpRequest(webRequest.getNativeRequest(HttpServletRequest.class)),
                        new ServletServerHttpResponse(webRequest.getNativeResponse(HttpServletResponse.class)));
            }
        }

        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        Annotation[] annotations = returnType.getMethodAnnotations();
        CustomerJsonSerializer jsonSerializer = new CustomerJsonSerializer();

        boolean shortDateFormat = false;
        for (Annotation annotation : annotations) {
            if (annotation instanceof JsonResult) {
                JsonResult json = (JsonResult) annotation;
                shortDateFormat = json.shortDateFormat();
                jsonSerializer.serializer(json.type(), shortDateFormat, json.include(), json.exclude());
                break;
            }
        }

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(jsonSerializer.toJson(returnValue, shortDateFormat));	}

}
