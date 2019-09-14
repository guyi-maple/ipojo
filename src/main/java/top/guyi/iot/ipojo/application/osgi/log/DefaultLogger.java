package top.guyi.iot.ipojo.application.osgi.log;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerConsumer;

@NoArgsConstructor
@AllArgsConstructor
public class DefaultLogger implements Logger {

    private Logger logger;

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public void trace(String s) {
        logger.trace(s);
    }

    @Override
    public void trace(String s, Object o) {
        logger.trace(s,o);
    }

    @Override
    public void trace(String s, Object o, Object o1) {
        logger.trace(s,o,o1);
    }

    @Override
    public void trace(String s, Object... objects) {
        logger.trace(s,objects);
    }

    @Override
    public <E extends Exception> void trace(LoggerConsumer<E> loggerConsumer) throws E {
        logger.trace(loggerConsumer);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void debug(String s) {
        logger.debug(s);
    }

    @Override
    public void debug(String s, Object o) {
        logger.debug(s,o);
    }

    @Override
    public void debug(String s, Object o, Object o1) {
        logger.debug(s,o,o1);
    }

    @Override
    public void debug(String s, Object... objects) {
        logger.debug(s,objects);
    }

    @Override
    public <E extends Exception> void debug(LoggerConsumer<E> loggerConsumer) throws E {
        logger.debug(loggerConsumer);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void info(String s) {
        logger.info(s);
    }

    @Override
    public void info(String s, Object o) {
        logger.info(s,o);
    }

    @Override
    public void info(String s, Object o, Object o1) {
        logger.info(s,o,o1);
    }

    @Override
    public void info(String s, Object... objects) {
        logger.info(s,objects);
    }

    @Override
    public <E extends Exception> void info(LoggerConsumer<E> loggerConsumer) throws E {
        logger.info(loggerConsumer);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public void warn(String s) {
        logger.warn(s);
    }

    @Override
    public void warn(String s, Object o) {
        logger.warn(s,o);
    }

    @Override
    public void warn(String s, Object o, Object o1) {
        logger.warn(s,o,o1);
    }

    @Override
    public void warn(String s, Object... objects) {
        logger.warn(s,objects);
    }

    @Override
    public <E extends Exception> void warn(LoggerConsumer<E> loggerConsumer) throws E {
        logger.warn(loggerConsumer);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public void error(String s) {
        logger.error(s);
    }

    @Override
    public void error(String s, Object o) {
        logger.error(s,o);
    }

    @Override
    public void error(String s, Object o, Object o1) {
        logger.error(s,o,o1);
    }

    @Override
    public void error(String s, Object... objects) {
        logger.error(s,objects);
    }

    @Override
    public <E extends Exception> void error(LoggerConsumer<E> loggerConsumer) throws E {
        logger.error(loggerConsumer);
    }

    @Override
    public void audit(String s) {
        logger.audit(s);
    }

    @Override
    public void audit(String s, Object o) {
        logger.audit(s,o);
    }

    @Override
    public void audit(String s, Object o, Object o1) {
        logger.audit(s,o,o1);
    }

    @Override
    public void audit(String s, Object... objects) {
        logger.audit(s,objects);
    }
}
