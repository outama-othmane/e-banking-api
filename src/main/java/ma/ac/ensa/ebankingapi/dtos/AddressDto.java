package ma.ac.ensa.ebankingapi.dtos;

import lombok.*;
import ma.ac.ensa.ebankingapi.models.Address;

import javax.validation.constraints.NotBlank;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    @NotBlank
    private String address1;

    private String address2;

    @NotBlank
    private String city;

    @NotBlank
    private String ZIPCode;

    @NotBlank
    private String country;

    public static Address toEntity(AddressDto addressDto) {
        if (addressDto == null) {
            return null;
        }

        Address address = Address.builder()
                .address1(addressDto.address1)
                .address2(addressDto.address2)
                .city(addressDto.city)
                .ZIPCode(addressDto.ZIPCode)
                .country(addressDto.country)
                .build();

        return address;
    }

    public static AddressDto fromEntity(Address address) {
        if (address == null) {
            return null;
        }

        AddressDto addressDto = AddressDto.builder()
                .address1(address.getAddress1())
                .address2(address.getAddress2())
                .city(address.getCity())
                .ZIPCode(address.getZIPCode())
                .country(address.getCountry())
                .build();

        return addressDto;
    }
}
