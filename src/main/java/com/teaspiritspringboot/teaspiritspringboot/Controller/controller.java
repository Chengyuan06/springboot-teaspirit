package com.teaspiritspringboot.teaspiritspringboot.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.teaspiritspringboot.teaspiritspringboot.model.Tea;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
public class controller {

  @GetMapping("/tea/{sku}") // {sku} est une variable de chemin (path variable), ce qui signifie qu'une partie de l'URL (par exemple /tea/123) sera extraite et utilisée comme paramètre dans la méthode.

  //@PathVariable("sku") : Cela signifie que la valeur de {sku} dans l'URL (par exemple, 123 dans /tea/123) sera capturée et attribuée à la variable int sku.
    public String getProductById(@PathVariable("sku") int sku, Model model) {
        Tea tea = Tea.readTea(sku);  // 获取产品详情
        if (tea != null) {
            model.addAttribute("tea", tea);
            return "product";  // 返回产品详情页面
        } else {
            return "error";  // 如果没有找到产品，则返回错误页面
        }
    }

    @GetMapping("/teaspirit")
        public String getHomePage(){
            return "home";
        }

}
    

// @GetMapping("path1")
// public ArrayList<Tea> test(){
//     return Tea.readAllTeas();

// }


