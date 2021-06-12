package com.lhever.sc.devops.core.base;

import com.lhever.sc.devops.core.utils.CollectionUtils;
import com.lhever.sc.devops.core.utils.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class AbstractTestCase {
    protected static Logger log = null;

    @Before
    public void setUp() {
        log = LoggerFactory.getLogger(this.getClass());
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.info("++++++++++++++++++++++   TEST  START  +++++++++++++++++++++++++++++++++");
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        beforeTest();
    }

    @After
    public void tearDown() {
        afterTest();
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        log.info("++++++++++++++++++++++   TEST  END    +++++++++++++++++++++++++++++++++");
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    protected void beforeTest() {
        //override this method for add before test behavior.
    }

    protected void afterTest() {
        //override this method for add after test behavior.
    }

    public static <T> OngoingStubbing<T> when(T methodCall) {
        return Mockito.when(methodCall);
    }

    public static <T> T verify(T methodCall) {
        return Mockito.verify(methodCall);
    }

    public static <T> T mock(Class<T> clazz) {
        return Mockito.mock(clazz);
    }

    public static boolean anyBoolean() {
        return Mockito.anyBoolean();
    }

    public static <T> T argThat(ArgumentMatcher<T> matcher) {
        return Mockito.argThat(matcher);
    }

    public static String anyString() {
        return Mockito.anyString();
    }

    public static <T> T spy(T object) {
        return Mockito.spy(object);
    }

    public static <T> List<T> anyList(Class<T> clazz) {
        return Mockito.anyList();
    }

    public static void notNull(String message, Object object) {
        Assert.notNull(object, message);
    }

    public static void notEmpty(String message, String text) {
        if (StringUtils.isEmpty(text)) {
            fail(message);
        }
    }

    public static void notEmpty(String message, Map<?, ?> map) {
        Assert.notEmpty(map, message);
    }

    public static void notEmpty(String message, Collection<?> collection) {
        Assert.notEmpty(collection, message);
    }

    public static void assertEquals(String message, long expected, long actual) {
        org.junit.Assert.assertEquals(message, expected, actual);
    }

    public static void assertEquals(String message, int expected, int actual) {
        org.junit.Assert.assertEquals(message, expected, actual);
    }

    public static void assertEquals(String message, String expected, String actual) {
        if (!StringUtils.equals(expected, actual)) {
            fail(message);
        }
    }

    public static <T> void assertEmpty(String message, Collection<T> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            fail(message);
        }
    }

    public static <T> void assertNotEmpty(String message, Collection<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            fail(message);
        }
    }

    public static void fail(String message) {
        throw new AssertionError(message);
    }

    public static void assertTrue(String message, boolean condition) {
        if (!condition) {
            fail(message);
        }
    }

    public static void assertFalse(String message, boolean condition) {
        assertTrue(message, !condition);
    }

}
