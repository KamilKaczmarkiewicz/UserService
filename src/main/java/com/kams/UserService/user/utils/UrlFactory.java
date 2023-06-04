package com.kams.UserService.user.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public class UrlFactory {

    public static <T> String createNextPageUrl(HttpServletRequest request, Page<T> page) {
        if(page.getNumber() >= page.getTotalPages()-1){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(request.getRequestURL().toString());
        builder.append("?");
        request.getParameterMap().forEach((key,value)-> builder.append(createNextPageUrlParameters(key,value)));
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

    private static String createNextPageUrlParameters(String key, String[] value){
        if (key.equals("page")){
            return key + "=" + (Integer.parseInt(value[0])+1) + "&";
        }
        return key + "=" + value[0] + "&";
    }

    public static <T> String createPreviousPageUrl(HttpServletRequest request, Page<T> page) {
        if(page.getNumber() == 0){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(request.getRequestURL().toString());
        builder.append("?");
        request.getParameterMap().forEach((key,value)-> builder.append(createPreviousPageUrlParameters(key,value)));
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

    private static String createPreviousPageUrlParameters(String key, String[] value){
        if (key.equals("page")){
            return key + "=" + (Integer.parseInt(value[0])-1) + "&";
        }
        return key + "=" + value[0] + "&";
    }

}
