package com.example.demobook.module.base.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;

public class SortUtils {
    public static Sort.Direction getSortDirection(String direction) {
        Sort.Direction sortDirection = Sort.DEFAULT_DIRECTION;

        if (direction != null) {
            if ("ASC".equalsIgnoreCase(direction)) {
                sortDirection = Sort.Direction.ASC;
            } else if ("DESC".equalsIgnoreCase(direction)) {
                sortDirection = Sort.Direction.DESC;
            }
        }

        return sortDirection;
    }

    public static List<Sort.Order> getSortOrders(String[] sort) {
        List<Sort.Order> sortOrders = new ArrayList<Sort.Order>();

        if (sort[0].contains(",")) {
            for (String item : sort) {
                String[] _sort = item.split(",");
                sortOrders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            sortOrders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }

        return sortOrders;
    }
}
