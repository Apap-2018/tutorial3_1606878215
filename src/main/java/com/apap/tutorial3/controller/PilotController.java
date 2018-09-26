package com.apap.tutorial3.controller;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial3.model.PilotModel;
import com.apap.tutorial3.service.PilotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/pilot/add")
	public String add (@RequestParam(value = "id", required = true) String id,
					   @RequestParam(value = "licenseNumber", required = true) String licenseNumber,
					   @RequestParam(value = "name", required = true) String name,
					   @RequestParam(value = "flyHour", required = true) int flyHour) {
		PilotModel pilot = new PilotModel(id, licenseNumber, name, flyHour);
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping("/pilot/view")
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("pilot", archive);
		return "view-pilot";
	}
	
	@RequestMapping("/pilot/viewall")
	public String viewall(Model model) {
		List<PilotModel> archive = pilotService.getPilotList();
		
		model.addAttribute("listPilot", archive);
		return "viewall-pilot";
	}
	
	@RequestMapping({"/pilot/view/license-number/{licenseNumber}" ,"/pilot/view/license-number/", "/pilot/view/license-number"})
	public String viewPilotPath(@PathVariable(name = "licenseNumber", required = false) String licenseNumber, Model model){
		String licenseNum = StringUtils.isEmpty(licenseNumber) ? "empty" : licenseNumber;
		if (licenseNum.equals("empty")) {
			model.addAttribute("error", "license-number-missing");
			return "error-page";
		} else {
			try {
				PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
				System.out.println(archive.getLicenseNumber());	
				model.addAttribute("pilot", archive);
				return "view-pilot";
			} catch (NullPointerException e) {
				System.out.println("error");
				model.addAttribute("error", "not-found-license-number");
				return "not-found";
			}
		}	
	}
	
	@RequestMapping({"/pilot/update/license-number/{licenseNumber}/fly-hour/{newFlyHour}", "/pilot/update/license-number/fly-hour/{newFlyHour}"})
	public String updateFlyHour(@PathVariable(name = "licenseNumber", required = false) String licenseNumber, 
			@PathVariable(name = "newFlyHour", required = false) int newFlyHour, Model model) {
		String licenseNum = StringUtils.isEmpty(licenseNumber) ? "empty" : licenseNumber;
		if (licenseNum.equals("empty")) {
			model.addAttribute("error", "license-number-missing");
			return "error-page";
		} else {
			try {
				PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
				archive.setFlyHour(newFlyHour);
				model.addAttribute("succeed", "update-flyHour");
				return "succeed";
			} catch (NullPointerException e) {
				System.out.println("error");
				model.addAttribute("error", "not-found-license-number");
				return "not-found";
			}
		}
	}
	
	@RequestMapping({"/pilot/delete/id/{id}", "/pilot/delete/id/", "/pilot/delete/id"})
	public String deletePilot(@PathVariable(name = "id", required = false) String id, Model model) {
		String idPilot = StringUtils.isEmpty(id) ? "empty" : id;
		if (idPilot.equals("empty")) {
			model.addAttribute("error", "id-missing");
			return "error-page";
		} else {
			try {
				PilotModel archive = pilotService.getPilotDetailByID(id);
				pilotService.deletePilotByID(id);
				model.addAttribute("succeed", "delete-pilot");
				return "succeed";
			} catch (NullPointerException e) {
				System.out.println("error");
				model.addAttribute("error", "not-found-id");
				return "not-found";
			}
		}
	}
}
