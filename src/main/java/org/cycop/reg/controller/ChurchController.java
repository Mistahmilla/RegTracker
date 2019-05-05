package org.cycop.reg.controller;

import org.cycop.reg.dao.ChurchDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/church")
public class ChurchController {

    @Autowired
    ChurchDAO churchDAO;

    @GetMapping
    public List churchSearch(@RequestParam(value="churchID", defaultValue="0") long churchID, @RequestParam(value="churchName", defaultValue = "") String churchName, @RequestParam(value="pastorName", defaultValue = "") String pastorName, @RequestParam(value="zipCode", defaultValue = "") String zipCode){
        return churchDAO.get(churchID, churchName, pastorName, zipCode);
    }

    @GetMapping("/{churchID}")
    public List getChurch(@PathVariable long churchID){
        return churchDAO.get(churchID, "", "", "");
    }

}
