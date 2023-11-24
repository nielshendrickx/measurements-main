package abb.interview.service.measurement;

import abb.interview.exception.DataException;
import abb.interview.model.Measurement;
import abb.interview.model.Measurements;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MeasurementServiceImpl implements MeasurementService {

    private final ObjectMapper objectMapper;
    private final Logger log;

    public MeasurementServiceImpl() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        log = LoggerFactory.getLogger(MeasurementService.class);
    }

    @Override
    public Measurements getMeasurements() {
        Measurements measurements = new Measurements();

        try {
            objectMapper.readValue(new URL("file:src/main/resources/measurements.json"), new TypeReference<List<Measurement>>() {
                    })
                    .forEach(measurement -> measurements.put(measurement.getKey(), measurement));
        } catch (IOException e) {
            log.error("Failed to load measurements", e);
            throw new DataException("Failed to load measurements");
        }

        return measurements;
    }

}
