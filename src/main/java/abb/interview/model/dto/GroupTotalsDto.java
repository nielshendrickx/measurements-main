package abb.interview.model.dto;

import abb.interview.model.Direction;

import static abb.interview.Application.DECIMAL_FORMAT;

public record GroupTotalsDto(String group, Direction direction, double totalMin, double totalMax, double totalAvg) {

    @Override
    public String toString() {
        return "GroupTotalsDto{" +
                "group='" + group + '\'' +
                ", direction=" + direction +
                ", min=" + DECIMAL_FORMAT.format(totalMin) +
                ", max=" + DECIMAL_FORMAT.format(totalMax) +
                ", avg=" + DECIMAL_FORMAT.format(totalAvg) +
                '}';
    }

}
