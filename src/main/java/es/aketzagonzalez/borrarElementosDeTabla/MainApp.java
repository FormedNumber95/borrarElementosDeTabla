package es.aketzagonzalez.borrarElementosDeTabla;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Arrays;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import static javafx.scene.control.TableView.TableViewSelectionModel;

import java.io.IOException;


/**
 * Clase MainApp.
 */
public class MainApp extends Application {
	
	   /** El field del nombre. */
   	private TextField fNameField;
	    
    	/** El field del apellido. */
    	private TextField lNameField;
	    
    	/** El DatePicker del apellido. */
    	private DatePicker dobField;
	    
    	/** La tabla. */
    	private TableView<Person> table;

    /**
     * Metodo que crea el stage y lo mustra.
     *
     * @param stage El stage
     * @throws IOException Seniala que hay un error de I/O.
     */
    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
    	fNameField = new TextField();
        lNameField = new TextField();
        dobField = new DatePicker();
        table = new TableView<>(PersonTableUtil.getPersonList());
        // Turn on multi-row selection for the TableView
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.MULTIPLE);
        // Add columns to the TableView
        table.getColumns().addAll(PersonTableUtil.getIdColumn(), PersonTableUtil.getFirstNameColumn(), PersonTableUtil.getLastNameColumn(), PersonTableUtil.getBirthDateColumn());
        GridPane newDataPane  = this.getNewPersonDataPane();
        Button restoreBtn = new Button("Restore Rows");
        restoreBtn.setOnAction(e -> restoreRows());
        Button deleteBtn = new Button("Delete Selected Rows");
        deleteBtn.setOnAction(e -> deleteSelectedRows());
        VBox root = new VBox(newDataPane, new HBox(restoreBtn, deleteBtn), table);
        root.setSpacing(5);
        root.setStyle("-fx-padding: 10;" + 
                      "-fx-border-style: solid inside;" + 
                      "-fx-border-width: 2;" +
                      "-fx-border-insets: 5;" + 
                      "-fx-border-radius: 5;" + 
                      "-fx-border-color: blue;");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Adding/Deleting Rows in a TableViews");
        stage.show();
    }

    /**
     * Consigue el panel de informacion de la nueva persona.
     *
     * @return El panel de informacion de la nueva persona
     */
    public GridPane getNewPersonDataPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(5);
        pane.addRow(0, new Label("First Name:"), fNameField);
        pane.addRow(1, new Label("Last Name:"), lNameField);
        pane.addRow(2, new Label("Birth Date:"), dobField);
        Button addBtn = new Button("Add");
        addBtn.setOnAction(e -> addPerson());
        // Add the "Add" button
        pane.add(addBtn, 2, 0);
        return pane;
    }
    
    /**
     * Borra las lineas seleccionadas.
     */
    public void deleteSelectedRows() {  
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        if (tsm.isEmpty()) {
            System.out.println("Please select a row to delete.");
            return;
        }
        ObservableList<Integer> list = tsm.getSelectedIndices();
        Integer[] selectedIndices = new Integer[list.size()];
        selectedIndices = list.toArray(selectedIndices);
        // Sort the array
        Arrays.sort(selectedIndices);
        // Delete rows (last to first)
        for(int i = selectedIndices.length - 1; i >= 0; i--) {
            tsm.clearSelection(selectedIndices[i].intValue());
            table.getItems().remove(selectedIndices[i].intValue());
        }
    }
    
    /**
     * Restaura las filas.
     */
    public void restoreRows() {
        table.getItems().clear();
        table.getItems().addAll(PersonTableUtil.getPersonList());
    }

    /**
     * Getter de una persona .
     *
     * @return La persona
     */
    public Person getPerson() {
        return new Person(fNameField.getText(), lNameField.getText(), dobField.getValue());
    }

    /**
     * Aniade una persona.
     */
    public void addPerson() {
        Person p = getPerson();
        table.getItems().add(p);
        clearFields();
    }

    /**
     * Borra los campos.
     */
    public void clearFields() {
        fNameField.setText(null);
        lNameField.setText(null);
        dobField.setValue(null);
    }
    
    /**
     * Metodo main donde solo se ejecuta el launch.
     *
     * @param args los argumentos que se pasan por consola, ninguno
     */
    public static void main(String[] args) {
        launch(args);
    }

}
