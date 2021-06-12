package com.lhever.sc.devops.core.support.datasource;

public enum DataSourceType {

    DEFAULT(DataSourceConstants.TYPE_DEFAULT),

    SECOND(DataSourceConstants.TYPE_SECOND);


    private String type;

    private DataSourceType(String type) {
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public static DataSourceType getByType(String type) {
        for (DataSourceType value : DataSourceType.values()) {

            String valueType = value.getType();

            if (valueType.equals(type)) {
                return value;
            }
        }

        return DataSourceType.DEFAULT;


    }


}
