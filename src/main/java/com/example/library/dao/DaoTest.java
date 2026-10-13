package com.example.library.dao;

import com.example.library.entity.Book;

import java.util.List;

public class DaoTest {
    public static void main(String[] args) throws Exception {
        BookDao dao = new BookDao();

        String testIsbn = "978-7-000-000";

        // 先清掉可能存在的脏数据，保证测试可重复跑
        dao.deleteByIsbn(testIsbn);

        // 1. 插入
        Book newBook = new Book(testIsbn, "重构", "Martin Fowler");
        dao.insert(newBook);
        System.out.println("插入完成");

        // 2. 查全部
        List<Book> all = dao.findAll();
        System.out.println("当前书籍总数: " + all.size());
        all.forEach(System.out::println);

        // 3. 借书
        dao.updateBorrowed(testIsbn, true);
        System.out.println("借书完成");

        Book afterBorrow = dao.findByIsbn(testIsbn);
        System.out.println("借后状态: " + afterBorrow);

        // 4. 删除
        dao.deleteByIsbn(testIsbn);
        System.out.println("删除完成");

        Book afterDelete = dao.findByIsbn(testIsbn);
        System.out.println("删除后查询结果: " + afterDelete);
    }
}