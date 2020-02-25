package com.winfred.training.designpattern.creational.builder;

public class ResourcePoolConfig {
    private String name;
    private int coreSize;
    private int maxSize;

    public ResourcePoolConfig(Builder builder) {
        this.name = builder.name;
        this.coreSize = builder.coreSize;
        this.maxSize = builder.maxSize;
    }

    public String getName() {
        return name;
    }

    public int getCoreSize() {
        return coreSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public static class Builder {

        private static final int CORE_SIZE = 8;
        private static final int MAX_SIZE = 8;

        private String name;
        private int coreSize = CORE_SIZE;
        private int maxSize = CORE_SIZE;

        public ResourcePoolConfig build() {
            return new ResourcePoolConfig(this);
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCoreSize(int size) {
            if (size <= 0) {
                throw new IllegalArgumentException("size must > 0");
            }
            this.coreSize = size;
            return this;
        }

        public Builder setMaxSize(int size) {
            if (size < coreSize) {
                throw new IllegalArgumentException("max size must >= 8");
            }
            this.maxSize = size;
            return this;
        }
    }

}
