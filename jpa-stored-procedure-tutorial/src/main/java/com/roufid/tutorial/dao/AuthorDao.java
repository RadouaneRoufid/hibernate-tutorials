package com.roufid.tutorial.dao;

import org.springframework.data.repository.CrudRepository;

import com.roufid.tutorial.entity.Author;

public interface AuthorDao extends CrudRepository<Author, Integer> {

}
