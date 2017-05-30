/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tomasulodisplay;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import assembly.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author meepo
 */

public class TomasuloDisplay extends Application {
	// 须在start函数最后把部件加到显示区域
	public InstQueue instqueue = new InstQueue();
	public MemBufQueue loadqueue = new MemBufQueue("Load", 3);
	public MemBufQueue storequeue = new MemBufQueue("Store", 3);
	public RegQueue regqueue = new RegQueue(10);
	public CalcQueue addqueue = new CalcQueue("Add", 3);
	public CalcQueue mulqueue = new CalcQueue("Mul", 2);
	public FlowMemQueue  memqueue = new FlowMemQueue();

	public static final int allheight = 700;
	public static final int allwidth = 1000;

	private Controller con = null;
	private Timeline timer = null;
	private Label cycCount = null;
	private Stage primaryStage = null;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Tomasulo算法演示模拟平台");
		this.primaryStage = primaryStage;
		// primaryStage.setWidth(allwidth);
		// primaryStage.setHeight(allheight);

		// Init ListBox
		Label instlabel = new Label("指令队列");
		instlabel.setFont(new Font("Tahoma", 20));
		VBox instBox = new VBox();
		instBox.setSpacing(5);
		instBox.setPadding(new Insets(10, 10, 10, 10));
		instBox.getChildren().addAll(instlabel, instqueue);

		Label loadlabel = new Label("Load 队列");
		loadlabel.setFont(new Font("Tahoma", 20));
		VBox LoadBox = new VBox();
		LoadBox.setSpacing(5);
		LoadBox.setPadding(new Insets(10, 10, 10, 10));
		LoadBox.getChildren().addAll(loadlabel, loadqueue);

		Label storelabel = new Label("Store 队列");
		storelabel.setFont(new Font("Tahoma", 20));
		VBox StoreBox = new VBox();
		StoreBox.setSpacing(5);
		StoreBox.setPadding(new Insets(10, 10, 10, 10));
		StoreBox.getChildren().addAll(storelabel, storequeue);

		Label addlabel = new Label("加减保留站");
		addlabel.setFont(new Font("Tahoma", 20));
		VBox AddBox = new VBox();
		AddBox.setSpacing(5);
		AddBox.getChildren().addAll(addlabel, addqueue);
		AddBox.setPadding(new Insets(0, 20, 0, 0));

		Label mullabel = new Label("乘除保留站");
		mullabel.setFont(new Font("Tahoma", 20));
		VBox MulBox = new VBox();
		MulBox.setSpacing(5);
		MulBox.getChildren().addAll(mullabel, mulqueue);
		
		Label reglabel = new Label("寄存器堆");
		reglabel.setFont(new Font("Tahoma", 20));
		VBox RegBox = new VBox();
		RegBox.setSpacing(5);
		RegBox.setPadding(new Insets(10, 10, 10, 10));
		RegBox.getChildren().addAll(reglabel, regqueue);
		
		Label memlabel = new Label("内存");
		memlabel.setFont(new Font("Tahoma", 20));
		VBox MemBox = new VBox();
		MemBox.setSpacing(5);
		MemBox.setPadding(new Insets(10, 10, 10, 10));
		MemBox.getChildren().addAll(memlabel, memqueue);

		HBox bl = new HBox();
		GridPane ButtonList = initButtons();
		Label cyclabel = new Label("Cycle:");
		cycCount = new Label("");
		bl.getChildren().addAll(ButtonList, cyclabel, cycCount);

		con = new Controller();
		update(con.log());

		// Insets top right bottom left
		HBox root = new HBox();
		VBox l1 = new VBox();
		l1.getChildren().addAll(bl, LoadBox, StoreBox);
		l1.setPadding(new Insets(0, 30, 0, 0));

		VBox l2 = new VBox();
		l2.getChildren().addAll(instBox, AddBox);
		l2.setPadding(new Insets(0, 30, 0, 0));
		
		VBox l3 = new VBox();
		l3.getChildren().addAll(MemBox, MulBox);
		l3.setPadding(new Insets(0, 30, 0, 0));

		root.getChildren().addAll(l1, l2, l3, RegBox);
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private GridPane initButtons() {
		// Init Buttons
		GridPane ButtonList = new GridPane();
		String ButtonStyle = "-fx-text-fill: white; " + "-fx-font: 15 arial;"
				+ "-fx-font-weight: bold;-fx-background-color: linear-gradient(#61a2b1, #2A5058);"
				+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );";
		Button LoadButton = new Button("Load");
		Button InputButton = new Button("Input");
		Button ResetButton = new Button("Reset");
		Button StartButton = new Button("Start");
		Button NextButton = new Button("Next");
		Button StopButton = new Button("Stop");

		LoadButton.setStyle(ButtonStyle);
		LoadButton.setOnAction((ActionEvent e) -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("打开指令文件");
			File file = fileChooser.showOpenDialog(primaryStage);
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				ArrayList<String> list = new ArrayList<String>();
				for (String line; (line = reader.readLine()) != null;)
					list.add(line);
				con.addIns(list);
				update(con.log());
				reader.close();
				StartButton.setDisable(false);
				NextButton.setDisable(false);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		InputButton.setStyle(ButtonStyle);
		InputButton.setOnAction((ActionEvent e) -> {
			Stage window = new Stage();
			window.setTitle("输入指令");
			window.initModality(Modality.APPLICATION_MODAL);
			TextArea input = new TextArea();
			Button OK = new Button("OK");
			Button cancel = new Button("Cancel");
			OK.setOnAction((ActionEvent e1) -> {
				try {
					BufferedReader reader = new BufferedReader(new StringReader(input.getText()));
					ArrayList<String> list = new ArrayList<String>();
					for (String line; (line = reader.readLine()) != null;)
						list.add(line);
					con.addIns(list);
					update(con.log());
					StartButton.setDisable(false);
					NextButton.setDisable(false);
					reader.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				window.close();
			});
			cancel.setOnAction((ActionEvent e1) -> window.close());
			VBox layout = new VBox();
			HBox buttons = new HBox();
			buttons.getChildren().addAll(OK, cancel);
			layout.getChildren().addAll(input, buttons);
			Scene scene = new Scene(layout);
			window.setScene(scene);
			window.showAndWait();
		});

		ResetButton.setStyle(ButtonStyle);
		ResetButton.setOnAction((ActionEvent e) -> {
			con.reset();
			update(con.log());
			StartButton.setDisable(true);
			NextButton.setDisable(true);
		});

		StartButton.setStyle(ButtonStyle);
		StartButton.setDisable(true);
		StartButton.setOnAction((ActionEvent e) -> {
			LoadButton.setDisable(true);
			InputButton.setDisable(true);
			StartButton.setDisable(true);
			ResetButton.setDisable(true);
			StartButton.setDisable(true);
			NextButton.setDisable(true);
			StopButton.setDisable(false);
			timer = new Timeline();
			timer.setCycleCount(Timeline.INDEFINITE);
			timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					con.run();
					update(con.log());
					if (con.isFinish()) {
						LoadButton.setDisable(false);
						InputButton.setDisable(false);
						StartButton.setDisable(true);
						ResetButton.setDisable(true);
						StopButton.setDisable(true);
						timer.pause();
					}
				}
			}));
			timer.play();
		});

		NextButton.setStyle(ButtonStyle);
		NextButton.setDisable(true);
		NextButton.setOnAction((ActionEvent e) -> {
			con.run();
			update(con.log());
			if (con.isFinish()) {
				LoadButton.setDisable(false);
				InputButton.setDisable(false);
				StartButton.setDisable(false);
				ResetButton.setDisable(false);
				StartButton.setDisable(true);
				NextButton.setDisable(true);
			}
		});

		StopButton.setStyle(ButtonStyle);
		StopButton.setDisable(true);
		StopButton.setOnAction((ActionEvent e) -> {
			LoadButton.setDisable(false);
			InputButton.setDisable(false);
			StartButton.setDisable(false);
			ResetButton.setDisable(false);
			StartButton.setDisable(false);
			NextButton.setDisable(false);
			StopButton.setDisable(true);
			timer.pause();
		});

		ButtonList.add(LoadButton, 0, 0);
		ButtonList.add(InputButton, 1, 0);
		ButtonList.add(ResetButton, 2, 0);
		ButtonList.add(StartButton, 0, 1);
		ButtonList.add(NextButton, 1, 1);
		ButtonList.add(StopButton, 2, 1);
		ButtonList.setPadding(new Insets(0, 0, 30, 0));
		return ButtonList;
	}

	void update(HashMap<String, String[][]> logs) {
		cycCount.setText(Integer.toString(con.getCycle()));
		instqueue.setData(logs.get("Inst"));
		loadqueue.setData(logs.get("Load"));
		storequeue.setData(logs.get("Store"));
		regqueue.setData(logs.get("Reg"));
		addqueue.setData(logs.get("Add"));
		mulqueue.setData(logs.get("Mult"));
		memqueue.setData(logs.get("Mem"));
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
