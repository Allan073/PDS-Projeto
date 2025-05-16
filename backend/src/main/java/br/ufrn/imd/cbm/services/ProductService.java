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
    @Autowired
    private UserService userService;
    public void createProduct(ProductDTO ProductDTO) {
        Product newProduct = Product.builder()
                .order(orderService.findOrderById(ProductDTO.orderid()))
                .name(ProductDTO.name())
                .description(ProductDTO.description())
                .quantity(ProductDTO.quantity())
                .price(ProductDTO.price())
                .productionDate(ProductDTO.productiondate())
                .build();
        productRepository.save(newProduct);
    }

    public Product findProductById(Long productId, ProductDTO ProductDTO) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
        if (orderService.findOrderById(ProductDTO.orderid()).equals(product.getOrder())) {
            return product;
        }
        else {
            throw new RuntimeException("Produto não pertence a pedido");
        }
    }


    public void updateProduct(Long productId, ProductDTO ProductDTO) {
        Product updatingproduct = findProductById(productId,ProductDTO);
        if (ProductDTO.orderid() != null) updatingproduct.setOrder(orderService.findOrderById(ProductDTO.orderid()));
        if (ProductDTO.name() != null) updatingproduct.setName(ProductDTO.name());
        if (ProductDTO.description() != null) updatingproduct.setDescription(ProductDTO.description());
        if (ProductDTO.quantity() != null) updatingproduct.setQuantity(ProductDTO.quantity());
        if (ProductDTO.price() != null) updatingproduct.setPrice(ProductDTO.price());
        if (ProductDTO.productiondate() != null) updatingproduct.setProductionDate(ProductDTO.productiondate());
        productRepository.save(updatingproduct);
    }

    public void deleteProduct(Long productId, ProductDTO ProductDTO) {
        Product product = findProductById(productId,ProductDTO);
        productRepository.deleteById(productId);
    }

    public List<Product> findAllOrderProducts(ProductDTO ProductDTO) {
        return productRepository.findByOrder_Id(ProductDTO.orderid())
                .orElseThrow(() -> new RuntimeException("Nenhuma receita encontrada!"));
    }

    public List<Product> findAllUserProducts(String username) {
        return productRepository.findByOrder_UserId(userService.findUserByEmail(username).getId())
                .orElseThrow(() -> new RuntimeException("Nenhuma receita encontrada!"));
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
