package br.ufrn.imd.cbm.controllers;

import br.ufrn.imd.cbm.dtos.ProductDTO;
import br.ufrn.imd.cbm.models.Product;
import br.ufrn.imd.cbm.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/orders/{orderId}/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Void> createProduct(@PathVariable Long userId, @PathVariable Long orderId,
                                              @RequestBody ProductDTO product) {
        productService.createProduct(userId,orderId,product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long userId, @PathVariable Long orderId,
                                                  @PathVariable Long productId) {
        Product product = productService.findProductById(userId,orderId, productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> updateProductById(@PathVariable Long userId, @PathVariable Long orderId,
                                                    @PathVariable Long productId, @RequestBody ProductDTO ProductDTO) {
        productService.updateProduct(userId,orderId,productId,ProductDTO);
        return new ResponseEntity<>("Endereço com sucesso",HttpStatus.OK);
    }

    @DeleteMapping("/{productId}") public ResponseEntity<String> deleteProductById(@PathVariable Long userId,
                                                                                   @PathVariable Long orderId,
                                                                                   @PathVariable Long productId) {
        productService.deleteProduct(userId,orderId,productId);
        return new ResponseEntity<>("Endereço com sucesso",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all") public ResponseEntity<List<Product>> getAllOrderProducts(@PathVariable Long userId, @PathVariable Long orderId) {
        List<Product> products = productService.findAllOrderProducts(userId,orderId);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    /*@RequestMapping("/users/{userId}/products")
    @GetMapping("/all") public ResponseEntity<List<Product>> getAllUserProducts(@PathVariable Long userId) {
        List<Product> products = productService.findAllUserProducts(userId);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @RequestMapping("/products")//talvez esteja quebrado porque tem um requestmapping la encima se for o caso jogo em
    //outra classe depois
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }*/
}
