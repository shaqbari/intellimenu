package com.sist.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.hadoop.conf.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sist.collertor.Homeplus;
import com.sist.collertor.Lotte;

@Controller
public class BigdataTestController {
	@Autowired
	Homeplus homple;
	
	@Autowired
	Lotte lot;
	@Autowired
	Configuration conf;
	@RequestMapping("/bigdata/22")
	public String baigDate(HttpServletRequest req){
		String path=req.getSession().getServletContext().getContext("/").getRealPath("") ;
		System.out.println(path+"asd");

		//lot.lotteFile(conf,path);
		
		homple.homeplusFileup(conf,path);
	  System.out.println("됨?");
	  return "bigdata/bigdata_main";
  }
}
