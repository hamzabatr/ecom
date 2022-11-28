package com.ms.product.services.implementations;

import com.ms.product.dto.ProductDTO;
import com.ms.product.mapper.IProductDTOMapper;
import com.ms.product.models.Product;
import com.ms.product.repositories.IProductRepository;
import com.ms.product.services.interfaces.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService{

    IProductRepository productRepository;
    IProductDTOMapper productDTOMapper;

    public ProductServiceImpl(IProductRepository productRepository, IProductDTOMapper productDTOMapper) {
        this.productRepository = productRepository;
        this.productDTOMapper = productDTOMapper;
    }

    @Override
    @Transactional
    public ProductDTO createOrUpdate(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getId()).orElseGet(Product::new);

        product = product.getId() == null ? productDTOMapper.productDTOToProduct(productDTO) : product;

        return productDTOMapper.productToProductDTO(productRepository.save(product));
    }
    @Override
    @Transactional
    public List<ProductDTO> listAll() {
        return productDTOMapper.productListToProductDTOList(productRepository.findAll());
    }

    @Override
    @Transactional(readOnly= true)
    public ProductDTO getById(Long id) {
        return productDTOMapper.productToProductDTO(
                productRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "couldnt find the product with id = " + id)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "couldnt find the specified product to delete"));

        productRepository.delete(product);
    }

}
