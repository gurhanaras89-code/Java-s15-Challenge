package com.workintech.library.model;

import java.util.ArrayList;
import java.util.List;

public class Reader {
    private String name;
    private List<Book> borrowedBooks;

    public Reader(String name) {
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() { return name; }
    public List<Book> getBorrowedBooks() { return borrowedBooks; }

    public boolean canBorrow() {
        return borrowedBooks.size() < 5;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    public void showMyBooks() {
        System.out.println("\n--- " + name + " Tarafından Ödünç Alınan Kitaplar ---");
        if (borrowedBooks.isEmpty()) {
            System.out.println("Üzerinizde hiç kitap yok.");
        } else {
            for (Book b : borrowedBooks) {
                System.out.println("- ID: " + b.getBookId() + " | " + b.getTitle() + " (" + b.getPrice() + " TL)");
            }
        }
    }
}