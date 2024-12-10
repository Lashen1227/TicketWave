package com.oop.backend.model;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class Logger {
    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);
}
