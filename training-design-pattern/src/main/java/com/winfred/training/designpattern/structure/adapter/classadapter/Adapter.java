package com.winfred.training.designpattern.structure.adapter.classadapter;

/**
 *
 */
@Deprecated
public class Adapter extends Source implements Target {

    @Override
    public int output() {
        int outputVoltage = super.outputVoltage();
        // 适配手机电压
        int output = outputVoltage / 44;
        return output;
    }
}
