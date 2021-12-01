package opnc.config;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigUtils {

    private final static Log log = LogFactory.getLog(ConfigUtils.class);

    /**
     * 配置文件名称
     */
    private final static String FILE_NAME = "config.properties";

    private final Map<String, String> propertyMap;

    public final static ConfigUtils INSTANCE = new ConfigUtils();

    private ConfigUtils() {
        this.propertyMap = new HashMap<>();

        Properties property = loadProperty();
        if (property == null) {
            return;
        }

        Enumeration<Object> keys = property.keys();
        while (keys.hasMoreElements()) {
            String key = String.valueOf(keys.nextElement());
            String value = property.getProperty(key, "");

            log.debug("key: " + key + ",value: " + value);

            value = new String(value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            this.propertyMap.put(key, value);
        }
    }

    private Properties loadProperty() {
        log.debug("loadProperty  FILE_NAME: " + FILE_NAME);

        Properties properties = new Properties();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(FILE_NAME);
        if (in == null) {
            log.debug("config.properties stream: null");
            return null;
        }
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public String getProperty(String key) {
        String value = propertyMap.get(key);
        return value == null ? "" : value;
    }
}
