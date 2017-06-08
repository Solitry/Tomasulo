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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
        public static final double widthrate = 19.0/20;
        public static final double heightrate = 18.0/20;
        
        static String ButtonStyle = "-fx-text-fill: white; " + "-fx-font: 15 arial;"
			+ "-fx-font-weight: bold;-fx-background-color: linear-gradient(#61a2b1, #2A5058);"
			+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );";
        
	// 须在start函数最后把部件加到显示区域
	public InstQueue instqueue = new InstQueue();
	public MemBufQueue loadqueue = new MemBufQueue("Load", 3);

	public MemBufQueue storequeue = new MemBufQueue("Store", 3);
	public RegQueue regqueue = new RegQueue(32);
	public CalcQueue addqueue = new CalcQueue("Add", 3);
	public CalcQueue mulqueue = new CalcQueue("Mul", 2);
	public FlowMemQueue memqueue = new FlowMemQueue();
	public AsmLineQueue alaQueue = new AsmLineQueue(2);
	public AsmLineQueue mlaQueue = new AsmLineQueue(6);
	public Rectangle Bus;
	public static int allheight;// = 800;
	public static int allwidth;// = 1450;
	public static double lineDelta = 20;
	public static double goldrate = 0.618;
	private Controller con = null;
	private Timeline timer = null;
	private Label cycCount = null;
	private Stage primaryStage = null;
	private HashMap<String, String[][]> logs = null;
	public MoveLine inst2add, inst2mul, add2add, mul2mul, inst2load, inst2store;
	public MoveLine add2bus, mul2bus, bus2reg, load2bus;

	private int[] tabelHeight = { 0, 60, 88, 116 };

	@Override
	public void start(Stage primaryStage) {
                
                primaryStage.setTitle("Tomasulo算法演示模拟平台");
		this.primaryStage = primaryStage;

		// Init ListBox
		Label instlabel = new Label("指令队列");
		instlabel.setFont(new Font("Tahoma", 20));
		VBox instBox = new VBox();
		instBox.setSpacing(5);
		instBox.getChildren().addAll(instlabel, instqueue);
		instBox.setPadding(new Insets(10, 10, 10, 10));

		Label loadlabel = new Label("Load 队列");
		loadlabel.setFont(new Font("Tahoma", 20));
		loadqueue.setMaxHeight(tabelHeight[3]);
		VBox LoadBox = new VBox();
		LoadBox.setSpacing(5);
		LoadBox.getChildren().addAll(loadlabel, loadqueue);
		LoadBox.setPadding(new Insets(50, 50, 10, 10));

		Label storelabel = new Label("Store 队列");
		storelabel.setFont(new Font("Tahoma", 20));
		storequeue.setMaxHeight(tabelHeight[3]);
		VBox StoreBox = new VBox();
		StoreBox.setSpacing(5);
		StoreBox.setPadding(new Insets(10, 50, 10, 10));
		StoreBox.getChildren().addAll(storelabel, storequeue);

		Label addlabel = new Label("加减保留站");
		addlabel.setFont(new Font("Tahoma", 20));
		addqueue.setMaxHeight(tabelHeight[3]);
		VBox AddBox = new VBox();
		AddBox.setSpacing(5);
		AddBox.getChildren().addAll(addlabel, addqueue);
		AddBox.setPadding(new Insets(50, 10, 10, 10));

		Label alaLabel = new Label("加法器");
		alaLabel.setFont(new Font("Tahoma", 20));
		alaQueue.setMaxHeight(tabelHeight[1]);
		VBox alaBox = new VBox();
		alaBox.setSpacing(5);
		alaBox.getChildren().addAll(alaLabel, alaQueue);
		alaBox.setPadding(new Insets(30, 10, 10, 10));

		Label mullabel = new Label("乘除保留站");
		mullabel.setFont(new Font("Tahoma", 20));
		mulqueue.setMaxHeight(tabelHeight[2]);
		VBox MulBox = new VBox();
		MulBox.setSpacing(5);
		MulBox.getChildren().addAll(mullabel, mulqueue);
		MulBox.setPadding(new Insets(50, 10, 10, 10));

		Label mlaLabel = new Label("乘法器");
		mlaLabel.setFont(new Font("Tahoma", 20));
		mlaQueue.setMaxHeight(tabelHeight[1]);
		VBox mlaBox = new VBox();
		mlaBox.setSpacing(5);
		mlaBox.getChildren().addAll(mlaLabel, mlaQueue);
		mlaBox.setPadding(new Insets(50, 10, 10, 10));

		Label reglabel = new Label("寄存器堆");
		reglabel.setFont(new Font("Tahoma", 20));
		regqueue.setMinWidth(allwidth / 11);
		VBox RegBox = new VBox();
		RegBox.setSpacing(5);
		RegBox.getChildren().addAll(reglabel, regqueue);
		RegBox.setPadding(new Insets(10, 10, 10, 10));

		Label memlabel = new Label("内存");
		Button memButton = new Button("+");
		memButton.setOnAction((ActionEvent e) -> {
			Stage window = new Stage();
			window.setTitle("添加内存");
			window.initModality(Modality.APPLICATION_MODAL);
			Label addrLabel = new Label("Addr");
			TextField addr = new TextField();
			Label valLabel = new Label("val");
			TextField val = new TextField();
			Button OK = new Button("OK");
			Button cancel = new Button("Cancel");
			OK.setOnAction((ActionEvent e1) -> {
				if (addr.getText() != "" && val.getText() != "") {
					con.setMem(Integer.parseInt(addr.getText()), Double.parseDouble(val.getText()));
					update(con.log());
				}
				window.close();
			});
			cancel.setOnAction((ActionEvent e1) -> window.close());
			VBox layout = new VBox();
			HBox inputs = new HBox();
			HBox buttons = new HBox();
			inputs.getChildren().addAll(addrLabel, addr, valLabel, val);
			buttons.getChildren().addAll(OK, cancel);
			layout.getChildren().addAll(inputs, buttons);
			Scene scene = new Scene(layout);
			window.setScene(scene);
			window.showAndWait();
		});
		memlabel.setFont(new Font("Tahoma", 20));
		VBox MemBox = new VBox();
		HBox MemInput = new HBox();
		MemInput.getChildren().addAll(memlabel, memButton);
		MemBox.setSpacing(5);
		MemBox.setPadding(new Insets(10, 10, 10, 10));
		MemBox.getChildren().addAll(MemInput, memqueue);

		HBox bl = new HBox();
		GridPane ButtonList = initButtons();
		Label cyclabel = new Label("  Cycle:");
		cyclabel.setFont(new Font("Tahoma", 17));
		cycCount = new Label("");
		cycCount.setFont(new Font("Tahoma", 17));
		bl.getChildren().addAll(ButtonList, cyclabel, cycCount);

		inst2add = new MoveLine(0);
		inst2mul = new MoveLine(0);
		add2add = new MoveLine(0);
		mul2mul = new MoveLine(0);
		inst2load = new MoveLine(0);
		inst2store = new MoveLine(0);
		add2bus = new MoveLine(1);
		mul2bus = new MoveLine(1);
		bus2reg = new MoveLine(2);
		load2bus = new MoveLine(1);
		con = new Controller(new MoveLine[] { inst2add, inst2mul, add2add, mul2mul, inst2load, inst2store, add2bus,
				mul2bus, bus2reg, load2bus });

		// Insets top right bottom left
		VBox updownroot = new VBox();
		HBox root = new HBox();

		VBox l1 = new VBox();
		l1.getChildren().addAll(bl, StoreBox, LoadBox);
		l1.setPadding(new Insets(10, 10, 10, 30));

		VBox l2 = new VBox();
		l2.getChildren().addAll(instBox, AddBox, alaBox);
		l2.setPadding(new Insets(10, 10, 10, 10));

		VBox l3 = new VBox();
		l3.getChildren().addAll(MemBox, MulBox, mlaBox);
		l3.setPadding(new Insets(10, 10, 10, 10));

		StackPane buspane = new StackPane();
		Bus = new Rectangle(allwidth, allheight / 20, Color.WHITE);
		Bus.setStroke(Color.GRAY);
		Bus.strokeWidthProperty().set(3);
		Label buslabel = new Label("总线");
		buslabel.setFont(new Font("Tahoma", 25));
		buspane.getChildren().addAll(Bus, buslabel);
		buspane.setPadding(new Insets(50, 0, 0, 0));
		root.getChildren().addAll(l1, l2, l3, RegBox);
                
                //Region updownspring = new Region();
                //VBox.setVgrow(updownspring, Priority.ALWAYS);
                updownroot.setSpacing(50);
		updownroot.getChildren().addAll(root,buspane);
		updownroot.setPrefHeight(allheight);
		updownroot.setPrefWidth(allwidth);
		// Scene scene = new Scene(root);
		Scene scene = new Scene(new Group());
		((Group) scene.getRoot()).getChildren().addAll(add2bus, mul2bus, bus2reg, load2bus,inst2add, inst2mul, add2add, mul2mul, inst2load,
				inst2store);

                ((Group) scene.getRoot()).getChildren().addAll(updownroot);

		primaryStage.setScene(scene);
		primaryStage.show();

		// Draw Line
		double x1 = instqueue.getLayoutX() + instBox.getLayoutX() + l2.getLayoutX()
				+ instqueue.getWidth() * (1 - goldrate);
		double y1 = instqueue.getLayoutY() + instBox.getLayoutY() + l2.getLayoutY() + instqueue.getHeight();
		double x2;
		double y2 = addqueue.getLayoutY() + AddBox.getLayoutY() + l2.getLayoutY();// +addqueue.getHeight();
                int movedelta = 10;
		inst2add.update(new Double[] { x1, y1 + 0, x1, y2 + movedelta, });

		x1 = instqueue.getLayoutX() + instBox.getLayoutX() + l2.getLayoutX() + instqueue.getWidth() * goldrate;
		y1 = instqueue.getLayoutY() + instBox.getLayoutY() + l2.getLayoutY() + instqueue.getHeight();
		x2 = mulqueue.getLayoutX() + MulBox.getLayoutX() + l3.getLayoutX() + mulqueue.getWidth() * (1 - goldrate);
		y2 = mulqueue.getLayoutY() + MulBox.getLayoutY() + l3.getLayoutY();

		inst2mul.update(new Double[] { x1, y1 + 0, x1, y1 + (y2 - y1) * (1 - goldrate), x2,
				y1 + (y2 - y1) * (1 - goldrate), x2, y2 + movedelta, });

		x1 = addqueue.getLayoutX() + AddBox.getLayoutX() + l2.getLayoutX() + addqueue.getWidth() * (1 - goldrate);
		y1 = addqueue.getLayoutY() + AddBox.getLayoutY() + l2.getLayoutY() + addqueue.getHeight();
		y2 = alaQueue.getLayoutY() + alaBox.getLayoutY() + l2.getLayoutY();

		add2add.update(new Double[] { x1, y1 + 0, x1, y2 + movedelta, });

		x1 = mulqueue.getLayoutX() + MulBox.getLayoutX() + l3.getLayoutX() + mulqueue.getWidth() * (1 - goldrate);
		y1 = mulqueue.getLayoutY() + MulBox.getLayoutY() + l3.getLayoutY() + mulqueue.getHeight();
		y2 = mlaQueue.getLayoutY() + mlaBox.getLayoutY() + l3.getLayoutY();

		mul2mul.update(new Double[] { x1, y1 + 0, x1, y2 + movedelta, });

		x1 = instqueue.getLayoutX() + instBox.getLayoutX() + l2.getLayoutX();
		y1 = instqueue.getLayoutY() + instBox.getLayoutY() + l2.getLayoutY() + instqueue.getHeight() * (goldrate);
		x2 = loadqueue.getLayoutX() + LoadBox.getLayoutX() + l1.getLayoutX() + loadqueue.getWidth();
		y2 = loadqueue.getLayoutY() + LoadBox.getLayoutY() + l1.getLayoutY() + loadqueue.getHeight() * (goldrate);

		inst2load.update(new Double[] { x1 - 0, y1, x1 - (x1 - x2) * (1 - goldrate), y1,
				x1 - (x1 - x2) * (1 - goldrate), y2, x2 - movedelta, y2, });

		x1 = instqueue.getLayoutX() + instBox.getLayoutX() + l2.getLayoutX();
		y1 = instqueue.getLayoutY() + instBox.getLayoutY() + l2.getLayoutY() + instqueue.getHeight() * (1 - goldrate);
		x2 = storequeue.getLayoutX() + StoreBox.getLayoutX() + l1.getLayoutX() + storequeue.getWidth();
		y2 = storequeue.getLayoutY() + StoreBox.getLayoutY() + l1.getLayoutY()
				+ storequeue.getHeight() * (1 - goldrate);

		inst2store.update(new Double[] { x1 - 0, y1, x1 - (x1 - x2) * (goldrate), y1, x1 - (x1 - x2) * (goldrate), y2,
				x2 - movedelta, y2, });

		x1 = alaQueue.getLayoutX() + alaBox.getLayoutX() + l2.getLayoutX() + alaQueue.getWidth() * (1 - goldrate);
		y1 = alaQueue.getLayoutY() + alaBox.getLayoutY() + l2.getLayoutY() + alaQueue.getHeight();
		y2 = Bus.getLayoutY() + buspane.getLayoutY() + l1.getLayoutY();

		add2bus.update(new Double[] { x1, y1 + 0, x1, y2 + movedelta, });

		x1 = mlaQueue.getLayoutX() + mlaBox.getLayoutX() + l3.getLayoutX() + mlaQueue.getWidth() * (1 - goldrate);
		y1 = mlaQueue.getLayoutY() + mlaBox.getLayoutY() + l3.getLayoutY() + mlaQueue.getHeight();
		y2 = Bus.getLayoutY() + buspane.getLayoutY() + l1.getLayoutY();

		mul2bus.update(new Double[] { x1, y1 + 0, x1, y2 + movedelta, });

		x1 = regqueue.getLayoutX() + RegBox.getLayoutX() + regqueue.getWidth() * (1 - goldrate);
		y1 = regqueue.getLayoutY() + RegBox.getLayoutY() + regqueue.getHeight();
		y2 = Bus.getLayoutY() + buspane.getLayoutY() + l1.getLayoutY();

		bus2reg.update(new Double[] { x1, y2 + movedelta, x1, y1 - movedelta, });

		x1 = loadqueue.getLayoutX() + LoadBox.getLayoutX() + l1.getLayoutX() + storequeue.getWidth() * (1 - goldrate);
		y1 = loadqueue.getLayoutY() + LoadBox.getLayoutY() + l1.getLayoutY() + loadqueue.getHeight();
		y2 = Bus.getLayoutY() + buspane.getLayoutY() + l1.getLayoutY();

		load2bus.update(new Double[] { x1, y1 + 0, x1, y2 + movedelta, });

	}

	private GridPane initButtons() {
		// Init Buttons
		GridPane ButtonList = new GridPane();
		Button LoadButton = new Button("Load");
		Button InputButton = new Button("Input");
		Button ResetButton = new Button("Reset");
		Button StartButton = new Button("Start");
		Button NextButton = new Button("Next");
		Button StopButton = new Button("Stop");
		int buttonwidth = allwidth / 22;
		LoadButton.setPrefWidth(buttonwidth);
		InputButton.setPrefWidth(buttonwidth);
		ResetButton.setPrefWidth(buttonwidth);
		StartButton.setPrefWidth(buttonwidth);
		NextButton.setPrefWidth(buttonwidth);
		StopButton.setPrefWidth(buttonwidth);

		LoadButton.setStyle(ButtonStyle);
		LoadButton.setOnAction((ActionEvent e) -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("打开指令文件");
			File file = fileChooser.showOpenDialog(primaryStage);
			try {
				if (file != null && file.exists()) {
					BufferedReader reader = new BufferedReader(new FileReader(file));
					ArrayList<String> list = new ArrayList<String>();
					for (String line; (line = reader.readLine()) != null;)
						list.add(line);
					con.addIns(list);
					update(con.log());
					reader.close();
					StartButton.setDisable(false);
					NextButton.setDisable(false);
				}
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
			input.setText(getList());
			Button OK = new Button("OK");
			Button cancel = new Button("Cancel");
			OK.setOnAction((ActionEvent e1) -> {
				try {
					System.out.println(input.getText());
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
			StartButton.setDisable(false);
			NextButton.setDisable(false);
		});

		StartButton.setStyle(ButtonStyle);
		StartButton.setDisable(true);
		StartButton.setOnAction((ActionEvent e) -> {
			LoadButton.setDisable(true);
			InputButton.setDisable(true);
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
						ResetButton.setDisable(false);
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
		this.logs = logs;
		cycCount.setText(Integer.toString(con.getCycle()));
		instqueue.setData(logs.get("Inst"));
		loadqueue.setData(logs.get("Load"));
		storequeue.setData(logs.get("Store"));
		regqueue.setData(logs.get("Reg"));
		addqueue.setData(logs.get("Add"));
		mulqueue.setData(logs.get("Mult"));
		memqueue.setData(logs.get("Mem"));
		alaQueue.setData(logs.get("Add"));
		mlaQueue.setData(logs.get("Mult"));
	}

	String getList() {
		String list = "";
		if (logs != null && logs.containsKey("Inst")) {
			String[][] args = logs.get("Inst");
			for (String[] ins : args)
				list += ins[1] + "\n";
		}
		return list;
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
        
	public static void main(String[] args) {
	        int screenWidth=((int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().width);
                int screenHeight = ((int)java.awt.Toolkit.getDefaultToolkit().getScreenSize().height); 
		allheight = (int)(screenHeight*heightrate);
                allwidth = (int)(screenWidth*widthrate);

                launch(args);
	}
}
