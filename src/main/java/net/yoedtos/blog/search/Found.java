package net.yoedtos.blog.search;

import java.util.List;
import java.util.Objects;

import org.hibernate.transform.ResultTransformer;

@SuppressWarnings("serial")
public class Found implements ResultTransformer {

	private Long id;
	private String title;
	private String details;
	
	public Found() {}
	
	public Found(Object id, Object title, Object details) {
		this.id = (Long) id;
		this.title = (String) title;
		this.details = (String) details;
	}
	
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDetails() {
		return details;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		return new Found(tuple[0], tuple[1], tuple[2]);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List transformList(List collection) {
		return null;
	}

	@Override
	public String toString() {
		return "Found [id=" + id + ", title=" + title + ", details=" + details + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(details, id, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Found other = (Found) obj;
		return Objects.equals(details, other.details) && Objects.equals(id, other.id)
				&& Objects.equals(title, other.title);
	}
}
