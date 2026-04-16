package com.jonatan.libraryapi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.jonatan.libraryapi.entity.Author;
import com.jonatan.libraryapi.entity.Book;
import com.jonatan.libraryapi.repository.AuthorRepository;
import com.jonatan.libraryapi.repository.BookRepository;

@Profile("!test")
@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(AuthorRepository authorRepository, BookRepository bookRepository) {
        return args -> {

            // För att undvika duplicates vid restart
            if (authorRepository.count() > 0) return;

            Author author = new Author();
            author.setName("George Orwell");
            author = authorRepository.save(author);

            Book book1 = new Book();
            book1.setTitle("1984");
            book1.setIsbn("9780451524935");
            book1.setPublicationYear(1949);
            book1.setAuthor(author);

            Book book2 = new Book();
            book2.setTitle("Animal Farm");
            book2.setIsbn("9780451526342");
            book2.setPublicationYear(1945);
            book2.setAuthor(author);

            bookRepository.save(book1);
            bookRepository.save(book2);
        };
    }
}