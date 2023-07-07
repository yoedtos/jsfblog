package net.yoedtos.blog.repository.fs;

import net.yoedtos.blog.exception.FSException;
import net.yoedtos.blog.model.dto.MediaDTO;
import net.yoedtos.blog.repository.Storage;

public class MediaFS implements Storage<MediaDTO> {
	private String urn;
	
	public String getUrn() {
		return this.urn;
	}
	
	@Override
	public void store(MediaDTO t) throws FSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drop(MediaDTO t) throws FSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MediaDTO load(Object obj) throws FSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MediaDTO replace(MediaDTO t) throws FSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object find(Object obj) throws FSException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
