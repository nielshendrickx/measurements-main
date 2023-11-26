package abb.interview.service.print;

import abb.interview.model.*;
import abb.interview.model.dto.DeviceSummaryDto;
import abb.interview.model.dto.GroupTotalsDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.UUID;

class PrintServiceImplTest {

    public static final UUID UUID = java.util.UUID.fromString("49e258fe-9d40-43b4-9a29-31f82ad5ec15");
    public static final String DEVICE_NAME = "device_test";
    public static final String DEVICE_GROUP = "test_group";
    public static final double POWER_MIN = 29.163184452205893;
    public static final double POWER_MAX = 65.44317306454077;
    public static final double POWER_AVG = 47.303178758373335;
    public static final int POWER_TIMESTAMP = 1626300000;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintService printService = new PrintServiceImpl();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void printAllMeasurements() {
        Key key = new Key(UUID.toString(), DEVICE_NAME, DEVICE_GROUP);
        Power power = new Power(POWER_MIN, POWER_MAX, POWER_AVG, POWER_TIMESTAMP);
        Measurement measurement = new Measurement();
        measurement.setResourceId(UUID.toString());
        measurement.setDeviceName(DEVICE_NAME);
        measurement.setDeviceGroup(DEVICE_GROUP);
        measurement.setDirection(Direction.IN);
        measurement.setPower(List.of(power));
        Measurements measurements = new Measurements();
        measurements.put(key, measurement);

        printService.printAllMeasurements(measurements);

        Assertions.assertEquals("[41mPrinting all recorded measurements:\n" +
                "\u001B[0mMeasurement{resourceId='49e258fe-9d40-43b4-9a29-31f82ad5ec15', deviceName='device_test', deviceGroup='test_group', direction=IN, power=[Power{min=29.163184452205893, max=65.44317306454077, avg=47.303178758373335, timestamp=1626300000}]}", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void printGroupTotals() {
        GroupTotalsDto groupTotal = new GroupTotalsDto(DEVICE_GROUP, Direction.IN, POWER_MIN, POWER_MAX, POWER_AVG);

        printService.printGroupTotals(groupTotal);

        Assertions.assertEquals("[41mPrinting group totals for group test_group and direction IN:\n" +
                "\u001B[0mGroupTotalsDto{group='test_group', direction=IN, min=29,1632, max=65,4432, avg=47,3032}", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void printAllDevices() {
        DeviceSummaryDto deviceSummaryDto = new DeviceSummaryDto(UUID, DEVICE_GROUP, Direction.IN, POWER_MAX);
        List<DeviceSummaryDto> devicesSummary = List.of(deviceSummaryDto);

        printService.printAllDevices(devicesSummary);

        Assertions.assertEquals("[41mPrinting all devices:\n" +
                "\u001B[0mDeviceSummaryDto{deviceId=49e258fe9d4043b49a2931f82ad5ec15, group='test_group', direction=IN, maxPower=65,4432}", outputStreamCaptor.toString()
                .trim());
    }

}