package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exceptions.ValidationException;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private Department department;

	private DepartmentService service;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;
	@FXML
	private TextField txtName;
	@FXML
	private Label labelError;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnCancel;

	@FXML
	public void onBtnSaveAction(ActionEvent event) {
		if (department == null || service == null) {
			throw new IllegalStateException("Department or Service was null!");
		}
		try {
			department = getFormData();
			service.saveOrUpdate(department);
			Alerts.showAlert("Success!", null, "Department inserted with success!", AlertType.CONFIRMATION);
			notifyDataChangeListener();
			Utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error to save department data", null, e.getMessage(), AlertType.ERROR);
		}catch(ValidationException e) {
			setLabelError(e.getErrors());
		}
	}

	private void notifyDataChangeListener() {
		for (DataChangeListener listeners : dataChangeListeners) {
			listeners.onDataChanged();
		}

	}

	private Department getFormData() {
		Department department = new Department();

		ValidationException exception = new ValidationException("Validation Error");

		department.setId(Utils.tryParseToInt(txtId.getText()));

		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addErrors("name", "Field name can't be empty");
		}
		
		department.setName(txtName.getText());
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		
		return department;
	}

	@FXML
	public void onBtnCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	public void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 40);
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	public void subscribeDataChangeListener(DataChangeListener list) {
		dataChangeListeners.add(list);
	}

	public void updateFormData() {
		txtId.setText(String.valueOf(department.getId()));
		txtName.setText(department.getName());
	}
	
	public void setLabelError(Map<String, String> errors) {
		Set<String> errorLabel = errors.keySet();
		
		if(errorLabel.contains("name")) {
			labelError.setText(errors.get("name"));
		}
	}

}
