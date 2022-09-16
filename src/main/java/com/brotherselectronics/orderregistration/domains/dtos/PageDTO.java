package com.brotherselectronics.orderregistration.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ToString
@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class PageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(1)
    private final Integer size;

    @Min(0)
    private final Integer page;

    @NotNull
    private final String[] sorts;

}
