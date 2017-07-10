package cn.bewweb.docments;

import java.util.Collection;
import java.util.LinkedHashSet;

public class FirstItem {
	private String name;
	
	private int type;
	
	public FirstItem(){
		
	}
	
	public FirstItem(String name) {
		super();
		this.name = name;
	}
	
	public FirstItem(String name, Collection<SecondItem> sitems) {
		super();
		this.name = name;
		this.sitems = sitems;
	}

	private Collection<SecondItem> sitems = new LinkedHashSet<SecondItem>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<SecondItem> getSitems() {
		return sitems;
	}

	public void setSitems(Collection<SecondItem> sitems) {
		this.sitems = sitems;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "FirstItem [name=" + name + ", type=" + type + ", sitems=" + sitems + "]";
	}

	
	
	
}
