package shtykh.topic;

import java.util.Set;

/**
 * Created by shtykh on 06/04/15.
 */
public interface Provider<T>{
	Set<String> keySet();
	T get(Object key) throws Exception;
}
