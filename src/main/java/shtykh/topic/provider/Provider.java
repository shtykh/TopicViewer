package shtykh.topic.provider;

/**
 * Created by shtykh on 06/04/15.
 */
public interface Provider<K, T>{
	K[] keys();
	T get(K key) throws ProviderException;
}
