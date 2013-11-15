package com.nullblock.vemacs.Shortify.common;

import com.google.common.collect.ImmutableMap;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * A simple configuration class Supports YAML, .properties
 *
 * @author tuxed
 */
public class CommonConfiguration {
    private Map<Object, Object> configuration = new HashMap<>();
    private Map<Object, Object> defaults = new HashMap<>();

    public CommonConfiguration() {
    }

    public CommonConfiguration(Map<Object, Object> map) {
        configuration = new HashMap<>(map);
    }

    public CommonConfiguration(Reader f) {
        loadYaml(f);
    }

    public CommonConfiguration(File f) throws IOException {
        loadYaml(f);
    }

    public void addDefault(Object name, Object val) {
        defaults.put(name, val);
    }

    public void removeDefault(Object name) {
        defaults.remove(name);
    }

    @SuppressWarnings("unchecked")
    public void loadYaml(File f) throws IOException {
        Object tmp;
        try (FileInputStream fis = new FileInputStream(f)) {
            tmp = new Yaml(new SafeConstructor()).load(fis);
        }
        if (tmp instanceof Map<?, ?>) {
            configuration = (Map<Object, Object>) tmp;
        }
    }

    @SuppressWarnings("unchecked")
    public void loadYaml(Reader f) {
        Object tmp = new Yaml(new SafeConstructor()).load(f);
        if (tmp instanceof Map<?, ?>) {
            configuration = (Map<Object, Object>) tmp;
        }
    }

    public void loadProperties(File f) throws
            IOException {
        Properties p = new Properties();
        try (FileInputStream fis = new FileInputStream(f)) {
            p.load(fis);
        }
        for (Map.Entry<?, ?> entry : p.entrySet()) {
            configuration.put(entry.getKey(), entry.getValue());
        }
    }

    public Object get(Object node) {
        return get(node, null);
    }

    public Object get(Object node, Object valIfNotFound) {
        if (!configuration.containsKey(node)) {
            if (defaults.containsKey(node)) {
                return defaults.get(node);
            } else {
                return valIfNotFound;
            }
        }
        return configuration.get(node);
    }

    public void set(Object node, Object val) {
        configuration.put(node, val);
    }

    public String getString(Object node) {
        return getString(node, "");
    }

    public String getString(Object node, String valIfNotFound) {
        return String.valueOf(get(node, valIfNotFound));
    }

    public Boolean getBoolean(Object node) {
        return Boolean.parseBoolean(getString(node, "false"));
    }

    public Boolean getBoolean(Object node, Boolean valIfNotFound) {
        return Boolean.parseBoolean(getString(node,
                String.valueOf(valIfNotFound)));
    }

    public boolean contains(Object node) {
        return configuration.containsKey(node);
    }

    private Map<Object, Object> mergeDefaultsAndConfig() {
        Map<Object, Object> s = new HashMap<>();
        s.putAll(defaults);
        s.putAll(configuration);
        return s;
    }

    public void mergeDefaults() {
        configuration = mergeDefaultsAndConfig();
    }

    public void dumpYaml(File f) throws IOException {
        try (BufferedWriter s = new BufferedWriter(new FileWriter(f))) {
            DumperOptions options = new DumperOptions();
            options.setIndent(4);
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            try {
                new Yaml(options).dump(mergeDefaultsAndConfig(), s);
            } finally {
                s.flush();
                s.close();
            }
        }
    }

    public void dumpProperties(File f) throws IOException {
        Properties p = new Properties();
        p.putAll(mergeDefaultsAndConfig());
        try (FileOutputStream fos = new FileOutputStream(f)) {
            p.store(fos, "");
        }
    }

    public Map<?, ?> getConfigurationMap() {
        return ImmutableMap.copyOf(configuration);
    }

    @SuppressWarnings("unchecked")
    public void setConfigurationMap(Map<?, ?> cfg) {
        configuration = new HashMap<>(cfg);
    }
}
