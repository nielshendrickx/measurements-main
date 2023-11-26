package abb.interview.service.reporting;

import abb.interview.model.*;
import abb.interview.model.dto.DeviceSummaryDto;
import abb.interview.model.dto.GroupTotalsDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class ReportingServiceImplTest {
    public static final String DEVICE_NAME_1 = "device_test_1";
    public static final String DEVICE_NAME_2 = "device_test_2";
    public static final String DEVICE_GROUP = "test_group";
    public static final double POWER_MIN = 29.163184452205893;
    public static final double POWER_MAX = 65.44317306454077;
    public static final double POWER_AVG = 47.303178758373335;
    public static final int POWER_TIMESTAMP = 1626300000;
    public static final UUID RESOURCE_ID_1 = UUID.randomUUID();
    public static final UUID RESOURCE_ID_2 = UUID.randomUUID();

    private final ReportingService reportingService = new ReportingServiceImpl();

    @Test
    void getGroupTotals() {
        Key key1 = new Key(RESOURCE_ID_1.toString(), DEVICE_NAME_1, DEVICE_GROUP);
        Key key2 = new Key(RESOURCE_ID_2.toString(), DEVICE_NAME_2, DEVICE_GROUP);

        Power power = new Power(POWER_MIN, POWER_MAX, POWER_AVG, POWER_TIMESTAMP);
        Measurement measurement1 = new Measurement();
        measurement1.setResourceId(RESOURCE_ID_1.toString());
        measurement1.setDeviceName(DEVICE_NAME_1);
        measurement1.setDeviceGroup(DEVICE_GROUP);
        measurement1.setDirection(Direction.IN);
        measurement1.setPower(List.of(power));
        Measurement measurement2 = new Measurement();
        measurement2.setResourceId(RESOURCE_ID_2.toString());
        measurement2.setDeviceName(DEVICE_NAME_2);
        measurement2.setDeviceGroup(DEVICE_GROUP);
        measurement2.setDirection(Direction.IN);
        measurement2.setPower(List.of(power));

        Measurements measurements = new Measurements();
        measurements.put(key1, measurement1);
        measurements.put(key2, measurement2);

        GroupTotalsDto groupTotalsExpected = new GroupTotalsDto(DEVICE_GROUP, Direction.IN, POWER_MIN*2, POWER_MAX*2, POWER_AVG*2);

        GroupTotalsDto groupTotalsActual = reportingService.getGroupTotals(measurements, DEVICE_GROUP, Direction.IN);

        Assertions.assertEquals(groupTotalsExpected, groupTotalsActual);
    }

    @Test
    void getDevicesSummary() {
        Key key1 = new Key(RESOURCE_ID_1.toString(), DEVICE_NAME_1, DEVICE_GROUP);
        Key key2 = new Key(RESOURCE_ID_2.toString(), DEVICE_NAME_2, DEVICE_GROUP);

        Power power = new Power(POWER_MIN, POWER_MAX, POWER_AVG, POWER_TIMESTAMP);
        Measurement measurement1 = new Measurement();
        measurement1.setResourceId(RESOURCE_ID_1.toString());
        measurement1.setDeviceName(DEVICE_NAME_1);
        measurement1.setDeviceGroup(DEVICE_GROUP);
        measurement1.setDirection(Direction.IN);
        measurement1.setPower(List.of(power));
        Measurement measurement2 = new Measurement();
        measurement2.setResourceId(RESOURCE_ID_2.toString());
        measurement2.setDeviceName(DEVICE_NAME_2);
        measurement2.setDeviceGroup(DEVICE_GROUP);
        measurement2.setDirection(Direction.OUT);
        measurement2.setPower(List.of(power));

        Measurements measurements = new Measurements();
        measurements.put(key1, measurement1);
        measurements.put(key2, measurement2);

        List<DeviceSummaryDto> devicesSummaryExpected = List.of(new DeviceSummaryDto(RESOURCE_ID_1, DEVICE_GROUP, Direction.IN, POWER_MAX), new DeviceSummaryDto(RESOURCE_ID_2,DEVICE_GROUP, Direction.OUT,POWER_MAX));

        List<DeviceSummaryDto> devicesSummaryActual = reportingService.getDevicesSummary(measurements);

        Assertions.assertEquals(devicesSummaryExpected, devicesSummaryActual);
    }

}