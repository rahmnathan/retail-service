package com.github.rahmnathan.retail.redsky.data.data;

public class RedSkyProduct {
    private final String name;

    private RedSkyProduct(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String name;

        public static Builder newInstance(){
            return new Builder();
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public RedSkyProduct build(){
            return new RedSkyProduct(name);
        }
    }
}
