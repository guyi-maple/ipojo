package tech.guyi.ipojo.application.osgi.service.reference;

import lombok.Data;

import java.util.UUID;

/**
 * @author guyi
 * 服务获取实体
 */
@Data
public class ServiceReferenceEntry {

    private String id;
    private Class<?>[] serviceClasses;
    private AbstractServiceReferenceInvoker invoker;
    private BundleServiceReferenceChecker checker;

    public ServiceReferenceEntry(Class<?>[] serviceClasses, AbstractServiceReferenceInvoker invoker, BundleServiceReferenceChecker checker) {
        this.id = UUID.randomUUID().toString();
        this.serviceClasses = serviceClasses;
        this.invoker = invoker;
        this.checker = checker;
    }
}
