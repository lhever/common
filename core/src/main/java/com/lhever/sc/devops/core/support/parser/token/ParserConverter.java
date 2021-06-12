package com.lhever.sc.devops.core.support.parser.token;

@FunctionalInterface
public interface ParserConverter {

    public String convert(String token);
}
