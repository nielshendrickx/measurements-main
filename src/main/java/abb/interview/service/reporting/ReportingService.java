package abb.interview.service.reporting;

import abb.interview.model.Direction;
import abb.interview.model.dto.DeviceSummaryDto;
import abb.interview.model.dto.GroupTotalsDto;

import java.util.List;

public interface ReportingService {

    GroupTotalsDto getGroupTotals(String group, Direction direction);

    List<DeviceSummaryDto> getDevicesSummary();

}
