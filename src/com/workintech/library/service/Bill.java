package com.workintech.library.service;

import com.workintech.library.model.Book;
import com.workintech.library.model.Reader;

public class Bill {
    public static void printInvoice(Reader reader, Book book) {
        System.out.println("\n--- 🧾 ÖDÜNÇ FATURASI ---");
        System.out.println("Müşteri: " + reader.getName());
        System.out.println("Kitap: " + book.getTitle());
        System.out.println("Tutar: " + book.getPrice() + " TL");
        System.out.println("-------------------------\n");
    }

    public static void printRefund(Reader reader, Book book, int daysLate) {
        double finePerDay = 5.0; // Günlük ceza 5 TL
        double totalFine = daysLate * finePerDay;
        double netRefund = book.getPrice() - totalFine;

        System.out.println("\n--- 💸 İADE VE CEZA HESAP FİŞİ ---");
        System.out.println("Okuyucu: " + reader.getName());
        System.out.println("İade Edilen Kitap: " + book.getTitle());
        System.out.println("Kitap Ücreti: " + book.getPrice() + " TL");
        System.out.println("Gecikilen Gün: " + daysLate + " gün (Günlük Ceza: " + finePerDay + " TL)");
        System.out.println("Toplam Ceza: " + totalFine + " TL");
        System.out.println("Okuyucuya İade Edilen Net Tutar: " + (netRefund > 0 ? netRefund : 0) + " TL");
        System.out.println("----------------------------------\n");
    }
}