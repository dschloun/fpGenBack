package be.unamur.fpgen.service;

import be.unamur.fpgen.HasAuthor;

import java.util.UUID;

public interface FindByIdService {
    HasAuthor findById(UUID resourceId);
}
