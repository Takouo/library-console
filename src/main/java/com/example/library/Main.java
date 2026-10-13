package com.example.library;

import com.example.library.entity.Book;
import com.example.library.exception.LibraryException;
import com.example.library.service.BookService;
import com.example.library.entity.LibraryStats;

import java.sql.SQLException;
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
                    case "8" -> searchBooks();
                    case "9" -> findBooksByAuthor();
                    case "10" -> showStats();
                    case "0" -> {
                        System.out.println("再见！");
                        return;
                    }
                    default -> System.out.println("无效选项，请重新输入。");
                }

            } catch (LibraryException e) {
                System.out.println("操作失败: " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("数据库异常: " + e.getMessage());
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
        System.out.println("8. 模糊搜索（书名/作者）");
        System.out.println("9. 按作者查询");
        System.out.println("10. 统计信息");
        System.out.println("0. 退出");
        System.out.print("请选择: ");
    }

    private static void addBook() throws SQLException {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();

        System.out.print("书名: ");
        String title = scanner.nextLine().trim();

        System.out.print("作者: ");
        String author = scanner.nextLine().trim();

        bookService.addBook(isbn, title, author);
        System.out.println("添加成功。");
    }

    private static void deleteBook() throws SQLException {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();

        bookService.deleteBook(isbn);
        System.out.println("删除成功。");
    }

    private static void updateBook() throws SQLException {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();

        System.out.print("新书名（不修改直接回车）: ");
        String title = scanner.nextLine().trim();

        System.out.print("新作者（不修改直接回车）: ");
        String author = scanner.nextLine().trim();

        bookService.updateBook(isbn, title, author);
        System.out.println("修改成功。");
    }

    private static void listBooks() throws SQLException {
        printBooks(bookService.findAll(), "暂无图书。");
    }

    private static void findBook() throws SQLException {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();

        System.out.println(bookService.findByIsbn(isbn));
    }

    private static void borrowBook() throws SQLException {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();

        bookService.borrowBook(isbn);
        System.out.println("借书成功。");
    }

    private static void returnBook() throws SQLException {
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();

        bookService.returnBook(isbn);
        System.out.println("还书成功。");
    }

    private static void searchBooks() throws SQLException {
        System.out.print("关键词（书名或作者）: ");
        String keyword = scanner.nextLine().trim();

        List<Book> result = bookService.searchByKeyword(keyword);
        printBooks(result, "没有匹配的图书。");
    }

    private static void findBooksByAuthor() throws SQLException {
        System.out.print("作者名: ");
        String author = scanner.nextLine().trim();

        List<Book> result = bookService.findByAuthor(author);
        printBooks(result, "没有该作者的图书。");
    }

    private static void showStats() throws SQLException {
        LibraryStats stats = bookService.getStats();
        System.out.println(stats);
    }

    private static void printBooks(List<Book> books, String emptyMessage) {
        if (books.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }

        books.forEach(System.out::println);
}
}