package abb.interview.service.print;

import abb.interview.model.Measurements;
import abb.interview.model.dto.DeviceSummaryDto;
import abb.interview.model.dto.GroupTotalsDto;

import java.util.List;

public interface PrintService {

    void printAllMeasurements(Measurements measurements);

    void printGroupTotals(GroupTotalsDto groupTotal);

    void printAllDevices(List<DeviceSummaryDto> devicesSummary);

}
