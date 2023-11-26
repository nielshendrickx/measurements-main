package abb.interview.service.measurement;

import abb.interview.model.Direction;
import abb.interview.model.Key;
import abb.interview.model.Measurement;
import abb.interview.model.Measurements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MeasurementServiceImplTest {
    public static final String DEVICE_NAME_1 = "device_1";
    public static final String DEVICE_NAME_2 = "device_2";
    public static final String DEVICE_GROUP = "group_a";
    public static final UUID RESOURCE_ID_1 = UUID.fromString("49e258fe-9d40-43b4-9a29-31f82ad5ec15");
    public static final UUID RESOURCE_ID_2 = UUID.fromString("4d5b9f5e-2b18-41bd-96f0-96674ed9846a");

    MeasurementService measurementService = new MeasurementServiceImpl();

    @Test
    public void getMeasurements() {
        Key key1 = new Key(RESOURCE_ID_1.toString(), DEVICE_NAME_1, DEVICE_GROUP);
        Key key2 = new Key(RESOURCE_ID_2.toString(), DEVICE_NAME_2, DEVICE_GROUP);

        Measurement measurement1 = new Measurement();
        measurement1.setResourceId(RESOURCE_ID_1.toString());
        measurement1.setDeviceName(DEVICE_NAME_1);
        measurement1.setDeviceGroup(DEVICE_GROUP);
        measurement1.setDirection(Direction.OUT);

        Measurement measurement2 = new Measurement();
        measurement2.setResourceId(RESOURCE_ID_2.toString());
        measurement2.setDeviceName(DEVICE_NAME_2);
        measurement2.setDeviceGroup(DEVICE_GROUP);
        measurement2.setDirection(Direction.OUT);

        Measurement[] expectedMeasurements = {measurement1, measurement2};

        Measurements actualMeasurements = measurementService.getMeasurements();

        Assertions.assertTrue(actualMeasurements.containsKey(key1));
        Assertions.assertTrue(actualMeasurements.containsKey(key2));
        assertThat(actualMeasurements.values())
                .usingElementComparatorIgnoringFields("power", "direction")
                .contains(expectedMeasurements);
    }

}