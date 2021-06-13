package com.lhever.sc.devops.core.constant;

import com.lhever.sc.devops.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 类说明
 * </p>
 *
 * @author sunbo14 2019/1/3 15:16
 * @version v1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {lihong10} 2019/1/3 15:16
 * @modify by reason: 原因: 升序和降序惯例上(包括sql语句)使用 asc 和 desc表示,
 * 但是前端的控件只能传递全名ascending 和 descending，所以增加了DESCENDING，ASCENDING两个枚举，
 * 并调整了getByOrder方法，保证传递全名时候，也可以匹配简称。
 */
public enum OrderEnum {


    //降序的缩写
    DESC("desc"),
    //降序的全名是:DESCENDING, 但顺序值依然是desc
    DESCENDING(DESC.getOrder()),

    //升序的缩写
    ASC("asc"),
    //升序的全名是ASCENDING:, 但顺序值依然是asc
    ASCENDING(ASC.getOrder());

    /*
     * 顺序字符串
     */
    private String order;
    private static Logger log = LoggerFactory.getLogger(OrderEnum.class);

    private OrderEnum(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }


    /**
     * 根据排序枚举字符串查找枚举常量
     *
     * @param order
     * @return
     * @author lihong10 2019/1/3 16:40
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2019/1/3 16:40
     * @modify by reason:{原因}
     */
    public static OrderEnum getByOrder(String order) {
        if (StringUtils.isBlank(order)) {
            return null;
        }

        order = order.trim();
        OrderEnum orderEnum = null;
        try {
            orderEnum = OrderEnum.valueOf(order.toUpperCase());
        } catch (Exception e) {
            log.error("no such order enum: -> " + order);
        }
        return orderEnum;
    }

    /**
     * 修正排序参数，参数不合法，默认是降序，方法等价于<code>reviseOrElseDescending</code>
     *
     * @param order
     * @return
     * @author lihong10 2019/1/3 16:35
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2019/1/3 16:35
     * @modify by reason:{原因}
     */
    public static String reviseOrder(String order) {
        OrderEnum orderEnum = OrderEnum.getByOrder(order);
        orderEnum = (orderEnum == null) ? OrderEnum.DESC : orderEnum;
        return orderEnum.getOrder();
    }

    /**
     * 修正排序参数，参数不合法，默认升序
     *
     * @param order
     * @return
     * @author lihong10 2019/1/3 16:35
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2019/1/3 16:35
     * @modify by reason:{原因}
     */
    public static String reviseOrElseAscending(String order) {
        return reviseOrder(order, OrderEnum.ASC);
    }

    /**
     * 修正排序参数，参数不合法，默认降序，等同于方法<code>reviseOrder</code>
     *
     * @param order
     * @return
     * @author lihong10 2019/1/3 16:35
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2019/1/3 16:35
     * @modify by reason:{原因}
     */
    public static String reviseOrElseDescending(String order) {
        return reviseOrder(order, OrderEnum.DESC);
    }

    /**
     * 修正排序参数，参数不合法，使用默认值
     *
     * @param order
     * @param order
     * @return
     * @author lihong10 2019/1/3 16:35
     * @modificationHistory=========================逻辑或功能性重大变更记录
     * @modify by user: {修改人} 2019/1/3 16:35
     * @modify by reason:{原因}
     */
    private static String reviseOrder(String order, OrderEnum def) {
        OrderEnum orderEnum = OrderEnum.getByOrder(order);
        orderEnum = (orderEnum == null) ? def : orderEnum;
        return orderEnum.getOrder();
    }


    public static void main(String[] args) {
        getByOrder("ssss");
        getByOrder("desc");
        getByOrder("DESCENDING");
    }


}
