package shtykh.topic;

import java.io.IOException;
import java.util.Set;

/**
 * Created by shtykh on 06/04/15.
 */
public interface Provider<T>{
	Set<String> keySet();
	T get(Object key) throws IOException;
}
