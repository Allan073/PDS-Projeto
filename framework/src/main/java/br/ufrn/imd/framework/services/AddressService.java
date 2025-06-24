package br.ufrn.imd.framework.services;

import br.ufrn.imd.framework.dtos.AddressDTO;
import br.ufrn.imd.framework.exceptions.InvalidArgumentException;
import br.ufrn.imd.framework.exceptions.NotFoundException;
import br.ufrn.imd.framework.models.Address;
import br.ufrn.imd.framework.models.User;
import br.ufrn.imd.framework.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AddressService {
    @Autowired
    private UserService userService;
    @Autowired
    private AddressRepository addressRepository;

    public void createAddress(AddressDTO AddressDTO, User user) throws InvalidArgumentException, NotFoundException {
        if (AddressDTO.street() == null || AddressDTO.street().isEmpty()) {
            throw new InvalidArgumentException("Rua vazia!");
        }
        try {
            Address newAddress = Address.builder()
                    .user(userService.findUserById(user.getId()))
                    .street(AddressDTO.street())
                    .number(AddressDTO.number())
                    .complement(AddressDTO.complement())
                    .build();
            addressRepository.save(newAddress);
        } catch (NotFoundException e) {
            throw e;
        }
    }

    public Address findAddressById(Long addressId, User user) throws NotFoundException {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new RuntimeException("Endereço não encontrado!"));
        if (user.isAdmin() || address.getUser().getId().equals(user.getId())) {
            return address;
        }
        else {
            throw new NotFoundException("Endereço não pertence a usuário");
        }
    }

    public void updateAddress(Long addressId, AddressDTO AddressDTO, User user) throws NotFoundException {
        try {
            Address updatingaddress = findAddressById(addressId, user);
            if (AddressDTO.street() != null) updatingaddress.setStreet(AddressDTO.street());
            if (AddressDTO.number() != null) updatingaddress.setNumber(AddressDTO.number());
            if (AddressDTO.complement() != null) updatingaddress.setComplement(AddressDTO.complement());
            addressRepository.save(updatingaddress);
        }
        catch (NotFoundException e) {
            throw e;
        }
    }

    public void deleteAddress(Long addressId, User user) throws NotFoundException {
        try {
            findAddressById(addressId, user);
            addressRepository.deleteById(addressId);
        } catch (NotFoundException e) {
            throw e;
        }
    }

    public List<Address> findAllUserAddresses(User user) {
        return addressRepository.findByUser_Id(user.getId());
    }

    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }
}
