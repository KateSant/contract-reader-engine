package com.thinktalkbuild.contractreader.api.store.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ContractMetadata {

    String id;

    String name;

    LocalDate startDate;
}
