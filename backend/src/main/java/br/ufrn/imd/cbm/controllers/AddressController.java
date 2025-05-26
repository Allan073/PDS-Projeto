package br.ufrn.imd.cbm.controllers;

import br.ufrn.imd.cbm.annotations.AdminOnly;
import br.ufrn.imd.cbm.annotations.AnyAuthed;
import br.ufrn.imd.cbm.dtos.AddressDTO;
import br.ufrn.imd.cbm.models.Address;
import br.ufrn.imd.cbm.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<Void> createAddress(@RequestBody AddressDTO address, @AuthenticationPrincipal String username) {
        addressService.createAddress(address,username);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @AnyAuthed
    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getAddressById(
            @PathVariable Long addressId,
            @AuthenticationPrincipal String username
    ) {
        Address address = addressService.findAddressById(addressId, username);
        return ResponseEntity.status(HttpStatus.OK).body(address);
    }

    @PostMapping("/{addressId}")
    public ResponseEntity<String> updateAddressById(@PathVariable Long addressId, @RequestBody AddressDTO address,
                                                    @AuthenticationPrincipal String username) {
        addressService.updateAddress(addressId,address, username);
        return new ResponseEntity<>("Endereço atualizado com sucesso",HttpStatus.OK);
    }

    @DeleteMapping("/{addressId}") public ResponseEntity<String> deleteAddressById(@PathVariable Long addressId,
                                                                                   @AuthenticationPrincipal String username) {
        addressService.deleteAddress(addressId, username);
        return new ResponseEntity<>("Endereço apagado com sucesso",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/alluser") public ResponseEntity<List<Address>> getAllUserAddresses(@AuthenticationPrincipal String username) {
        List<Address> addresses = addressService.findAllUserAddresses(username);
        return ResponseEntity.status(HttpStatus.OK).body(addresses);
    }
    @AdminOnly
    @GetMapping("/all")
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressService.findAllAddresses();
        return ResponseEntity.status(HttpStatus.OK).body(addresses);
    }

    @GetMapping("/ru")
    public ResponseEntity<String> testUser(@AuthenticationPrincipal String username) {
        return new ResponseEntity<>(username,HttpStatus.OK);
    }
}
