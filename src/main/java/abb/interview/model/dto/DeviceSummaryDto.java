package abb.interview.model.dto;

import abb.interview.model.Direction;

import java.util.UUID;

import static abb.interview.Application.DECIMAL_FORMAT;

public record DeviceSummaryDto(UUID deviceId, String group, Direction direction, double maxPower) {

    @Override
    public String toString() {
        return "DeviceSummaryDto{" +
                "deviceId=" + deviceId.toString().replace("-","") +
                ", group='" + group + '\'' +
                ", direction=" + direction +
                ", maxPower=" + DECIMAL_FORMAT.format(maxPower) +
                '}';
    }

}
