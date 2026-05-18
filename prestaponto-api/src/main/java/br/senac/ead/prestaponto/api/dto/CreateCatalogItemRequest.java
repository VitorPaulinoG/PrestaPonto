package br.senac.ead.prestaponto.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CreateCatalogItemRequest {

    @NotBlank(message = "O campo 'name' é obrigatório.")
    @Size(max = 500, min = 3, message = "O campo 'name' deve conter entre 3 e 500 caracteres.")
    private String name;

    @Size(max = 1000, message = "O campo 'description' deve conter no máximo 1000 caracteres.")
    private String description;
    
    @NotBlank(message = "O campo 'category' é obrigatório.")
    @Size(max = 255, message = "O campo 'category' deve conter no máximo 255 caracteres.")
    private String category;

    @NotNull(message = "O campo 'price' é obrigatório.")
    private double price;
}
