package com.ms.product.services.interfaces;

import com.ms.product.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IProductService {
    ProductDTO createOrUpdate(ProductDTO productDTO);
    List<ProductDTO> listAll();
    ProductDTO getById(Long id);
    void deleteById(Long id);
}
