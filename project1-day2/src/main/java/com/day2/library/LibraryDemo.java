package com.day2.library;


public class LibraryDemo {
    public static void main(String[] args) {
        FictionBook fiction = new FictionBook("The Hobbit", "J.R.R. Tolkien", "978-0-261-10221-4", "Fantasy");
        NonFictionBook nonfiction = new NonFictionBook("A Brief History of Time", "Stephen Hawking", "978-0-553-17698-8", "Science");

        System.out.println("=== Fiction Book ===");
        fiction.displayInfo();

        System.out.println("\n=== Non-Fiction Book ===");
        nonfiction.displayInfo();
    }
}