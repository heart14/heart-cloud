package com.heart.mavendl.mvndl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * About:
 * Other:
 * Created: wfli on 2021/12/17 10:35.
 * Editored:
 */
@Controller
@RequestMapping
public class PageController {

    @RequestMapping(value = "/index.html")
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
