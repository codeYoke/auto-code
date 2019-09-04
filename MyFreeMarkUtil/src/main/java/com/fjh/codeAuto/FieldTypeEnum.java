package com.fjh.codeAuto;

public enum FieldTypeEnum {
    INT{
        public  String getFieldType(){
            return "java.lang.Integer";
        }
    },VARCHAR{
        public  String getFieldType(){
            return "java.lang.String";
        }
    },FLOAT{
        public  String getFieldType(){
            return "java.lang.Float";
        }
    },TIMESTAMP{
        public  String getFieldType(){
            return "java.util.Date";
        }
    };


    public abstract String getFieldType();
}
