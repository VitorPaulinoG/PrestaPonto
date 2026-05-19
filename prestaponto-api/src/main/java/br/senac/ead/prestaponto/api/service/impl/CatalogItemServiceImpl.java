package br.senac.ead.prestaponto.api.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import br.senac.ead.prestaponto.api.entity.CatalogItem;
import br.senac.ead.prestaponto.api.entity.User;
import br.senac.ead.prestaponto.api.exception.EntityNotFoundException;
import br.senac.ead.prestaponto.api.repository.CatalogItemRepository;
import br.senac.ead.prestaponto.api.service.CatalogItemService;

@Service
public class CatalogItemServiceImpl implements CatalogItemService {

    private final CatalogItemRepository repository;

    public CatalogItemServiceImpl(CatalogItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public CatalogItem register(CatalogItem catalogItem) {
        return repository.save(catalogItem);
    }

    @Override
    public CatalogItem findById(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format("O item de catálogo com ID = %s não foi encontrado.", id)));
    }

    @Override
    public Page<CatalogItem> findByFilter(UUID providerId, String category, String name, String providerName, Pageable pageable) {
        if (providerId == null && (category == null || category.isBlank())
            && (name == null || name.isBlank()) && (providerName == null || providerName.isBlank())) {
            return repository.findAll(pageable);
        }

        var categoryPattern = (category == null || category.isBlank()) ? null : "%" + category + "%";
        var namePattern = (name == null || name.isBlank()) ? null : "%" + name + "%";
        var providerNamePattern = (providerName == null || providerName.isBlank()) ? null : "%" + providerName + "%";
        return repository.findByFilter(providerId, categoryPattern, namePattern, providerNamePattern, pageable);
    }

    @Override
    public void deleteById(UUID id, User loggedUser) {
        var catalogItem =
            repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("O item de catálogo não foi encontrado"));

        if (catalogItem.getProvider().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("O usuário não tem permissão para deletar este item do catálogo");
        }

        repository.delete(catalogItem);
    }

    @Override
    public void update(UUID id, CatalogItem catalogItem, User loggedUser) {
        var catalogItemSaved =
            repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("O item de catálogo não foi encontrado"));

        if (catalogItemSaved.getProvider().getId() != loggedUser.getId()) {
            throw new AccessDeniedException("O usuário não tem permissão para editar este item do catálogo");
        }

        catalogItemSaved.setName(catalogItem.getName());
        catalogItemSaved.setDescription(catalogItem.getDescription());
        catalogItemSaved.setCategory(catalogItem.getCategory());
        catalogItemSaved.setPrice(catalogItem.getPrice());

        repository.save(catalogItemSaved);
    }
}
