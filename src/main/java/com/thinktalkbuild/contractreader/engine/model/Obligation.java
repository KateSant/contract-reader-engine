package com.thinktalkbuild.contractreader.engine.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Obligation extends Match{

    private String party;
    private String obligingVerb;
    private String action;
}
