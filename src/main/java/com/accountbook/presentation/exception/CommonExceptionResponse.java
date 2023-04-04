package com.accountbook.presentation.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonExceptionResponse {
    private String exceptionMessage;
    private String exceptionDetail;
}
