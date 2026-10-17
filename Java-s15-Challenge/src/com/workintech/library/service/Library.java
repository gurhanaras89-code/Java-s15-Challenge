package com.workintech.library.service;

import com.workintech.library.enums.BookStatus;
import com.workintech.library.model.Author;
import com.workintech.library.model.Book;
import com.workintech.library.model.Reader;

import java.util.*;

public class Library {
    private Map<Long, Book> bookMap = new HashMap<>();
    private List<Book> bookList = new ArrayList<>();
    private Set<Author> authorSet = new HashSet<>();
    private Map<Long, Reader> memberMap = new HashMap<>();


    public void addMember(long id, Reader reader) {
        memberMap.put(id, reader);
        System.out.println("✅ Yeni üye başarıyla kaydedildi: " + reader.getName() + " (ID: " + id + ")");
    }


    public Reader findMemberById(long id) {
        return memberMap.get(id);
    }

    public Book findById(long id) {
        return bookMap.get(id);
    }

    public boolean addBook(Book book) {
        if (bookMap.containsKey(book.getBookId())) {
            System.out.println("⚠️ HATA: " + book.getBookId() + " ID'sine sahip bir kitap zaten var!");
            return false;
        }
        bookMap.put(book.getBookId(), book);
        bookList.add(book);
        authorSet.add(book.getAuthor());
        System.out.println("✅ Kitap başarıyla eklendi: " + book.getTitle());
        return true;
    }

    public void listAllBooks() {
        System.out.println("\n=== KÜTÜPHANEDEKİ TÜM KİTAPLAR (" + bookList.size() + " Adet) ===");
        if (bookList.isEmpty()) {
            System.out.println("Kütüphanede kitap bulunmuyor.");
            return;
        }
        for (Book b : bookList) {
            b.displayDetails();
        }
    }

    public void searchByTitle(String keyword) {
        System.out.println("\n--- '" + keyword + "' İçeren Kitaplar ---");
        boolean found = false;
        for (Book b : bookList) {
            if (b.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                b.displayDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("❌ Aranan kelimede kitap bulunamadı.");
        }
    }

    public void listByCategory(String category) {
        System.out.println("\n--- " + category + " Kategorisindeki Kitaplar ---");
        boolean found = false;
        for (Book b : bookList) {
            if (b.getCategory().equalsIgnoreCase(category)) {
                b.displayDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("❌ Bu kategoride kitap bulunamadı.");
        }
    }

    public void listByAuthor(String authorName) {
        System.out.println("\n--- " + authorName + " Tarafından Yazılan Kitaplar ---");
        boolean found = false;
        for (Book b : bookList) {
            if (b.getAuthor().getName().equalsIgnoreCase(authorName)) {
                b.displayDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("❌ Bu yazara ait kitap bulunamadı.");
        }
    }

    public void updateBookPrice(long bookId, double newPrice) {
        Book book = bookMap.get(bookId);
        if (book != null) {
            System.out.println("🔄 " + book.getTitle() + " eski fiyatı: " + book.getPrice() + " TL");
            book.setPrice(newPrice);
            System.out.println("✅ Yeni fiyat güncellendi: " + newPrice + " TL");
        } else {
            System.out.println("❌ Güncellenecek kitap bulunamadı!");
        }
    }


    public void borrowBook(Reader reader, long bookId) {
        Book book = bookMap.get(bookId);

        if (book == null) {
            System.out.println("❌ Kitap bulunamadı!");
            return;
        }

        if (book.getStatus() == BookStatus.BORROWED) {
            System.out.println("❌ Bu kitap zaten başkasında!");
            return;
        }

        if (!reader.canBorrow()) {
            System.out.println("❌ " + reader.getName() + " 5 kitap limitine ulaştığı için daha fazla kitap alamaz!");
            return;
        }

        book.setStatus(BookStatus.BORROWED);
        reader.borrowBook(book);
        Bill.printInvoice(reader, book);
    }


    public void returnBook(Reader reader, long bookId, int daysLate) {
        Book book = bookMap.get(bookId);

        if (book != null && reader.getBorrowedBooks().contains(book)) {
            book.setStatus(BookStatus.AVAILABLE);
            reader.returnBook(book);
            Bill.printRefund(reader, book, daysLate);
        } else {
            System.out.println("❌ Bu kitap " + reader.getName() + " üzerinde görünmüyor!");
        }
    }
}