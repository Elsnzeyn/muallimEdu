package org.example.muallimeduazbackend.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.muallimeduazbackend.enums.UserRoleEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequestDto {
    //    private int userId;
    private String name;
        private String email;
    private String password;
    private UserRoleEnum role;
}
