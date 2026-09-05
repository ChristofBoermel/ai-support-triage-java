package com.chris.aisupporttriage.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AnalyzeTicketRequest(@NotBlank
                                   @Size(max = 4000)
                                   String description,
                                   @Size(max = 100)
                                   String customerId,
                                   @Size(max = 100) String
                                   affectedService) {

}
