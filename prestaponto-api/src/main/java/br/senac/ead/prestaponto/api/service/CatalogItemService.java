package br.senac.ead.prestaponto.api.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.senac.ead.prestaponto.api.entity.CatalogItem;
import br.senac.ead.prestaponto.api.entity.User;

public interface CatalogItemService {
    CatalogItem register(CatalogItem catalogItem);

    CatalogItem findById(UUID id);

    Page<CatalogItem> findByFilter(UUID providerId, String category, String name, String providerName, Pageable pageable);

    void deleteById(UUID id, User loggedUser);

    void update(UUID id, CatalogItem catalogItem, User loggedUser);
}
