package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};

/**
* Create by Code Generator
* @Author ZengMin
* @Date ${.now?string["yyyy-MM-dd HH:mm:ss"]}
*/

<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>
