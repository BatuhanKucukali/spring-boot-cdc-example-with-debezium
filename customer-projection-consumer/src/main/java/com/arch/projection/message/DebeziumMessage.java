package com.arch.projection.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebeziumMessage<T> {
    private Payload<T> payload;
}
