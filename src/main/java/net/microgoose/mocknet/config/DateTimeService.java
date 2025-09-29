package net.microgoose.mocknet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Service
public class DateTimeService {
    
    @Value("${app.timezone:UTC}")
    private String timezone;
    
    public OffsetDateTime toOffsetDateTime(Instant instant) {
        ZoneId zoneId = ZoneId.of(timezone);
        return instant.atOffset(zoneId.getRules().getOffset(instant));
    }
}