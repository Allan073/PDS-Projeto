package br.ufrn.imd.cbm.controllers;

import br.ufrn.imd.cbm.annotations.AdminOnly;
import br.ufrn.imd.cbm.dtos.ProductDTO;
import br.ufrn.imd.cbm.models.Product;
import br.ufrn.imd.cbm.models.User;
import br.ufrn.imd.cbm.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @AdminOnly
    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody ProductDTO product) {
        productService.createProduct(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @AdminOnly
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId, @RequestBody ProductDTO ProductDTO) {
        Product product = productService.findProductById(productId, ProductDTO);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @AdminOnly
    @PostMapping("/{productId}")
    public ResponseEntity<String> updateProductById(@PathVariable Long productId, @RequestBody ProductDTO ProductDTO) {
        productService.updateProduct(productId,ProductDTO);
        return new ResponseEntity<>("Endereço com sucesso",HttpStatus.OK);
    }

    @AdminOnly
    @DeleteMapping("/{productId}") public ResponseEntity<String> deleteProductById(@PathVariable Long productId, @RequestBody ProductDTO ProductDTO) {
        productService.deleteProduct(productId,ProductDTO);
        return new ResponseEntity<>("Endereço com sucesso",HttpStatus.NO_CONTENT);
    }

    @AdminOnly
    @GetMapping("/allorder") public ResponseEntity<List<Product>> getAllOrderProducts(@RequestBody ProductDTO ProductDTO) {
        List<Product> products = productService.findAllOrderProducts(ProductDTO);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @AdminOnly
    @GetMapping("/alluser") public ResponseEntity<List<Product>> getAllUserProducts(@AuthenticationPrincipal User user) {
        List<Product> products = productService.findAllUserProducts(user);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @AdminOnly
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
}
