package fussball;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.ds.PGSimpleDataSource;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class verbindung extends Application {
	private boolean retarded;
	private Connection con;
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		retarded = false;
		primaryStage.setTitle("Fussballverein");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);

		TextField user = new TextField("postgres");
		grid.add(user, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);

		Label dbname = new Label("Datenbank:");
		grid.add(dbname, 0, 3);

		TextField dbBox = new TextField("fussball");
		grid.add(dbBox, 1, 3);

		Label hostadr = new Label("Hostadresse:");
		grid.add(hostadr, 0, 4);

		TextField hostBox = new TextField("127.0.0.1");
		grid.add(hostBox, 1, 4);

		Label port = new Label("Port:");
		grid.add(port, 0, 5);

		TextField portBox = new TextField("5432");
		grid.add(portBox, 1, 5);

		Button btn = new Button("Connect");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 6);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 1, 7);

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!retarded) {
					try {
						Class.forName("org.postgresql.Driver").newInstance();
						System.out.println("postgresql JDBC Driver Registered!");
						PGSimpleDataSource ds = new PGSimpleDataSource();
						ds.setServerName("localhost");
						ds.setDatabaseName("fussball");
						ds.setUser("postgres");
						ds.setPassword("postgres");
						con = ds.getConnection();
						System.out.println("Opened database successfully");
						actiontarget.setFill(Color.FIREBRICK);
						btn.setText("Disconnect");
						retarded=true;
					} catch (SQLException e2) {
						e2.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else{
					retarded=false;
					btn.setText("Connect");
					try {
						con.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}finally{
						System.out.println("Disconnected");
					}
					
				}
			}
		});

		Scene scene = new Scene(grid, 300, 275);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}