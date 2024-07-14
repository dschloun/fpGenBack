package be.unamur.fpgen.repository;

import be.unamur.fpgen.author.Author;

import java.util.UUID;

public interface AuthorRepository {

    Author saveAuthor(Author author);

    Author updateAuthor(Author author);

    Author getAuthorById(UUID authorId);
}
