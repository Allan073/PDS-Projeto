package br.ufrn.imd.cbm.controllers;

import br.ufrn.imd.cbm.dtos.AddressDTO;
import br.ufrn.imd.cbm.models.Address;
import br.ufrn.imd.cbm.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<Void> createAdress(@PathVariable Long userId, @RequestBody AddressDTO address) {
        addressService.createAddress(userId,address);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long userId, @PathVariable Long addressId) {
        Address address = addressService.findAddressById(userId, addressId);
        return ResponseEntity.status(HttpStatus.OK).body(address);
    }

    @PostMapping("/{addressId}")
    public ResponseEntity<String> updateAddressById(@PathVariable Long userId, @PathVariable Long addressId, @RequestBody AddressDTO AddressDTO) {
        addressService.updateAddress(userId,addressId,AddressDTO);
        return new ResponseEntity<>("Endereço com sucesso",HttpStatus.OK);
    }

    @DeleteMapping("/{addressId}") public ResponseEntity<String> deleteAddressById(@PathVariable Long userId, @PathVariable Long addressId) {
        addressService.deleteAddress(userId,addressId);
        return new ResponseEntity<>("Endereço com sucesso",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all") public ResponseEntity<List<Address>> getAllUserAddresses(@PathVariable Long userId) {
        List<Address> addresses = addressService.findAllUserAddresses(userId);
        return ResponseEntity.status(HttpStatus.OK).body(addresses);
    }

    @RequestMapping("/address")
    @GetMapping("/all")
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressService.findAllAddresses();
        return ResponseEntity.status(HttpStatus.OK).body(addresses);
    }

}
