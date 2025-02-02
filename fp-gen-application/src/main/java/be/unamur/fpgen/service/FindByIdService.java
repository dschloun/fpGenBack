package be.unamur.fpgen.service;

import be.unamur.fpgen.HasAuthor;

import java.util.UUID;

/**
 * Service interface to find a resource by its id.
 */
public interface FindByIdService {
    HasAuthor findById(UUID resourceId);
}
