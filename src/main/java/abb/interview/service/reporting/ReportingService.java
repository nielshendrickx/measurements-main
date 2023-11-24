package abb.interview.service.reporting;

import abb.interview.model.Direction;
import abb.interview.model.Measurements;
import abb.interview.model.dto.DeviceSummaryDto;
import abb.interview.model.dto.GroupTotalsDto;

import java.util.List;

public interface ReportingService {

    GroupTotalsDto getGroupTotals(Measurements measurements, String group, Direction direction);

    List<DeviceSummaryDto> getDevicesSummary(Measurements measurements);

}
