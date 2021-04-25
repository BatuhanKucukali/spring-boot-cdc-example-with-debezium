package com.arch.projection.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Payload<T> {
    private T before;
    private T after;
    private Source source;
    private Operation op;
    @JsonProperty(value = "ts_ms")
    private Long timestampMs;
}
