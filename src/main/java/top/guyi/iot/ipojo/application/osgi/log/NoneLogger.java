package top.guyi.iot.ipojo.application.osgi.log;

import lombok.AllArgsConstructor;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerConsumer;

/**
 * @author guyi
 * 空Logger，所有日志都会被丢弃
 */
@AllArgsConstructor
public class NoneLogger implements Logger {

    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void trace(String s) {

    }

    @Override
    public void trace(String s, Object o) {

    }

    @Override
    public void trace(String s, Object o, Object o1) {

    }

    @Override
    public void trace(String s, Object... objects) {

    }

    @Override
    public <E extends Exception> void trace(LoggerConsumer<E> loggerConsumer) throws E {

    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void debug(String s) {

    }

    @Override
    public void debug(String s, Object o) {

    }

    @Override
    public void debug(String s, Object o, Object o1) {

    }

    @Override
    public void debug(String s, Object... objects) {

    }

    @Override
    public <E extends Exception> void debug(LoggerConsumer<E> loggerConsumer) throws E {

    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public void info(String s) {

    }

    @Override
    public void info(String s, Object o) {

    }

    @Override
    public void info(String s, Object o, Object o1) {

    }

    @Override
    public void info(String s, Object... objects) {

    }

    @Override
    public <E extends Exception> void info(LoggerConsumer<E> loggerConsumer) throws E {

    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public void warn(String s) {

    }

    @Override
    public void warn(String s, Object o) {

    }

    @Override
    public void warn(String s, Object o, Object o1) {

    }

    @Override
    public void warn(String s, Object... objects) {

    }

    @Override
    public <E extends Exception> void warn(LoggerConsumer<E> loggerConsumer) throws E {

    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    @Override
    public void error(String s) {

    }

    @Override
    public void error(String s, Object o) {

    }

    @Override
    public void error(String s, Object o, Object o1) {

    }

    @Override
    public void error(String s, Object... objects) {

    }

    @Override
    public <E extends Exception> void error(LoggerConsumer<E> loggerConsumer) throws E {

    }

    @Override
    public void audit(String s) {

    }

    @Override
    public void audit(String s, Object o) {

    }

    @Override
    public void audit(String s, Object o, Object o1) {

    }

    @Override
    public void audit(String s, Object... objects) {

    }
}
