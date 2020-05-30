package com.product.model.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AddBookReq {

    @NotBlank(message = "title is missing or blank")
    private String title;

    private String author;

    @NotBlank(message = "isbn is missing or blank")
    private String isbn;

    @NotNull(message = "price is missing")
    private BigDecimal price;

    @NotNull(message = "amount is missing")
    private Integer amount;

    private String shortDesc;

    private String longDesc;

    public AddBookReq() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    @Override
    public String toString() {
        return "AddBookRequestBody [title=" + title + ", author=" + author + ", isbn=" + isbn + ", price=" + price + ", amount=" + amount + ", shortDesc=" + shortDesc
                + ", longDesc=" + longDesc + "]";
    }

}
