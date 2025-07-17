package br.ufrn.imd.sbm.services;

import br.ufrn.imd.framework.exceptions.InvalidArgumentException;
import br.ufrn.imd.framework.exceptions.NotFoundException;
import br.ufrn.imd.framework.models.Item;
import br.ufrn.imd.framework.services.ItemService;
import br.ufrn.imd.sbm.dtos.SubscriptionTypeDTO;
import br.ufrn.imd.sbm.models.SubscriptionType;
import br.ufrn.imd.sbm.repositories.SubscriptionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionTypeService {
    @Autowired
    private SubscriptionTypeRepository subscriptionTypeRepository;
    @Autowired
    private ItemService itemService;

    public void createSubscriptionType(SubscriptionTypeDTO subscriptionTypeDTO) throws NotFoundException, InvalidArgumentException {
        if (subscriptionTypeDTO == null) {
            throw new InvalidArgumentException("Corpo do request nulo!");
        }
        if (subscriptionTypeDTO.name() == null || subscriptionTypeDTO.name().isEmpty()) {
            throw new InvalidArgumentException("Tipos de assinatura devem possuir nome!");
        }
        try {
            List<Item> items = itemService.findAllById(subscriptionTypeDTO.items());
            SubscriptionType subscriptionType = SubscriptionType.builder()
                    .name(subscriptionTypeDTO.name())
                    .description(subscriptionTypeDTO.description())
                    .items(items)
                    .price(subscriptionTypeDTO.price())
                    .frequency(subscriptionTypeDTO.frequency())
                    .build();
            subscriptionTypeRepository.save(subscriptionType);
        }
        catch (NotFoundException e) {
            throw e;
        }
    }
    public SubscriptionType findSubscriptionTypeById(Long id) throws NotFoundException {
        return subscriptionTypeRepository.findById(id).orElseThrow(() -> new NotFoundException("Assinatura não encontrada!"));
    }

    public void updateSubscriptionType(Long id, SubscriptionTypeDTO subscriptionTypeDTO) throws NotFoundException {
        try {
            SubscriptionType updatingSubscriptionType = findSubscriptionTypeById(id);
            if (subscriptionTypeDTO.name() != null)
                updatingSubscriptionType.setName(subscriptionTypeDTO.name());
            if (subscriptionTypeDTO.description() != null)
                updatingSubscriptionType.setDescription(subscriptionTypeDTO.description());
            if (subscriptionTypeDTO.price() != null && subscriptionTypeDTO.price()>=0)
                updatingSubscriptionType.setPrice(subscriptionTypeDTO.price());
            if (subscriptionTypeDTO.frequency() != null && subscriptionTypeDTO.frequency()>=1)
                updatingSubscriptionType.setFrequency(subscriptionTypeDTO.frequency());
        } catch (NotFoundException e) {
            throw e;
        }

    }
    public void deleteSubscriptionType(Long id) throws NotFoundException {
        try {
            SubscriptionType subscriptionType = findSubscriptionTypeById(id);
            subscriptionTypeRepository.delete(subscriptionType);
        } catch (NotFoundException e) {
            throw e;
        }
    }
    public List<SubscriptionType> findAllSubscriptionTypes() {
        return subscriptionTypeRepository.findAll();
    }
}
