package top.guyi.iot.ipojo.application.osgi.service.reference;

import lombok.Data;

import java.util.UUID;

@Data
public class ServiceReferenceEntry {

    private String id;
    private Class<?>[] serviceClasses;
    private ServiceReferenceInvoker invoker;
    private BundleServiceReferenceChecker checker;

    public ServiceReferenceEntry(Class<?>[] serviceClasses, ServiceReferenceInvoker invoker,BundleServiceReferenceChecker checker) {
        this.id = UUID.randomUUID().toString();
        this.serviceClasses = serviceClasses;
        this.invoker = invoker;
        this.checker = checker;
    }
}
