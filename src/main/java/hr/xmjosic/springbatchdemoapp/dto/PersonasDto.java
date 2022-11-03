package hr.xmjosic.springbatchdemoapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonasDto {
  private String rnum;
  private String firstName;
  private String LastName;
  private String email;
}
