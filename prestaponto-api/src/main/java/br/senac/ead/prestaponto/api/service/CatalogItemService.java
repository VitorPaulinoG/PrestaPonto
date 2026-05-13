package br.senac.ead.prestaponto.api.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.senac.ead.prestaponto.api.entity.CatalogItem;

public interface CatalogItemService {
    CatalogItem register(CatalogItem catalogItem);

    CatalogItem findById(UUID id);

    Page<CatalogItem> findByProviderIdAndCategory(UUID providerId, String category, Pageable pageable);

    void deleteById(UUID id);

    void update(UUID id, CatalogItem catalogItem);
}
