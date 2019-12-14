package com.winfred.training.designpattern.build.factory.method;

public interface Logger {

    void trace(Object message, Throwable t);

    void info(Object message, Throwable t);

    void debug(Object message, Throwable t);

    void warn(Object message, Throwable t);

    void error(Object message, Throwable t);

}
