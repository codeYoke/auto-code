package com.fjh.codeAuto;

/**
 * ClassName: DataProperties
 * Description:数据库元数据转换类
 * date: 2019/9/3 16:34
 *
 * @author 冯佳豪
 */
public class DataProperties {

    private String remarks;
    private String typeName;
    private String columnName;

    public DataProperties(String remarks, String typeName, String columnName) {
        this.remarks = remarks;
        this.typeName = typeName;
        this.columnName = columnName;
    }

    public DataProperties() {
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTypeName() {
        //替换成java的数据类型
        if(FieldTypeEnum.INT.name().equals(typeName) ){
            return FieldTypeEnum.INT.getFieldType();
        } else if (FieldTypeEnum.VARCHAR.name().equals(typeName)){
            return FieldTypeEnum.VARCHAR.getFieldType();
        }else if (FieldTypeEnum.FLOAT.name().equals(typeName)){
            return FieldTypeEnum.FLOAT.getFieldType();
        }else if (FieldTypeEnum.TIMESTAMP.name().equals(typeName)){
            return FieldTypeEnum.TIMESTAMP.getFieldType();
        }

        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
