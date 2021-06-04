package ma.ac.ensa.ebankingapi.dtos;

import lombok.*;
import ma.ac.ensa.ebankingapi.models.Agency;
import ma.ac.ensa.ebankingapi.models.Agent;
import ma.ac.ensa.ebankingapi.models.Client;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AgencyDto {

    private Long id;

    @NotBlank
    private String title;

    private AddressDto address;

    public static Agency toEntity(AgencyDto agencyDto) {
        if (agencyDto == null) {
            // TODO: throw an exception
            return null;
        }

        Agency agency = Agency.builder()
                .title(agencyDto.title)
                .address(AddressDto.toEntity(agencyDto.address))
                .build();

        agency.setId(agencyDto.id);

        return agency;
    }

    public static AgencyDto fromEntity(Agency agency) {
        if (agency == null) {
            // TODO: throw an exception
            return null;
        }

        AgencyDto agencyDto = AgencyDto.builder()
                .id(agency.getId())
                .title(agency.getTitle())
                .address(AddressDto.fromEntity(agency.getAddress()))
                .build();

        return agencyDto;
    }
}
