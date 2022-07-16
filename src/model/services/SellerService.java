package model.services;

import java.util.List;

import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {
	
	
	private SellerDao dao = DaoFactory.createSellerDao();
	
	
	public List<Seller> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Seller department) {
		if(department.getId() == null) {
			dao.insert(department);
			Alerts.showAlert("Insert Success!", null, "Seller inserted with success!", AlertType.CONFIRMATION);
		}
		else {
			dao.update(department);
			Alerts.showAlert("Update Success!", null, "Seller updated with success!", AlertType.CONFIRMATION);
		}
	}
	
	public void remove(Seller department) {
		dao.deleteById(department.getId());
	}
}
