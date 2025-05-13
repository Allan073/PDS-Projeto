package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.dtos.AddressDTO;
import br.ufrn.imd.cbm.models.Address;
import br.ufrn.imd.cbm.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private UserService userService;
    @Autowired
    private AddressRepository addressRepository;

    public void createAddress(AddressDTO AddressDTO) {
        Address newAddress = Address.builder()
                .user(userService.findUserById(AddressDTO.userId()))
                .street(AddressDTO.street())
                .number(AddressDTO.number())
                .complement(AddressDTO.complement())
                .build();
        addressRepository.save(newAddress);
    }

    public Address findAddressById(Long addressId, AddressDTO AddressDTO) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Endereço não encontrado!"));
        if (address.getUser().getId().equals(AddressDTO.userId())) {
            return address;
        }
        else {
            throw new RuntimeException("Endereço não pertence a usuário");
        }
    }

    public void updateAddress(Long addressId, AddressDTO AddressDTO) {
        Address updatingaddress = findAddressById(addressId, AddressDTO);
        if (AddressDTO.userId() != null) updatingaddress.setUser(userService.findUserById(AddressDTO.userId()));
        if (AddressDTO.street() != null) updatingaddress.setStreet(AddressDTO.street());
        if (AddressDTO.number() != null) updatingaddress.setNumber(AddressDTO.number());
        if (AddressDTO.complement() != null) updatingaddress.setComplement(AddressDTO.complement());
        addressRepository.save(updatingaddress);
    }

    public void deleteAddress(Long addressId, AddressDTO AddressDTO) {
        Address address = findAddressById(addressId, AddressDTO);
        addressRepository.deleteById(addressId);
    }

    public List<Address> findAllUserAddresses(AddressDTO AddressDTO) {
        return addressRepository.findByUser_Id(AddressDTO.userId())
                .orElseThrow(() -> new RuntimeException("Nenhuma receita encontrada!"));
    }

    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }
}
