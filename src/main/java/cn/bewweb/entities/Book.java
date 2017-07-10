package cn.bewweb.entities;

import java.math.BigDecimal;
import java.util.Date;

public class Book {
    protected Long isbn;

    protected String bookName;

    protected String author;

    protected String translator;

    protected String press;

    protected Date pressDay;

    protected Integer edition;

    protected Integer allPages;

    protected String format;

    protected String bookPackage;

    protected String pager;

    protected String type;

    protected String folder;

    protected String eBook;

    protected BigDecimal eBookPrice;

    protected Integer borrowCopyCount;

    protected Integer sellCopyCount;
    
    protected String bookPosition;

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator == null ? null : translator.trim();
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press == null ? null : press.trim();
    }

    public Date getPressDay() {
        return pressDay;
    }

    public void setPressDay(Date pressDay) {
        this.pressDay = pressDay;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public Integer getAllPages() {
        return allPages;
    }

    public void setAllPages(Integer allPages) {
        this.allPages = allPages;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format == null ? null : format.trim();
    }

    public String getBookPackage() {
        return bookPackage;
    }

    public void setBookPackage(String bookPackage) {
        this.bookPackage = bookPackage == null ? null : bookPackage.trim();
    }

    public String getPager() {
        return pager;
    }

    public void setPager(String pager) {
        this.pager = pager == null ? null : pager.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder == null ? null : folder.trim();
    }

    public String geteBook() {
        return eBook;
    }

    public void seteBook(String eBook) {
        this.eBook = eBook == null ? null : eBook.trim();
    }

    public BigDecimal geteBookPrice() {
        return eBookPrice;
    }

    public void seteBookPrice(BigDecimal eBookPrice) {
        this.eBookPrice = eBookPrice;
    }

    public Integer getBorrowCopyCount() {
        return borrowCopyCount;
    }

    public void setBorrowCopyCount(Integer borrowCopyCount) {
        this.borrowCopyCount = borrowCopyCount;
    }

    public Integer getSellCopyCount() {
        return sellCopyCount;
    }

    public void setSellCopyCount(Integer sellCopyCount) {
        this.sellCopyCount = sellCopyCount;
    }

	public String getBookPosition() {
		return bookPosition;
	}

	public void setBookPosition(String bookPosition) {
		this.bookPosition = bookPosition;
	}

	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", bookName=" + bookName + ", author=" + author + ", translator=" + translator
				+ ", press=" + press + ", pressDay=" + pressDay + ", edition=" + edition + ", allPages=" + allPages
				+ ", format=" + format + ", bookPackage=" + bookPackage + ", pager=" + pager + ", type=" + type
				+ ", folder=" + folder + ", eBook=" + eBook + ", eBookPrice=" + eBookPrice + ", borrowCopyCount="
				+ borrowCopyCount + ", sellCopyCount=" + sellCopyCount + ", bookPosition=" + bookPosition + "]";
	}


    
}