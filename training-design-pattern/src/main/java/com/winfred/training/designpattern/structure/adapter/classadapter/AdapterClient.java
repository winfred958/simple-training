package com.winfred.training.designpattern.structure.adapter.classadapter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class AdapterClient {

    @Test
    public void adapterTest() {
        Target target = new Adapter();
        int output = target.output();
        log.info("{}", output);
    }

}
