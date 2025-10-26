package ru.kpfu.itis.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.dto.FieldErrorDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private boolean success;
    private List<FieldErrorDto> errors;
}
