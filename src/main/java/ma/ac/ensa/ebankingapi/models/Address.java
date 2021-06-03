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

    @Column(nullable = true)
    private String address1;

    @Column(nullable = true)
    private String address2;

    @Column(nullable = true)
    private String city;

    @Column(nullable = true)
    private String ZIPCode;

    @Column(nullable = true)
    private String country;
}
