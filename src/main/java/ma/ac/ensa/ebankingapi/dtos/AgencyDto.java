package ma.ac.ensa.ebankingapi.dtos;

import lombok.*;
import ma.ac.ensa.ebankingapi.exception.ConvertDtoToEntityException;
import ma.ac.ensa.ebankingapi.exception.ConvertEntityToDtoException;
import ma.ac.ensa.ebankingapi.models.Agency;

import javax.validation.constraints.NotBlank;

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
            throw new ConvertDtoToEntityException(
                    String.format("Impossible to convert a null object of type %s to an entity.",
                            AgencyDto.class.getName()
                    )
            );
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
            throw new ConvertEntityToDtoException(
                    String.format("Impossible to convert a null object of type %s to a dto.",
                            Agency.class.getName()
                    )
            );
        }

        AgencyDto agencyDto = AgencyDto.builder()
                .id(agency.getId())
                .title(agency.getTitle())
                .address(AddressDto.fromEntity(agency.getAddress()))
                .build();

        return agencyDto;
    }
}
