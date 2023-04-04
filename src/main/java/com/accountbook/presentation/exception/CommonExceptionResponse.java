package com.accountbook.presentation.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CommonExceptionResponse {
    private String exceptionMessage;
    private String exceptionDetail;
}
