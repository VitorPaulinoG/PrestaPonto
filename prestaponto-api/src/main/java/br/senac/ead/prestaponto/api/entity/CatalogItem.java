package br.senac.ead.prestaponto.api.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_service")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatalogItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false, length = 500)
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false, length = 255)
    private String category;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private double price;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private User provider;

}
