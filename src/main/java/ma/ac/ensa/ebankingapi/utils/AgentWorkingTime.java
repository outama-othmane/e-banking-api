package ma.ac.ensa.ebankingapi.utils;

import com.google.common.collect.Maps;

import java.time.LocalTime;
import java.util.Map;

public class AgentWorkingTime {

    public static Map<String, Map<String, LocalTime>> get() {
        Map<String, Map<String, LocalTime>> workingTime = Maps.newHashMap();

        Map<String, LocalTime> monday = Maps.newHashMap();
        monday.put("start", LocalTime.parse("08:30"));
        monday.put("end", LocalTime.parse("16:30"));

        Map<String, LocalTime> saturday = Maps.newHashMap();
        saturday.put("start", null);
        saturday.put("end", null);

        workingTime.put("monday", monday);
        workingTime.put("tuesday", monday);
        workingTime.put("wednesday", monday);
        workingTime.put("thursday", monday);
        workingTime.put("friday", monday);
        workingTime.put("saturday", saturday);
        workingTime.put("sunday", saturday);


        return workingTime;
    }
}
