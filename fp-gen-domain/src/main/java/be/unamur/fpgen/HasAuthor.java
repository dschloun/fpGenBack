package be.unamur.fpgen;

import be.unamur.fpgen.author.Author;

/**
 * Interface used to check the presence of an author in a dataset, project and so on
 */
public interface HasAuthor {

    Author getAuthor();
}
