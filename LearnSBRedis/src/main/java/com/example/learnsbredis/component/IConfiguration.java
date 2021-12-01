package com.example.learnsbredis.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author liujd65
 * @date 2021/11/17 17:02
 **/
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IConfiguration {

    @Value("${i.id}")
    String id;

    @Value("${i.name}")
    String name;
}
