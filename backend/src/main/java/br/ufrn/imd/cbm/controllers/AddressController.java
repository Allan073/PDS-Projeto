package br.ufrn.imd.cbm.controllers;

import br.ufrn.imd.framework.annotations.AdminOnly;
import br.ufrn.imd.framework.annotations.AnyAuthed;
import br.ufrn.imd.framework.dtos.AddressDTO;
import br.ufrn.imd.framework.exceptions.InvalidArgumentException;
import br.ufrn.imd.framework.exceptions.NotFoundException;
import br.ufrn.imd.framework.models.Address;
import br.ufrn.imd.framework.models.User;
import br.ufrn.imd.framework.services.AddressService;
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
    @AnyAuthed
    @PostMapping
    public ResponseEntity<String> createAddress(@RequestBody AddressDTO address, @AuthenticationPrincipal User user) {
        try {
            addressService.createAddress(address,user);
            return new ResponseEntity<>("Endereço criado com sucesso!",HttpStatus.CREATED);
        } catch (InvalidArgumentException | NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @AnyAuthed
    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getAddressById(
            @PathVariable Long addressId,
            @AuthenticationPrincipal User user
    ) {
        try {
            Address address = addressService.findAddressById(addressId, user);
            return ResponseEntity.status(HttpStatus.OK).body(address);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @AnyAuthed
    @PutMapping("/{addressId}")
    public ResponseEntity<String> updateAddressById(@PathVariable Long addressId, @RequestBody AddressDTO address,
                                                    @AuthenticationPrincipal User user) {
        try {
            addressService.updateAddress(addressId,address, user);
            return new ResponseEntity<>("Endereço atualizado com sucesso",HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @AnyAuthed
    @DeleteMapping("/{addressId}") public ResponseEntity<String> deleteAddressById(@PathVariable Long addressId,
                                                                                   @AuthenticationPrincipal User user) {
        try {
            addressService.deleteAddress(addressId, user);
            return new ResponseEntity<>("Endereço apagado com sucesso",HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @AnyAuthed
    @GetMapping("/alluser") public ResponseEntity<List<Address>> getAllUserAddresses(@AuthenticationPrincipal User user) {
        System.out.println(user);
        List<Address> addresses = addressService.findAllUserAddresses(user);
        return ResponseEntity.status(HttpStatus.OK).body(addresses);
    }
    @AdminOnly
    @GetMapping("/all")
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressService.findAllAddresses();
        return ResponseEntity.status(HttpStatus.OK).body(addresses);
    }


}
