package by.pankov.simple.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgeRq {

    @ApiParam(name = "age of user", required = true)
    private Integer age;
}
