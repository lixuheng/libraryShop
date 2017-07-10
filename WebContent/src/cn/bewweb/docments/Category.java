package cn.bewweb.docments;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Category {
	@Id
	private String id;
	@Field
	private String name;
	@Field
	private int type;
	
	public Category(){};
	
	
	
	public Category(String id, String name, Collection<FirstItem> fitems) {
		super();
		this.id = id;
		this.name = name;
		this.fitems = fitems;
	}




	private Collection<FirstItem> fitems = new LinkedHashSet<FirstItem>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<FirstItem> getFitems() {
		return fitems;
	}

	public void setFitems(Collection<FirstItem> fitems) {
		this.fitems = fitems;
	}



	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}



	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", type=" + type + ", fitems=" + fitems + "]";
	}




	
	

}
