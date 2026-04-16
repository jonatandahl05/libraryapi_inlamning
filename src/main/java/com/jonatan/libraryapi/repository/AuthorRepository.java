package com.jonatan.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jonatan.libraryapi.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    
}
