package abb.interview;

import abb.interview.model.Direction;
import abb.interview.model.dto.DeviceSummaryDto;
import abb.interview.service.measurement.MeasurementService;
import abb.interview.service.measurement.MeasurementServiceImpl;
import abb.interview.service.reporting.ReportingService;
import abb.interview.service.reporting.ReportingServiceImpl;

import java.text.DecimalFormat;
import java.util.List;

public class Application {

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("##.0000");

    public static void main(String[] args) {
        MeasurementService measurementService = new MeasurementServiceImpl();
        ReportingService reportingService = new ReportingServiceImpl(measurementService);

        printAllMeasurements(measurementService);
        printGroupTotals(reportingService);
        printAllDevices(reportingService);
    }

    private static void printAllMeasurements(MeasurementService measurementService) {
        System.out.printf("\u001B[41m" +"Printing all recorded measurements:%n"+ "\u001B[0m");
        measurementService.getMeasurements().values().forEach(System.out::println);
    }

    private static void printGroupTotals(ReportingService reportingService) {
        System.out.printf("\u001B[41m" +"Printing group totals:%n"+ "\u001B[0m");
        System.out.printf("Group total for group a with direction IN: %s%n", reportingService.getGroupTotals("group_a", Direction.IN));
        System.out.printf("Group total for group a with direction OUT: %s%n", reportingService.getGroupTotals("group_a", Direction.OUT));
        System.out.printf("Group total for group b with direction IN: %s%n", reportingService.getGroupTotals("group_b", Direction.IN));
        System.out.printf("Group total for group b with direction IN: %s%n", reportingService.getGroupTotals("group_b", Direction.OUT));
    }

    private static void printAllDevices(ReportingService reportingService) {
        System.out.printf("\u001B[41m" + "Printing all devices:%n" + "\u001B[0m");
        List<DeviceSummaryDto> devicesSummary = reportingService.getDevicesSummary();
        devicesSummary.forEach(System.out::println);
    }

}
