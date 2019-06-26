package com.thecodingpark.loadproperties;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ServiceProperties {

    @NotBlank
    private String serviceId;

    @Length(min = 4)
    private String url;

    private boolean required = true;
}
