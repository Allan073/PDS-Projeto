package br.ufrn.imd.framework.dtos;

import java.util.Date;
import java.util.List;

public record OrderDTO(
        Date orderdate,
        String description,
        List<Long> items,
        Integer orderstate,
        Double totalprice
) {
}
