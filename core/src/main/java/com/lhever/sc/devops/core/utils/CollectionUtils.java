package com.lhever.sc.devops.core.utils;

import com.lhever.sc.devops.core.support.page.RepeatablePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionUtils {

    private static final Logger log = LoggerFactory.getLogger(Bean2MapUtil.class);

    public static boolean isEmpty(Collection coll) {
        return (coll == null || coll.isEmpty());
    }


    public static boolean isNotEmpty(Collection coll) {
        return !CollectionUtils.isEmpty(coll);
    }


    @SuppressWarnings("Map generic type missing")
    public static boolean mapEmpty(Map map) {
        return (map == null || map.size() == 0);
    }

    @SuppressWarnings("Map generic type missing")
    public static boolean mapNotEmpty(Map map) {

        return !mapEmpty(map);
    }

    public static <T> int nullSafeSize(Collection<T> collection) {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    public static <T extends Object> T getValueSafely(Map<String, Object> map, String key, Class<? extends Object> T) {

        if (map == null || map.size() == 0) {
            return null;
        }

        T value = null;
        try {
            value = (T) map.get(key);
        } catch (Exception e) {
            log.error("从map取值, key=[" + key + "], 转换为类型" + T + "错误", e);
        }

        return value;
    }

    public static <T extends Object> T getValue(Map<String, Object> map, String key, Class<? extends Object> T) {

        T value = null;
        try {
            value = (T) map.get(key);
        } catch (Exception e) {
            log.error("从map取值, key=[" + key + "], 转换为类型" + T + "错误", e);
        }

        return value;
    }

    /**
     * 移除链表中的重复元素，注意：链表中的元素必须正确重写equals和hashCode方法。该方法会改变原来链表的大小
     *
     * @param list
     * @return
     * @author lihong10 2017年11月3日 下午15:42:30
     */
    public static <E> List<E> removeRepeat(List<E> list) {
        if (CollectionUtils.isEmpty(list)) {
            return list;

        }
        Set<E> timeSet = new HashSet<E>();
        timeSet.addAll(list); //去重
        list.clear();//清空列表
        list.addAll(timeSet);//去重后追加到列表
        return list;
    }

    public static <ID, A> Map<ID, A> mapping(Collection<A> values, Function<A, ID> idFunc) {
        if (CollectionUtils.isEmpty(values)) {
            return new HashMap<>(0);
        }

        Map<ID, A> map = new HashMap<>();
        for (A a : values) {
            ID id = idFunc.apply(a);
            map.put(id, a);
        }
        return map;
    }

    public static <A, K, V> Map<K, V> mapping(Collection<A> values, Function<A, K> keyFunc, Function<A, V> valueFunc) {
        if (CollectionUtils.isEmpty(values)) {
            return new HashMap<>(0);
        }

        Map<K, V> map = new HashMap<>();
        for (A a : values) {
            K key = keyFunc.apply(a);
            V value = valueFunc.apply(a);
            map.put(key, value);
        }
        return map;
    }

    public static <T> List<T> addTo(List<T> list, boolean ignoreNull, T... args) {
        if (args == null) {
            return list;
        }
        for (T t : args) {
            if (ignoreNull && t == null) {
                continue;
            }
            list.add(t);
        }
        return list;
    }

    public static <T> List<T> addTo(List<T> list, T... args) {
        return addTo(list, true, args);
    }


    public static <T> Set<T> addTo(Set<T> set, boolean ignoreNull, T... args) {
        if (args == null) {
            return set;
        }
        for (T t : args) {
            if (ignoreNull && t == null) {
                continue;
            }
            set.add(t);
        }
        return set;
    }

    public static <T> Set<T> addTo(Set<T> set, T... args) {
        return addTo(set, true, args);
    }

    public static <K, V> Map<K, V> putTo(Map<K, V> map, K key , V value) {
        map.put(key, value);
        return map;
    }



    public  static List<String> getNotBlank(List<String> list) {
        if (list == null) {
            return new ArrayList<>(0);
        }

        List<String> filtered = list.stream().filter(i -> StringUtils.isNotBlank(i)).collect(Collectors.toList());
        return filtered;
    }

    public static  <T> List<T> getNotNull(List<T> list) {
        if (list == null) {
            return new ArrayList<>(0);
        }

        List<T> filtered = list.stream().filter(i -> i != null).collect(Collectors.toList());
        return filtered;
    }



    public static <T> void consumeInPage(List<T> items, int pageSize, Consumer<List<T>> consumer) {
        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize need > 0");
        }

        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        if (items.size() > pageSize) {
            RepeatablePage<T> page = new RepeatablePage<>(items, pageSize);
            for (List<T> subPage : page) {
                consumer.accept(subPage);
            }
        } else {
            consumer.accept(items);
        }

    }




}











