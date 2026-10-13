package com.example.library;

import com.example.library.entity.Book;
import com.example.library.exception.LibraryException;
import com.example.library.service.BookService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BookService bookService = new BookService();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> addBook();
                    case "2" -> deleteBook();
                    case "3" -> updateBook();
                    case "4" -> listBooks();
                    case "5" -> findBook();
                    case "6" -> borrowBook();
                    case "7" -> returnBook();
                    case "0" -> {
                        System.out.println("再见！");
                        return;
                    }
                    default -> System.out.println("无效选项，请重新输入。");
                }
            } catch (LibraryException e) {
                System.out.println("操作失败: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("系统异常: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== 图书管理系统 =====");
        System.out.println("1. 添加图书");
        System.out.println("2. 删除图书");
        System.out.println("3. 修改图书");
        System.out.println("4. 查询全部");
        System.out.println("5. 按 ISBN 查询");
        System.out.println("6. 借书");
        System.out.println("7. 还书");
        System.out.println("0. 退出");
        System.out.print("请选择: ");
    }

    private static void addBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();

        System.out.print("书名: ");
        String title = scanner.nextLine().trim();

        System.out.print("作者: ");
        String author = scanner.nextLine().trim();

        bookService.addBook(isbn, title, author);
        System.out.println("添加成功。");
    }

    private static void deleteBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();

        bookService.deleteBook(isbn);
        System.out.println("删除成功。");
    }

    private static void updateBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();

        System.out.print("新书名（不修改直接回车）: ");
        String title = scanner.nextLine().trim();

        System.out.print("新作者（不修改直接回车）: ");
        String author = scanner.nextLine().trim();

        bookService.updateBook(isbn, title, author);
        System.out.println("修改成功。");
    }

    private static void listBooks() {
        List<Book> books = bookService.findAll();

        if (books.isEmpty()) {
            System.out.println("暂无图书。");
            return;
        }

        books.forEach(System.out::println);
    }

    private static void findBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();

        System.out.println(bookService.findByIsbn(isbn));
    }

    private static void borrowBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();

        bookService.borrowBook(isbn);
        System.out.println("借书成功。");
    }

    private static void returnBook() {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();

        bookService.returnBook(isbn);
        System.out.println("还书成功。");
    }
}