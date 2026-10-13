package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.LibraryStats;
import com.example.library.exception.BookAlreadyExistsException;
import com.example.library.exception.BookNotFoundException;
import com.example.library.exception.LibraryException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BookService {
    private final Map<String, Book> books = new LinkedHashMap<>();

    public void addBook(String isbn, String title, String author) {
        if (books.containsKey(isbn)) {
            throw new BookAlreadyExistsException(isbn);
        }
        books.put(isbn, new Book(isbn, title, author));
    }

    public void deleteBook(String isbn) {
        getBookOrThrow(isbn);
        books.remove(isbn);
    }

    public void updateBook(String isbn, String title, String author) {
        Book book = getBookOrThrow(isbn);

        if (title != null && !title.isBlank()) {
            book.setTitle(title);
        }

        if (author != null && !author.isBlank()) {
            book.setAuthor(author);
        }
    }

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }
    public List<Book> searchByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new LibraryException("搜索关键词不能为空");
        }

        String lower = keyword.toLowerCase();

        return books.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(lower)
                        || book.getAuthor().toLowerCase().contains(lower))
                .toList();
    }

    public List<Book> findByAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new LibraryException("作者名不能为空");
        }

        String lower = author.toLowerCase();

        return books.values().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(lower))
                .toList();
    }

    public LibraryStats getStats() {
        int total = books.size();

        int borrowed = (int) books.values().stream()
                .filter(Book::isBorrowed)
                .count();

        int available = total - borrowed;

        return new LibraryStats(total, borrowed, available);
    }

    public Book findByIsbn(String isbn) {
        return getBookOrThrow(isbn);
    }

    public void borrowBook(String isbn) {
        Book book = getBookOrThrow(isbn);

        if (book.isBorrowed()) {
            throw new LibraryException("图书已被借出: " + isbn);
        }

        book.setBorrowed(true);
    }

    public void returnBook(String isbn) {
        Book book = getBookOrThrow(isbn);

        if (!book.isBorrowed()) {
            throw new LibraryException("图书未被借出，无需归还: " + isbn);
        }

        book.setBorrowed(false);
    }

    private Book getBookOrThrow(String isbn) {
        Book book = books.get(isbn);

        if (book == null) {
            throw new BookNotFoundException(isbn);
        }

        return book;
    }
}