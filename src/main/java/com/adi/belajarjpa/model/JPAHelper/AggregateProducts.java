package com.adi.belajarjpa.model.JPAHelper;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AggregateProducts {

    private Long min;

    private Long max;

    private Double avg;

    public AggregateProducts(Long min, Long max, Double avg) {
        this.min = min;
        this.max = max;
        this.avg = avg;
    }
}
