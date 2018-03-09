package com.test;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by leo on 2017/12/15.
 */
public class TestString {
    public static void main(String[] args){
        String input = " ";
        System.out.println(StringUtils.trim(input));
        System.out.println(StringUtils.isBlank(input));
        System.out.println(StringUtils.isEmpty(input));
    }
}
