package org.xyz.cartsvc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CartConvertRequest(
          Long userId,
          List<Long> cartItemIds
){}
