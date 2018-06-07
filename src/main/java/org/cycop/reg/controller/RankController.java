package org.cycop.reg.controller;

import org.cycop.reg.dataobjects.repositories.RankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rank")
public class RankController {

    @Autowired
    private RankRepository rankRepo;

    @GetMapping("/{rankCode}")
    public List getRank(@PathVariable String rankCode) {
        return rankRepo.getRank(rankCode);
    }

    @GetMapping
    public List getAllRanks() {
        return rankRepo.getAll();
    }

}
