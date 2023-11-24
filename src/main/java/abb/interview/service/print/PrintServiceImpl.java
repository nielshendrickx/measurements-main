package abb.interview.service.print;

import abb.interview.model.Measurements;
import abb.interview.model.dto.DeviceSummaryDto;
import abb.interview.model.dto.GroupTotalsDto;

import java.util.List;

public class PrintServiceImpl implements PrintService{
    @Override
    public void printAllMeasurements(Measurements measurements) {
        System.out.printf("\u001B[41m" +"Printing all recorded measurements:%n"+ "\u001B[0m");
        measurements.values().forEach(System.out::println);
    }

    @Override
    public void printGroupTotals(GroupTotalsDto groupTotal) {
        System.out.printf("\u001B[41m" +"Printing group totals for group %s and direction %s:%n"+ "\u001B[0m", groupTotal.group(), groupTotal.direction());
        System.out.println(groupTotal);
    }

    @Override
    public void printAllDevices(List<DeviceSummaryDto> devicesSummary) {
        System.out.printf("\u001B[41m" + "Printing all devices:%n" + "\u001B[0m");
        devicesSummary.forEach(System.out::println);
    }

}
