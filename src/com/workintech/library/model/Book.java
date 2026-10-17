package com.workintech.library.model;

import com.workintech.library.enums.BookStatus;

public class Book {
    private long bookId;
    private String title;
    private Author author;
    private double price;
    private String category;
    private BookStatus status;

    public Book(long bookId, String title, Author author, double price, String category) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
        this.category = category;
        this.status = BookStatus.AVAILABLE;
    }

    public long getBookId() { return bookId; }
    public String getTitle() { return title; }
    public Author getAuthor() { return author; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; } // Fiyat güncelleme için setter
    public String getCategory() { return category; }
    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }

    public void displayDetails() {
        System.out.println("ID: " + bookId + " | Başlık: " + title + " | Yazar: " + author.getName() + " | Kategori: " + category + " | Fiyat: " + price + " TL | Durum: " + status);
    }
}