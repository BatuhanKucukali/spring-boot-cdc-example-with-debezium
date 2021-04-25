package com.arch.projection.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Source {
    private String version;
    private String connector;
    private String name;
    @JsonProperty(value = "ts_ms")
    private Long timestampMs;
    private String snapshot;
    private String db;
    private String sequence;
    private String schema;
    private String table;
    private Long txId;
    private Long lsn;
}
