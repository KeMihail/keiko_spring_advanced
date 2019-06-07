package com.epam.keikom.dao.domain.aspect;

import java.io.Serializable;

public class CounterAspectDomain implements Serializable {

    private Long event;
    private Integer byName;
    private Integer byPrice;
    private Integer byBooked;

    public Long getEvent() {
        return event;
    }

    public Integer getByName() {
        return byName;
    }

    public Integer getByPrice() {
        return byPrice;
    }

    public Integer getByBooked() {
        return byBooked;
    }

    public void setEvent(Long event) {
        this.event = event;
    }

    public void setByName(Integer byName) {
        this.byName = byName;
    }

    public void setByPrice(Integer byPrice) {
        this.byPrice = byPrice;
    }

    public void setByBooked(Integer byBooked) {
        this.byBooked = byBooked;
    }
}
