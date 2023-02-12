package com.thinktalkbuild.contractreader.api.engine.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Period;

@Getter
@Setter
public class Duration extends Match{

    private Period period;

}
