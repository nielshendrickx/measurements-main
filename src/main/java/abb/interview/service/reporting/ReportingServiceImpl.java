package abb.interview.service.reporting;

import abb.interview.model.Direction;
import abb.interview.model.Measurement;
import abb.interview.model.Power;
import abb.interview.model.dto.DeviceSummaryDto;
import abb.interview.model.dto.GroupTotalsDto;
import abb.interview.service.measurement.MeasurementService;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReportingServiceImpl implements ReportingService {

    MeasurementService measurementService;

    public ReportingServiceImpl(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @Override
    public GroupTotalsDto getGroupTotals(String group, Direction direction) {
        List<Measurement> measurements = measurementService.getMeasurements()
                .values()
                .stream()
                .filter(entry -> group.equals(entry.getDeviceGroup()) && direction.equals(entry.getDirection()))
                .toList();

        return new GroupTotalsDto(group,
                direction,
                getTotal(measurements, Power::getMin),
                getTotal(measurements, Power::getMax),
                getTotal(measurements, Power::getAvg)
                );
    }

    @Override
    public List<DeviceSummaryDto> getDevicesSummary() {
        return measurementService.getMeasurements()
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
