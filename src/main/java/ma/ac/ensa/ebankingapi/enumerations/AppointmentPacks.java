package ma.ac.ensa.ebankingapi.enumerations;

import lombok.Getter;

@Getter
public enum AppointmentPacks {

    SHORT(30),
    LONG(60);

    final Integer durationInMinutes;

    AppointmentPacks(Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }
}
