package com.workintech.library;

import com.workintech.library.model.*;
import com.workintech.library.service.Library;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);


        seedBooks(library);
        seedMembers(library);

        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== KÜTÜPHANE OTOMASYON SİSTEMİ ===");
            System.out.println("1. Tüm Kitapları Listele");
            System.out.println("2. Kitap İsmi İle Ara");
            System.out.println("3. Kategoriye Göre Kitapları Gör");
            System.out.println("4. Yazara Göre Kitapları Gör");
            System.out.println("5. Yeni Kitap Ekle");
            System.out.println("6. Kitap Ödünç Ver (Öğrenci ID İle)");
            System.out.println("7. Kitap İade Al (Öğrenci ID İle)");
            System.out.println("8. Öğrencinin Üzerindeki Kitapları Gör");
            System.out.println("0. Çıkış");
            System.out.print("Seçiminiz (0-8 arası): ");

            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("⚠️ HATA: Lütfen sadece sayı giriniz!");
                scanner.nextLine();
                continue;
            }

            if (choice < 0 || choice > 9) {
                System.out.println("⚠️ HATA: Geçersiz seçim! Lütfen 0-8 arasında bir değer giriniz.");
                continue;
            }

            switch (choice) {
                case 1:
                    library.listAllBooks();
                    break;

                case 2:
                    System.out.print("Aranacak kitap adı : ");
                    library.searchByTitle(scanner.nextLine());
                    break;

                case 3:
                    System.out.print("Kategori girin (Roman, Bilim, Tarih, Yazılım vb.): ");
                    library.listByCategory(scanner.nextLine());
                    break;

                case 4:
                    System.out.print("Yazar adı girin: ");
                    library.listByAuthor(scanner.nextLine());
                    break;

                case 5:
                    long id;
                    while (true) {
                        id = getValidLongInput(scanner, "Kitap ID (Sadece Rakam): ");
                        if (library.findById(id) != null) {
                            System.out.println("⚠️ HATA: " + id + " ID'li bir kitap sistemde zaten mevcut!");
                        } else {
                            break;
                        }
                    }

                    System.out.print("Kitap Adı: ");
                    String title = scanner.nextLine();
                    System.out.print("Yazar Adı: ");
                    String authorName = scanner.nextLine();
                    double price = getValidDoubleInput(scanner, "Fiyat (Sadece Rakam): ");
                    System.out.print("Kategori: ");
                    String cat = scanner.nextLine();

                    library.addBook(new StudyBooks(id, title, new Author(authorName), price, cat));
                    break;

                case 6:
                    Reader borrowingReader = getOrCreateReader(scanner, library);
                    if (borrowingReader != null) {
                        long borrowBookId = getValidLongInput(scanner, "Alınacak Kitap ID: ");
                        library.borrowBook(borrowingReader, borrowBookId);
                    }
                    break;

                case 7:
                    Reader returningReader = getOrCreateReader(scanner, library);
                    if (returningReader != null) {
                        long returnBookId = getValidLongInput(scanner, "İade Edilecek Kitap ID: ");
                        int daysLate = (int) getValidLongInput(scanner, "Kaç gün geciktiniz? (Gecikme yoksa 0): ");
                        library.returnBook(returningReader, returnBookId, daysLate);
                    }
                    break;

                case 8:
                    Reader showReader = getOrCreateReader(scanner, library);
                    if (showReader != null) {
                        showReader.showMyBooks();
                    }
                    break;

                case 0:
                    exit = true;
                    System.out.println("Kütüphane otomasyon sisteminden çıkılıyor. İyi günler!");
                    break;
            }
        }
        scanner.close();
    }

    // --- ID SORGULAYAN, YOKSA YENİ KAYIT AÇAN YARDIMCI METOD ---
    private static Reader getOrCreateReader(Scanner scanner, Library library) {
        long studentId = getValidLongInput(scanner, "Öğrenci / Üye ID Numarası: ");
        Reader reader = library.findMemberById(studentId);

        if (reader == null) {
            System.out.println("⚠️ " + studentId + " ID numaralı üye sistemde bulunamadı!");
            System.out.print("Yeni öğrenci kaydı oluşturmak ister misiniz? (E/H): ");
            String response = scanner.nextLine().trim();

            if (response.equalsIgnoreCase("E")) {
                System.out.print("Yeni Öğrencinin Adı ve Soyadı: ");
                String name = scanner.nextLine().trim();
                reader = new Reader(name);
                library.addMember(studentId, reader);
            } else {
                System.out.println("İşlem iptal edildi.");
                return null;
            }
        } else {
            System.out.println("👤 Öğrenci Doğrulandı: " + reader.getName());
        }

        return reader;
    }

    // Sistemdeki Örnek Öğrenciler/Üyeler
    private static void seedMembers(Library library) {
        library.addMember(2001, new Reader("Ahmet Yılmaz"));
        library.addMember(2002, new Reader("Ayşe Kaya"));
        library.addMember(2003, new Reader("Mehmet Demir"));
    }

    // Sistemdeki Örnek Kitaplar
    private static void seedBooks(Library library) {
        Author a1 = new Author("Yaşar Kemal");
        Author a2 = new Author("Orhan Pamuk");
        Author a3 = new Author("Sabahattin Ali");
        Author a4 = new Author("Mustafa Kemal Atatürk");
        Author a5 = new Author("Robert C. Martin");
        Author a6 = new Author("Yuval Noah Harari");

        library.addBook(new StudyBooks(101, "İnce Memed 1", a1, 150.0, "Roman"));
        library.addBook(new StudyBooks(102, "İnce Memed 2", a1, 160.0, "Roman"));
        library.addBook(new StudyBooks(103, "Masumiyet Müzesi", a2, 180.0, "Roman"));
        library.addBook(new StudyBooks(104, "Kar", a2, 140.0, "Roman"));
        library.addBook(new StudyBooks(105, "Kürk Mantolu Madonna", a3, 90.0, "Roman"));
        library.addBook(new StudyBooks(106, "İçimizdeki Şeytan", a3, 95.0, "Roman"));
        library.addBook(new StudyBooks(107, "Nutuk", a4, 200.0, "Tarih"));
        library.addBook(new StudyBooks(108, "Clean Code", a5, 450.0, "Yazılım"));
        library.addBook(new StudyBooks(109, "Clean Architecture", a5, 480.0, "Yazılım"));
        library.addBook(new StudyBooks(110, "Sapiens", a6, 220.0, "Tarih"));
        library.addBook(new StudyBooks(111, "Homo Deus", a6, 230.0, "Tarih"));
        library.addBook(new Magazines(112, "Bilim ve Teknik - Sayı 100", new Author("TÜBİTAK"), 40.0, "Bilim"));
        library.addBook(new Magazines(113, "National Geographic", new Author("Kolektif"), 50.0, "Coğrafya"));
        library.addBook(new StudyBooks(114, "Java ile Nesne Yönelimli Programlama", new Author("Akademik Yazar"), 300.0, "Yazılım"));
        library.addBook(new StudyBooks(115, "Veri Yapıları ve Algoritmalar", new Author("Akademik Yazar"), 320.0, "Yazılım"));
    }

    public static long getValidLongInput(Scanner scanner, String prompt) {
        long value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextLong()) {
                value = scanner.nextLong();
                scanner.nextLine();
                if (value >= 0) break;
                System.out.println("⚠️ HATA: Değer negatif olamaz!");
            } else {
                System.out.println("⚠️ HATA: Lütfen sadece rakam giriniz!");
                scanner.nextLine();
            }
        }
        return value;
    }

    public static double getValidDoubleInput(Scanner scanner, String prompt) {
        double value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                scanner.nextLine();
                if (value >= 0) break;
                System.out.println("⚠️ HATA: Fiyat negatif olamaz!");
            } else {
                System.out.println("⚠️ HATA: Lütfen geçerli bir sayı giriniz!");
                scanner.nextLine();
            }
        }
        return value;
    }
}