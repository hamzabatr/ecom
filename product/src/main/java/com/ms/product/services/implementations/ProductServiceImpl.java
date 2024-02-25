package com.ms.product.services.implementations;

import com.ms.product.dto.ProductDTO;
import com.ms.product.mapper.IProductDTOMapper;
import com.ms.product.entities.Product;
import com.ms.product.repositories.IProductRepository;
import com.ms.product.services.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final IProductRepository productRepository;
    private final IProductDTOMapper productDTOMapper;

    @Override
    @Transactional
    public ProductDTO createOrUpdate(ProductDTO productDTO) {
        Product product = productDTO.getId() == null ?
                new Product() :
                productRepository.findById(productDTO.getId())
                        .orElseGet(Product::new);

        product = product.getId() == null ? productDTOMapper.productDTOToProduct(productDTO) : product;

        return productDTOMapper.productToProductDTO(productRepository.save(product));
    }

    @Override
    @Transactional
    public List<ProductDTO> listAll() {
        return productDTOMapper.productListToProductDTOList(productRepository.findAll());

    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getById(Long id) {
        return productDTOMapper.productToProductDTO(
                productRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "couldnt find the product with id = " + id)));
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "couldnt find the specified product to delete"));

        productRepository.delete(product);
        return true;
    }

    @Override
    @Transactional
    public ProductDTO likeById (Long id, Boolean likeBool){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "couldnt find the product with id = " + id));
        product.setLikes(likeBool ? product.getLikes() + 1 : (product.getLikes() > 0 ? product.getLikes() - 1 : 0));

        return productDTOMapper.productToProductDTO(productRepository.save(product));
    }


    @Override
    @Transactional
    public ProductDTO dislikeById (Long id, Boolean likeBool){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "couldnt find the product with id = " + id));
        product.setDislikes(likeBool ? product.getDislikes() + 1 : (product.getDislikes() > 0 ? product.getDislikes() - 1 : 0));
        return productDTOMapper.productToProductDTO(productRepository.save(product));
    }

}
