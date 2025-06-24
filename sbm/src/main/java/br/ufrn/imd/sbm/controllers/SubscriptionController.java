package br.ufrn.imd.sbm.controllers;

import br.ufrn.imd.framework.annotations.AdminOnly;
import br.ufrn.imd.framework.annotations.AnyAuthed;
import br.ufrn.imd.framework.exceptions.NotFoundException;
import br.ufrn.imd.framework.models.User;
import br.ufrn.imd.sbm.dtos.SubscriptionDTO;
import br.ufrn.imd.sbm.models.Subscription;
import br.ufrn.imd.sbm.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscription")
class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;
    @AnyAuthed
    @PostMapping
    public ResponseEntity<String> createSubscription(@AuthenticationPrincipal User user, 
                                                     @RequestBody SubscriptionDTO subscriptionDTO) {
        try {
            subscriptionService.createSubscription(user,subscriptionDTO);
            return new ResponseEntity<>("Assinatura criada com sucesso!", HttpStatus.CREATED);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @AnyAuthed
    @GetMapping("/{subscriptionId}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable Long subscriptionId, @AuthenticationPrincipal User user) {
        try {
            Subscription subscription = subscriptionService.findSubscriptionById(subscriptionId, user);
            return ResponseEntity.status(HttpStatus.OK).body(subscription);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @AnyAuthed
    @DeleteMapping("/{subscriptionId}") public ResponseEntity<String> deleteSubscriptionById(@PathVariable Long subscriptionId, @AuthenticationPrincipal User user) {
        try {
            subscriptionService.deleteSubscription(subscriptionId, user);
            return new ResponseEntity<>("Assinatura apagada com sucesso", HttpStatus.NO_CONTENT);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @AnyAuthed
    @GetMapping("/alluser") public ResponseEntity<List<Subscription>> getAllUserSubscriptions(@AuthenticationPrincipal User user) {
        List<Subscription> subscriptions = subscriptionService.findAllUserSubscriptions(user);
        return ResponseEntity.status(HttpStatus.OK).body(subscriptions);
    }

    @AdminOnly
    @GetMapping("/all")
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionService.findAllSubscriptions();
        return ResponseEntity.status(HttpStatus.OK).body(subscriptions);
    }
}
