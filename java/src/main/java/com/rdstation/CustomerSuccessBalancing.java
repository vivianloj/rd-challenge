package com.rdstation;

import com.rdstation.utils.ScoreComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CustomerSuccessBalancing {

    private final List<CustomerSuccess> customersSuccess;
    private final List<Customer> customers;
    private final List<Integer> customersSuccessAway;

    public CustomerSuccessBalancing(List<CustomerSuccess> customersSuccess,
                                    List<Customer> customers,
                                    List<Integer> customerSuccessAway) {
        this.customersSuccess = customersSuccess;
        this.customers = customers;
        this.customersSuccessAway = customerSuccessAway;
    }


    public int run() {

        final List<CustomerSuccess> customerSuccessParsed = this.customersSuccess.stream()
                .filter(item -> !this.customersSuccessAway.contains(item.getId())).collect(Collectors.toList());

        final ScoreComparator comparator = new ScoreComparator();
        customerSuccessParsed.sort(comparator);
        this.customers.sort(comparator);

        final AtomicInteger maxCustomers = new AtomicInteger(0);
        final List<Integer> cssMaxCustomers = new ArrayList<>();
        final AtomicInteger csIndex = new AtomicInteger(0);
        final AtomicInteger countCustomerPerCs = new AtomicInteger(0);

        for (Customer customer : this.customers) {
            while (csIndex.get() < customerSuccessParsed.size()) {
                final CustomerSuccess customerSuccess = customerSuccessParsed.get(csIndex.get());

                if (customerSuccess.getScore() >= customer.getScore()) {
                    countCustomerPerCs.getAndIncrement();
                    break;
                }

                if (countCustomerPerCs.get() > 0) {
                    if (countCustomerPerCs.get() > maxCustomers.get()) {
                        maxCustomers.set(countCustomerPerCs.get());
                        cssMaxCustomers.clear();
                    }

                    if (countCustomerPerCs.get() >= maxCustomers.get()) {
                        cssMaxCustomers.add(customerSuccess.getId());
                    }
                }

                countCustomerPerCs.set(0);
                csIndex.getAndIncrement();
            }

            if (csIndex.get() >= customerSuccessParsed.size()) {
                break;
            }

        }

        if (csIndex.get() < customerSuccessParsed.size()) {
            if (countCustomerPerCs.get() > maxCustomers.get()) {
                cssMaxCustomers.clear();
            }

            if (countCustomerPerCs.get() >= maxCustomers.get()) {
                cssMaxCustomers.add(customerSuccessParsed.get(csIndex.get()).getId());
            }
        }

        return cssMaxCustomers.isEmpty() || cssMaxCustomers.size() > 2 ? 0 : cssMaxCustomers.get(0);
    }

}
