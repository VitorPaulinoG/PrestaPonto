package br.senac.ead.prestaponto.api.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.senac.ead.prestaponto.api.entity.CatalogItem;

public interface CatalogItemRepository extends JpaRepository<CatalogItem, UUID> {

    @Query("""
            SELECT c FROM CatalogItem c
            WHERE (:providerId IS NULL OR c.provider.id = :providerId)
              AND (:categoryPattern IS NULL OR c.category LIKE :categoryPattern)
              AND (:namePattern IS NULL OR c.name LIKE :namePattern)
              AND (:providerNamePattern IS NULL OR c.provider.name LIKE :providerNamePattern)
            """)
    Page<CatalogItem> findByFilter(
            @Param("providerId") UUID providerId,
            @Param("categoryPattern") String categoryPattern,
            @Param("namePattern") String namePattern,
            @Param("providerNamePattern") String providerNamePattern,
            Pageable pageable
    );
}
