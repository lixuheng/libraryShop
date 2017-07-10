package cn.bewweb.entities;

public class BookWithBLOBs extends Book {
    private String inroduction;

    private String direcotry;

    private String authorPreface;

    private String translatorPreface;

    private String pressPreface;

    private String recommend;

    public String getInroduction() {
        return inroduction;
    }

    public void setInroduction(String inroduction) {
        this.inroduction = inroduction == null ? null : inroduction.trim();
    }

    public String getDirecotry() {
        return direcotry;
    }

    public void setDirecotry(String direcotry) {
        this.direcotry = direcotry == null ? null : direcotry.trim();
    }

    public String getAuthorPreface() {
        return authorPreface;
    }

    public void setAuthorPreface(String authorPreface) {
        this.authorPreface = authorPreface == null ? null : authorPreface.trim();
    }

    public String getTranslatorPreface() {
        return translatorPreface;
    }

    public void setTranslatorPreface(String translatorPreface) {
        this.translatorPreface = translatorPreface == null ? null : translatorPreface.trim();
    }

    public String getPressPreface() {
        return pressPreface;
    }

    public void setPressPreface(String pressPreface) {
        this.pressPreface = pressPreface == null ? null : pressPreface.trim();
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend == null ? null : recommend.trim();
    }

	@Override
	public String toString() {
		return "BookWithBLOBs [inroduction=" + inroduction + ", direcotry=" + direcotry + ", authorPreface="
				+ authorPreface + ", translatorPreface=" + translatorPreface + ", pressPreface=" + pressPreface
				+ ", recommend=" + recommend + ", isbn=" + isbn + ", bookName=" + bookName + ", author=" + author
				+ ", translator=" + translator + ", press=" + press + ", pressDay=" + pressDay + ", edition=" + edition
				+ ", allPages=" + allPages + ", format=" + format + ", bookPackage=" + bookPackage + ", pager=" + pager
				+ ", type=" + type + ", folder=" + folder + ", eBook=" + eBook + ", eBookPrice=" + eBookPrice
				+ ", borrowCopyCount=" + borrowCopyCount + ", sellCopyCount=" + sellCopyCount + "]";
	}


    
    
}