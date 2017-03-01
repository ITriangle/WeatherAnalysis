package com.seentech;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by seentech on 2017/2/15.
 */
public class LogTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    private final Logger logger = LoggerFactory.getLogger(LogTests.class);

//    static private final Logger logger = LoggerFactory.getLogger(LogTests.class);
    @Test
    public void test1(){
        String string = "true";

        boolean bl = Boolean.valueOf(string);

        System.out.println(string + " : " + bl);

        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");

        logger.error("error {}", "param");
    }


}
