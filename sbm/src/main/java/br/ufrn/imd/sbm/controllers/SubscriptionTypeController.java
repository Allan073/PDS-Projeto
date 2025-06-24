package br.ufrn.imd.sbm.controllers;

import br.ufrn.imd.framework.annotations.AdminOnly;
import br.ufrn.imd.framework.annotations.AnyAuthed;
import br.ufrn.imd.framework.exceptions.InvalidArgumentException;
import br.ufrn.imd.framework.exceptions.NotFoundException;
import br.ufrn.imd.sbm.models.SubscriptionType;
import br.ufrn.imd.sbm.services.SubscriptionTypeService;
import br.ufrn.imd.sbm.dtos.SubscriptionTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptiontype")
class SubscriptionTypeController {
    @Autowired
    private SubscriptionTypeService subscriptionTypeService;

    @AdminOnly
    @PostMapping
    public ResponseEntity<Void> createSubscriptionType(@RequestBody SubscriptionTypeDTO subscriptionTypeDto) {
        try {subscriptionTypeService.createSubscriptionType(subscriptionTypeDto);
        return new ResponseEntity<>(HttpStatus.CREATED);}
        catch (InvalidArgumentException | NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @AnyAuthed
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionType> getSubscriptionTypeById(@PathVariable Long id) {
        try {
            SubscriptionType subscriptionType = subscriptionTypeService.findSubscriptionTypeById(id);
            return ResponseEntity.status(HttpStatus.OK).body(subscriptionType);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @AdminOnly
    @PutMapping("/{id}")
    public ResponseEntity<String> updateSubscriptionType(@PathVariable Long id, @RequestBody SubscriptionTypeDTO subscriptionTypeDto) {
        try {
            subscriptionTypeService.updateSubscriptionType(id, subscriptionTypeDto);
            return new ResponseEntity<>("SubscriptionType atualizado com sucesso!", HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @AdminOnly
    @GetMapping("/all")
    public ResponseEntity<List<SubscriptionType>> getAllSubscriptionTypes() {
        List<SubscriptionType> subscriptionTypes = subscriptionTypeService.findAllSubscriptionTypes();
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionTypes);
    }

    @AdminOnly
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubscriptionTypeById(@PathVariable Long id) {
        try {
            subscriptionTypeService.deleteSubscriptionType(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
