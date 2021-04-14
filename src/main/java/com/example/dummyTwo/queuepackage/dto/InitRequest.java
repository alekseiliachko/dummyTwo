package com.example.dummyTwo.queuepackage.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InitRequest {
    @JsonProperty(required = true)
    private String remoteName;

    @JsonProperty(required = true)
    private String remotePass;

    @JsonProperty(required = true)
    private String remoteKey;
}
