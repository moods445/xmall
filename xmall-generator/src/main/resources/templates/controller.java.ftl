package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("/admin/")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    private ${table.serviceName} ${table.serviceName?substring(1)?uncap_first};

    @GetMapping("/${entity?uncap_first}s")
    public Page<${entity}> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size,
                                  @RequestParam(value = "sort", defaultValue = "") String sortStr) {
        Page<${entity}> pg = new Page<>(page, size);
        return ${table.serviceName?substring(1)?uncap_first}.page(pg);
    }

    @DeleteMapping("/${entity?uncap_first}/{id}")
    public void del(@PathVariable("id") long id) {
        ${table.serviceName?substring(1)?uncap_first}.removeById(id);
    }

    @PutMapping("/${entity?uncap_first}")
    public void save(@Validated @RequestBody ${entity} ${entity?uncap_first}) {
        ${table.serviceName?substring(1)?uncap_first}.save(${entity?uncap_first});
    }
    

}
</#if>
