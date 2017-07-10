package cn.bewweb.docments;

public class SecondItem {
	private String name;
	
	private int type;
	
	public SecondItem(){};
	
	

	public SecondItem(String name) {
		super();
		this.name = name;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}



	@Override
	public String toString() {
		return "SecondItem [name=" + name + ", type=" + type + "]";
	}

	
	
	

}
