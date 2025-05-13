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
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping
    public ResponseEntity<Void> createAddress(@RequestBody AddressDTO address) {
        System.out.println("createAddress");
        addressService.createAddress(address);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long addressId, @RequestBody AddressDTO AddressDTO) {
        Address address = addressService.findAddressById(addressId, AddressDTO);
        return ResponseEntity.status(HttpStatus.OK).body(address);
    }

    @PostMapping("/{addressId}")
    public ResponseEntity<String> updateAddressById(@PathVariable Long addressId, @RequestBody AddressDTO AddressDTO) {
        addressService.updateAddress(addressId,AddressDTO);
        return new ResponseEntity<>("Endereço atualizado com sucesso",HttpStatus.OK);
    }

    @DeleteMapping("/{addressId}") public ResponseEntity<String> deleteAddressById(@PathVariable Long addressId,
                                                                                   @RequestBody AddressDTO AddressDTO) {
        addressService.deleteAddress(addressId, AddressDTO);
        return new ResponseEntity<>("Endereço apagado com sucesso",HttpStatus.NO_CONTENT);
    }

    @GetMapping("/alluser") public ResponseEntity<List<Address>> getAllUserAddresses(@RequestBody AddressDTO AddressDTO) {
        List<Address> addresses = addressService.findAllUserAddresses(AddressDTO);
        return ResponseEntity.status(HttpStatus.OK).body(addresses);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressService.findAllAddresses();
        return ResponseEntity.status(HttpStatus.OK).body(addresses);
    }

}
