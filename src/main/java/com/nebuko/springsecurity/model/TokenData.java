package com.nebuko.springsecurity.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenData {

  private String userId;
}

