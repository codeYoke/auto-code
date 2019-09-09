package ${entityPackageName};
<#list fields as field>
    <#if field.typeName == "java.lang.String">
    <#else>
    import ${field.typeName};
    </#if>
</#list>

/**
* ClassName:${entityClassName}
* Description:
* date: ${date?datetime}
*/
public class ${entityClassName} {

<#list fields as field>
    /**
    * ${field.remarks}
    */
    private ${(field.typeName?split("."))?last} ${field.columnName};
</#list>

<#list fields as field>
    public ${(field.typeName?split("."))?last} get${field.columnName?cap_first}() {
        return ${field.columnName};
    }

    public void set${field.columnName?cap_first}(${(field.typeName?split("."))?last} ${field.columnName}) {
        this.${field.columnName} = ${field.columnName};
    }
</#list>


}