package com.apap.tutorial3.service;

import java.util.List;

import com.apap.tutorial3.model.PilotModel;

public interface PilotService {
	void addPilot (PilotModel pilot);
	void deletePilotByID (String ID);
	List<PilotModel> getPilotList();
	PilotModel getPilotDetailByLicenseNumber(String licenseNumber);
	PilotModel getPilotDetailByID(String ID);
}
