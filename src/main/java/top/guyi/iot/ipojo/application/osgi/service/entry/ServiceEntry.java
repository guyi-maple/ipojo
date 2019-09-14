package top.guyi.iot.ipojo.application.osgi.service.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceEntry {

    private Class<?> interfaceClass;
    private Class<?> service;

}
