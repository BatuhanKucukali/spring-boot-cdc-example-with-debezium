package com.arch.projection.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Operation {
    @JsonProperty("c") C("Create"),
    @JsonProperty("u") U("Update"),
    @JsonProperty("d") D("Delete"),
    @JsonProperty("r") R("Read");

    private final String message;
}
