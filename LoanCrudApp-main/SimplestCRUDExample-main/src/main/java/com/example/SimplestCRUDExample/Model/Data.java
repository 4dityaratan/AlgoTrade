package com.example.SimplestCRUDExample.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Data {
    private String open;
    private String close;
    private String high;
    private String low;
    private String volume;
}
