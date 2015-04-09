package shtykh.topic.provider;

import java.util.Set;

/**
 * Created by shtykh on 06/04/15.
 */
public interface Provider<T>{
	void init() throws Exception;
	Set<String> keySet();
	T get(Object key) throws Exception;
}
