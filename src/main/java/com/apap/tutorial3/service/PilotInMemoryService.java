package com.apap.tutorial3.service;

import java.util.ArrayList;
import java.util.List;

import com.apap.tutorial3.model.PilotModel;
import org.springframework.stereotype.Service;

@Service
public class PilotInMemoryService implements PilotService {
	private List<PilotModel> archivePilot;
	
	public PilotInMemoryService() {
		archivePilot = new ArrayList<>();
	}
	
	@Override
	public void addPilot(PilotModel pilot) {
		archivePilot.add(pilot);
	}
	
	@Override
	public List<PilotModel> getPilotList() {
		return archivePilot;
	}

	@Override
	public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
		for (PilotModel pilot : this.getPilotList()) {
			if (pilot.getLicenseNumber().equals(licenseNumber)) {
				return pilot;
			}
		}
		return null;
	}

	@Override
	public PilotModel getPilotDetailByID(String ID) {
		for (PilotModel pilot : this.getPilotList()) {
			if (pilot.getId().equals(ID)) {
				return pilot;
			}
		}
		return null;
	}
	
	@Override
	public void deletePilotByID(String ID) {
		for (PilotModel pilot : this.getPilotList()) {
			if (pilot.getId().equals(ID)) {
				this.getPilotList().remove(pilot);
			}
		}
	}
}
