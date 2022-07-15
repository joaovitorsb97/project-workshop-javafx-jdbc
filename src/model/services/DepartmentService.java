package model.services;

import java.util.List;

import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	
	private DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	
	public List<Department> findAll(){
		return dao.findAll();
	}
	
	public void saveOrUpdate(Department department) {
		if(department.getId() == null) {
			dao.insert(department);
			Alerts.showAlert("Insert Success!", null, "Department inserted with success!", AlertType.CONFIRMATION);
		}
		else {
			dao.update(department);
			Alerts.showAlert("Update Success!", null, "Department updated with success!", AlertType.CONFIRMATION);
		}
	}
}
