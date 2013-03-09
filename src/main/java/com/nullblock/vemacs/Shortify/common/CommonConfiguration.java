package com.nullblock.vemacs.Shortify.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

/**
 * A simple configuration class Supports YAML, .properties
 * 
 * @author tuxed
 * 
 */
public class CommonConfiguration {
	private Map<Object, Object> configuration = new HashMap<Object, Object>();
	private Map<Object, Object> defaults = new HashMap<Object, Object>();

	public CommonConfiguration() {
	}

	public CommonConfiguration(Map<Object, Object> map) {
		configuration = map;
	}

	public CommonConfiguration(Reader f) throws FileNotFoundException {
		loadYaml(f);
	}

	public CommonConfiguration(File f) throws FileNotFoundException {
		loadYaml(f);
	}

	public void addDefault(Object name, Object val) {
		defaults.put(name, val);
	}

	public void removeDefault(Object name) {
		defaults.remove(name);
	}

	@SuppressWarnings("unchecked")
	public void loadYaml(File f) throws FileNotFoundException {
		Object tmp = new Yaml(new SafeConstructor())
				.load(new FileInputStream(f));
		if (tmp instanceof Map<?, ?>) {
			configuration = (Map<Object, Object>) tmp;
		}
	}

	@SuppressWarnings("unchecked")
	public void loadYaml(Reader f) throws FileNotFoundException {
		Object tmp = new Yaml(new SafeConstructor()).load(f);
		if (tmp instanceof Map<?, ?>) {
			configuration = (Map<Object, Object>) tmp;
		}
	}

	public void loadProperties(File f) throws FileNotFoundException,
			IOException {
		Properties p = new Properties();
		p.load(new FileInputStream(f));
		for (Map.Entry<?, ?> entry : p.entrySet()) {
			configuration.put(String.valueOf(entry.getKey()),
					String.valueOf(entry.getValue()));
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
		configuration.remove(node);
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
		return Boolean.parseBoolean(getString(node, String.valueOf(valIfNotFound)));
	}
	
	public boolean contains(Object node) {
		return configuration.containsKey(node);
	}

	private Map<Object, Object> mergeDefaultsAndConfig() {
		HashMap<Object, Object> s = new HashMap<Object, Object>();
		s.putAll(defaults);
		s.putAll(configuration);
		return s;
	}
	
	public void mergeDefaults() {
		configuration = mergeDefaultsAndConfig();
	}

	public void dumpYaml(File f) throws IOException {
		BufferedWriter s = new BufferedWriter(new FileWriter(f));
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

	public void dumpProperties(File f) throws FileNotFoundException,
			IOException {
		Properties p = new Properties();
		p.putAll(mergeDefaultsAndConfig());
		p.store(new FileOutputStream(f), "");
	}

	public Map<?, ?> getConfigurationMap() {
		return configuration;
	}

	@SuppressWarnings("unchecked")
	public void setConfigurationMap(Map<?, ?> cfg) {
		configuration = (Map<Object, Object>) cfg;
	}
}
