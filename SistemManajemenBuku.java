/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package praktikum12_soal;

/**
 *
 * @author ASUS
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Buku implements Serializable {
    private String judul;
    private String pengarang;
    private int tahunTerbit;

    public Buku(String judul, String pengarang, int tahunTerbit) {
        this.judul = judul;
        this.pengarang = pengarang;
        this.tahunTerbit = tahunTerbit;
    }

    @Override
    public String toString() {
        return "Judul: " + judul + ", Pengarang: " + pengarang + ", Tahun Terbit: " + tahunTerbit;
    }

    public void tampilkanInfo() {
        System.out.println(this);
    }
}

public class SistemManajemenBuku {
    private static final String FILE_BUKU_TXT = "buku.txt";   
    private static final String FILE_BUKU_SER = "buku.ser";   
    private static List<Buku> daftarBuku = new ArrayList<>(); 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu Sistem Manajemen Buku:");
            System.out.println("1. Tambah Buku Baru dan Simpan ke buku.txt");
            System.out.println("2. Simpan Objek Buku ke buku.ser (Serialisasi)");
            System.out.println("3. Tampilkan Daftar Buku dari buku.txt");
            System.out.println("4. Tampilkan Daftar Buku dari buku.ser");
            System.out.println("5. Keluar");
            System.out.print("Pilih opsi: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); 

            switch (pilihan) {
                case 1:
                    tambahBuku(scanner);
                    break;
                case 2:
                    simpanKeFileSerial();
                    break;
                case 3:
                    tampilkanDariFileTeks();
                    break;
                case 4:
                    tampilkanDariFileSerial();
                    break;
                case 5:
                    System.out.println("Keluar dari program.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void tambahBuku(Scanner scanner) {
        System.out.println("Masukkan judul buku: ");
        String judul = scanner.nextLine();
        System.out.println("Masukkan nama pengarang: ");
        String pengarang = scanner.nextLine();
        System.out.println("Masukkan tahun terbit: ");
        int tahunTerbit = scanner.nextInt();
        scanner.nextLine(); 

        Buku buku = new Buku(judul, pengarang, tahunTerbit);
        daftarBuku.add(buku);
        System.out.println("Buku berhasil ditambahkan.");
        
        simpanKeFileTeks();
    }

    private static void simpanKeFileTeks() {
        try (FileWriter writer = new FileWriter(FILE_BUKU_TXT, true); 
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            for (Buku buku : daftarBuku) {
                bufferedWriter.write(buku.toString() + "\n");
            }
            System.out.println("Data buku berhasil disimpan ke file buku.txt.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan ke file buku.txt.");
            e.printStackTrace();
        }
    }

    private static void simpanKeFileSerial() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_BUKU_SER))) {
            oos.writeObject(daftarBuku);
            System.out.println("Objek buku berhasil disimpan ke file buku.ser.");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan ke file buku.ser.");
            e.printStackTrace();
        }
    }

    private static void tampilkanDariFileTeks() {
        System.out.println("Daftar Buku dari file buku.txt:");
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_BUKU_TXT))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca dari file buku.txt.");
            e.printStackTrace();
        }
    }

    private static void tampilkanDariFileSerial() {
        System.out.println("Daftar Buku dari file buku.ser:");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_BUKU_SER))) {
            List<Buku> bukuList = (List<Buku>) ois.readObject();
            for (Buku buku : bukuList) {
                buku.tampilkanInfo();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Terjadi kesalahan saat membaca dari file buku.ser.");
            e.printStackTrace();
        }
    }
}

