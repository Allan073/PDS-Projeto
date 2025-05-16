package br.ufrn.imd.cbm.services;

import br.ufrn.imd.cbm.dtos.AddressDTO;
import br.ufrn.imd.cbm.models.Address;
import br.ufrn.imd.cbm.models.User;
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

    public void createAddress(AddressDTO AddressDTO, String username) {
        Address newAddress = Address.builder()
                .user(userService.findUserByEmail(username))
                .street(AddressDTO.street())
                .number(AddressDTO.number())
                .complement(AddressDTO.complement())
                .build();
        addressRepository.save(newAddress);
    }

    public Address findAddressById(Long addressId, String username) {
        User user = userService.findUserByEmail(username);
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Endereço não encontrado!"));
        if (user.isAdmin() || address.getUser().getId().equals(user.getId())) {
            return address;
        }
        else {
            throw new RuntimeException("Endereço não pertence a usuário");
        }
    }

    public void updateAddress(Long addressId, AddressDTO AddressDTO, String username) {
        Address updatingaddress = findAddressById(addressId, username);
        if (AddressDTO.street() != null) updatingaddress.setStreet(AddressDTO.street());
        if (AddressDTO.number() != null) updatingaddress.setNumber(AddressDTO.number());
        if (AddressDTO.complement() != null) updatingaddress.setComplement(AddressDTO.complement());
        addressRepository.save(updatingaddress);
    }

    public void deleteAddress(Long addressId, String username) {
        findAddressById(addressId, username);
        addressRepository.deleteById(addressId);
    }

    public List<Address> findAllUserAddresses(String username) {
        return addressRepository.findByUser_Id(userService.findUserByEmail(username).getId())
                .orElseThrow(() -> new RuntimeException("Nenhuma receita encontrada!"));
    }

    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }
}
