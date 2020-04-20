package com.winfred.training.designpattern.structure.adapter.objectatapter;


/**
 * 用组合取代继承
 * <p>
 * {@link java.io.InputStreamReader}
 * {@link java.io.OutputStreamWriter}
 */
public class Adapter implements Target {
    private Source source;

    public Adapter(Source source) {
        this.source = source;
    }

    @Override
    public int output() {
        int outputVoltage = source.outputVoltage();
        int output = outputVoltage / 44;
        return output;
    }
}
