package com.ms.product.services.interfaces;

import com.ms.product.dto.ProductDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IProductService {
    ProductDTO createOrUpdate(ProductDTO productDTO);
    List<ProductDTO> listAll();
    ProductDTO getById(Long id);
    boolean deleteById(Long id);
    ProductDTO likeById (Long id, Boolean likeBool);
    ProductDTO dislikeById (Long id, Boolean likeBool);
}
