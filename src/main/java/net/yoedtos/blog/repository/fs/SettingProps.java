package net.yoedtos.blog.repository.fs;

import java.util.Properties;

import net.yoedtos.blog.exception.FSException;
import net.yoedtos.blog.repository.Storage;

public class SettingProps implements Storage<Properties> {

	@Override
	public void store(Properties t) throws FSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drop(Properties t) throws FSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Properties load(Object obj) throws FSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties replace(Properties t) throws FSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object find(Object obj) throws FSException {
		// TODO Auto-generated method stub
		return null;
	}
}
