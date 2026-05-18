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
    public Page<CatalogItem> findByFilter(UUID providerId, String category, Pageable pageable) {
        if (providerId != null && (category != null && !category.isBlank())) 
            return repository.findByProviderIdAndCategory(providerId, category, pageable);
        if (providerId != null)
            return repository.findByProviderId(providerId, pageable);
        if (category != null && !category.isBlank())
            return repository.findByCategory(category, pageable);
        
        return repository.findAll(pageable);
    }

    @Override
    public void deleteById(UUID id, User loggedUser) {
        var catalogItem = findById(id);

        verifyProperty(catalogItem, loggedUser);
        
        repository.delete(catalogItem);
    }

    @Override
    public void update(UUID id, CatalogItem catalogItem, User loggedUser) {
        var existingItem = findById(id);
        verifyProperty(existingItem, loggedUser);

        existingItem.setName(catalogItem.getName());
        existingItem.setDescription(catalogItem.getDescription());
        existingItem.setCategory(catalogItem.getCategory());
        existingItem.setPrice(catalogItem.getPrice());

        repository.save(existingItem);
    }

    private void verifyProperty(CatalogItem existingEntity, User loggedUser) {
        if (!existingEntity.getProvider().getId().equals(loggedUser.getId()))
            throw new AccessDeniedException("O usuário não possui permissão para essa operação.");
    }


}
