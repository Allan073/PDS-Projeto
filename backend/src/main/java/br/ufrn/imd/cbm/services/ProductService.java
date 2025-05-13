package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.dtos.ProductDTO;
import br.ufrn.imd.cbm.models.Product;
import br.ufrn.imd.cbm.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderService orderService;

    public void createProduct(Long userId, Long orderId, ProductDTO ProductDTO) {
        Product newProduct = Product.builder()
                .order(orderService.findOrderById(userId,orderId))
                .name(ProductDTO.name())
                .description(ProductDTO.description())
                .quantity(ProductDTO.quantity())
                .price(ProductDTO.price())
                .productionDate(ProductDTO.productionDate())
                .build();
        productRepository.save(newProduct);
    }

    public Product findProductById(Long userId, Long orderId, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Endereço não encontrado!"));
        if (orderService.findOrderById(userId,orderId).equals(product.getOrder())) {
            return product;
        }
        else {
            throw new RuntimeException("Endereço não pertence a usuário");
        }
    }

    public void updateProduct(Long userId, Long orderId, Long productId, ProductDTO ProductDTO) {
        Product updatingproduct = findProductById(userId,orderId,productId);
        if (ProductDTO.name() != null) updatingproduct.setName(ProductDTO.name());
        if (ProductDTO.description() != null) updatingproduct.setDescription(ProductDTO.description());
        if (ProductDTO.quantity() != null) updatingproduct.setQuantity(ProductDTO.quantity());
        if (ProductDTO.price() != null) updatingproduct.setPrice(ProductDTO.price());
        if (ProductDTO.productionDate() != null) updatingproduct.setProductionDate(ProductDTO.productionDate());
        productRepository.save(updatingproduct);
    }

    public void deleteProduct(Long userId, Long orderId, Long productId) {
        Product product = findProductById(userId, orderId, productId);
        productRepository.deleteById(productId);
    }

    public List<Product> findAllOrderProducts(Long userId, Long orderId) {
        return productRepository.findByOrder_Id(orderId)
                .orElseThrow(() -> new RuntimeException("Nenhuma receita encontrada!"));
    }

    public List<Product> findAllUserProducts(Long userId) {
        return productRepository.findByOrder_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Nenhuma receita encontrada!"));
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
