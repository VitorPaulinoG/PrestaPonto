package br.senac.ead.prestaponto.api.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.senac.ead.prestaponto.api.entity.CatalogItem;

public interface CatalogItemRepository extends JpaRepository<CatalogItem, UUID> {
    Page<CatalogItem> findByProviderIdAndCategory(UUID providerId, String category, Pageable pageable);
}
