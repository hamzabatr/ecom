package com.ms.product.config;

import com.ms.product.entities.Product;
import com.ms.product.repositories.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductItemWriter implements ItemWriter<Product> {
    private final IProductRepository productRepository;

    @Override
    public void write(@NonNull Chunk<? extends Product> chunk)  {
        productRepository.saveAll(chunk);
    }
}
