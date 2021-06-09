package ma.ac.ensa.ebankingapi.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Address {

    @Column
    private String address1;

    @Column
    private String address2;

    @Column
    private String city;

    @Column
    private String ZIPCode;

    @Column
    private String country;
}
