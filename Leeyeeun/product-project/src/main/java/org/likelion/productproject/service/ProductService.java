package org.likelion.productproject.service;

import lombok.RequiredArgsConstructor;
import org.likelion.productproject.domain.Product;
import org.likelion.productproject.domain.ProductReposityory;
import org.likelion.productproject.dto.ProductResponseDto;
import org.likelion.productproject.dto.ProductSaveRequestDto;
import org.likelion.productproject.dto.ProductUpdateRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.LoggingPermission;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductReposityory productReposityory;

    private static Long sequence = 0L;

    public Product saveProduct(ProductSaveRequestDto requestDto) {
        Product product = Product.builder()
                .id(++sequence)
                .productId(requestDto.getProductId())
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .build();

        return productReposityory.save(product);
    }

    public ProductResponseDto findProductById(Long id) {
        Product product = productReposityory.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당하는 상품이 없습니다. id = " + id));

        return ProductResponseDto.from(product);
    }

    public List<ProductResponseDto> findAllProduct() {
        return productReposityory.findAll().stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }

    public Product updateProductById(Long id, ProductUpdateRequestDto requestDto) {
        Product product = productReposityory.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당하는 상품이 없습니다. id = " + id));

        product.update(requestDto.getName(), requestDto.getPrice());

        return productReposityory.save(product);
    }

    public void deleteProductById(Long id) {
        productReposityory.deleteById(id);
    }
}
