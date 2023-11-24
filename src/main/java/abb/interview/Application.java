package abb.interview;

import abb.interview.model.Direction;
import abb.interview.model.Measurements;
import abb.interview.model.dto.DeviceSummaryDto;
import abb.interview.service.measurement.MeasurementService;
import abb.interview.service.measurement.MeasurementServiceImpl;
import abb.interview.service.print.PrintService;
import abb.interview.service.print.PrintServiceImpl;
import abb.interview.service.reporting.ReportingService;
import abb.interview.service.reporting.ReportingServiceImpl;

import java.text.DecimalFormat;
import java.util.List;

public class Application {

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##.0000");

    public static void main(String[] args) {
        MeasurementService measurementService = new MeasurementServiceImpl();
        ReportingService reportingService = new ReportingServiceImpl();
        PrintService printService = new PrintServiceImpl();

        Measurements measurements = measurementService.getMeasurements();

        printService.printAllMeasurements(measurements);

        printService.printGroupTotals(reportingService.getGroupTotals(measurements,"group_a", Direction.IN));
        printService.printGroupTotals(reportingService.getGroupTotals(measurements,"group_a", Direction.OUT));
        printService.printGroupTotals(reportingService.getGroupTotals(measurements,"group_b", Direction.IN));
        printService.printGroupTotals(reportingService.getGroupTotals(measurements,"group_b", Direction.OUT));

        printService.printAllDevices(reportingService.getDevicesSummary(measurements));
    }

}
