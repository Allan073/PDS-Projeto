package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.dtos.AddressDTO;
import br.ufrn.imd.cbm.models.Address;
import br.ufrn.imd.cbm.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private UserService userService;
    @Autowired AddressRepository addressRepository;

    public void createAddress(Long userId, AddressDTO AddressDTO) {
        Address newAddress = Address.builder()
                .user(userService.findUserById(userId))
                .street(AddressDTO.street())
                .number(AddressDTO.number())
                .complement(AddressDTO.complement())
                .build();
        addressRepository.save(newAddress);
    }

    public Address findAddressById(Long userId, Long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Endereço não encontrado!"));
        if (address.getUser().getId().equals(userId)) {
            return address;
        }
        else {
            throw new RuntimeException("Endereço não pertence a usuário");
        }
    }

    public void updateAddress(Long userId, Long addressId, AddressDTO AddressDTO) {
        Address updatingaddress = findAddressById(userId,addressId);
        if (AddressDTO.street() != null) updatingaddress.setStreet(AddressDTO.street());
        if (AddressDTO.number() != null) updatingaddress.setNumber(AddressDTO.number());
        if (AddressDTO.complement() != null) updatingaddress.setComplement(AddressDTO.complement());
        addressRepository.save(updatingaddress);
    }

    public void deleteAddress(Long userId, Long addressId) {
        Address address = findAddressById(userId, addressId);
        addressRepository.deleteById(addressId);
    }

    public List<Address> findAllUserAddresses(Long userId) {
        return addressRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Nenhuma receita encontrada!"));
    }

    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }
}
