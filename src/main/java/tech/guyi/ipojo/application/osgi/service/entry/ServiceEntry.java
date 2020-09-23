package tech.guyi.ipojo.application.osgi.service.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guyi
 * 服务实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceEntry {

    private Class<?> interfaceClass;
    private Class<?> service;

}
