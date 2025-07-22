package com.blog.domain.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {

  private int status;
  private String message;
  private List<FieldError> errors;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class FieldError {

    private String field;
    private String message;
  }

}
