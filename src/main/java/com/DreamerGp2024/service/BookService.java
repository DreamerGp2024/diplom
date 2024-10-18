package com.DreamerGp2024.service;

import java.util.List;

import com.DreamerGp2024.model.Book;
import com.DreamerGp2024.model.StoreException;

public interface BookService {

    public Book getBookById(String bookId) throws StoreException;

    public List<Book> getAllBooks() throws StoreException;

    public List<Book> getBooksByCommaSeperatedBookIds(String commaSeperatedBookIds) throws StoreException;

    public String deleteBookById(String bookId) throws StoreException;

    public String addBook(Book book) throws StoreException;

    public String updateBookQtyById(String bookId, int quantity) throws StoreException;
    
    public String updateBook(Book book) throws StoreException;

}
