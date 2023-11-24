package abb.interview.service.reporting;

import abb.interview.model.Direction;
import abb.interview.model.Measurement;
import abb.interview.model.Measurements;
import abb.interview.model.Power;
import abb.interview.model.dto.DeviceSummaryDto;
import abb.interview.model.dto.GroupTotalsDto;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReportingServiceImpl implements ReportingService {

    @Override
    public GroupTotalsDto getGroupTotals(Measurements measurements, String group, Direction direction) {
        List<Measurement> measurementsForGroupAndDirection = measurements
                .values()
                .stream()
                .filter(entry -> group.equals(entry.getDeviceGroup()) && direction.equals(entry.getDirection()))
                .toList();

        return new GroupTotalsDto(group,
                direction,
                getTotal(measurementsForGroupAndDirection, Power::getMin),
                getTotal(measurementsForGroupAndDirection, Power::getMax),
                getTotal(measurementsForGroupAndDirection, Power::getAvg)
                );
    }

    @Override
    public List<DeviceSummaryDto> getDevicesSummary(Measurements measurements) {
        return measurements
                .values()
                .stream()
                .map(measurement -> new DeviceSummaryDto(
                        UUID.fromString(measurement.getResourceId()),
                        measurement.getDeviceGroup(),
                        measurement.getDirection(),
                        measurement.getPower().stream().max(Comparator.comparing(Power::getMax)).orElseThrow(() -> new UnsupportedOperationException("No max power supplied to measurement.")).getMax()
                ))
                .sorted(Comparator.comparing(DeviceSummaryDto::group)
                        .thenComparing(DeviceSummaryDto::direction)
                        .thenComparing(DeviceSummaryDto::maxPower))
                .collect(Collectors.toList());
    }

    private double getTotal(List<Measurement> measurements, Function<Power, Double> function) {
        return measurements.stream()
                .flatMap(measurement -> measurement.getPower().stream().map(function))
                .reduce(Double::sum).orElse(0d);
    }

}
