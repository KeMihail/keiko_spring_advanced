package com.epam.keikom.dao.domain.aspect;

import java.io.Serializable;

public class DiscountAspectDomain implements Serializable {

    private String discount;
    private Double amount;

    public String getDiscount() {
        return discount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
