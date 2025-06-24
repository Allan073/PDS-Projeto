package br.ufrn.imd.sbm.dtos;

import java.util.List;

public record SubscriptionTypeDTO(String name, String description, List<Long> items, Double price, Integer frequency) {}
