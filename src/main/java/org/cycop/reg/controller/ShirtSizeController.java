package org.cycop.reg.controller;

import org.cycop.reg.dataobjects.repositories.ShirtSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shirtsize")
public class ShirtSizeController {

    @Autowired
    ShirtSizeRepository shirtSizeRepository;

    @GetMapping("/{shirtSizeCode}")
    public List getShirtSize(@PathVariable String shirtSizeCode) {
        return shirtSizeRepository.getShirtSizeByCode(shirtSizeCode);
    }

    @GetMapping
    public  List getAll(){
        return shirtSizeRepository.getShirtSize();
    }
}
