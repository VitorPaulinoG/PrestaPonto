package br.senac.ead.prestaponto.api.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.senac.ead.prestaponto.api.entity.CatalogItem;
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
    public Page<CatalogItem> findByProviderIdAndCategory(UUID providerId, String category, Pageable pageable) {
        return repository.findByProviderIdAndCategory(providerId, category, pageable);
    }

    @Override
    public void deleteById(UUID id) {
        var catalogItem = findById(id);
        repository.delete(catalogItem);
    }

    @Override
    public void update(UUID id, CatalogItem catalogItem) {
        var existingItem = findById(id);
        existingItem.setName(catalogItem.getName());
        existingItem.setDescription(catalogItem.getDescription());
        existingItem.setCategory(catalogItem.getCategory());
        existingItem.setPrice(catalogItem.getPrice());

        repository.save(existingItem);
    }


}
