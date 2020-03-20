package top.guyi.iot.ipojo.application.bean;

import lombok.Data;

/**
 * @author guyi
 * 组件注解信息
 */
@Data
public class ComponentInfo {

    private String name;
    private int order = 999;
    private boolean proxy = true;

    public ComponentInfo(String name) {
        this.name = name;
    }

    public ComponentInfo(String name, int order) {
        this.name = name;
        this.order = order;
    }

    public ComponentInfo(String name,boolean proxy) {
        this.name = name;
        this.proxy = proxy;
    }

    public ComponentInfo(String name, int order, boolean proxy) {
        this.name = name;
        this.order = order;
        this.proxy = proxy;
    }
}
