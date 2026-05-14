package br.senac.ead.prestaponto.api.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCatalogItemResponse {
    private UUID id;
    private String name;
    private String description;
    private String category;
    private double price;
    private GetProviderResponse provider;
}
