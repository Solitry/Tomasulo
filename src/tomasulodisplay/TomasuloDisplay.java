/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tomasulodisplay;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author meepo
 */

public class TomasuloDisplay extends Application {
	// 须在start函数最后把部件加到显示区域
	public InstQueue instqueue = new InstQueue(7);
	public MemQueue loadqueue = new MemQueue("Load", 3);
	public MemQueue storequeue = new MemQueue("Store", 3);
	public RegQueue regqueue = new RegQueue(10);
	public CalcQueue addqueue = new CalcQueue("Add", 3);
	public CalcQueue mulqueue = new CalcQueue("Mul", 3);

    public static final int allheight = 700;
    public static final int allwidth = 1000;
        
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Tomasulo算法演示模拟平台");
		//primaryStage.setWidth(allwidth);
		//primaryStage.setHeight(allheight);

		//Init ListBox
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
                
        Label mullabel = new Label("乘除保留站");
		mullabel.setFont(new Font("Tahoma", 20));
		VBox MulBox = new VBox();
		MulBox.setSpacing(5);
		MulBox.getChildren().addAll(mullabel, mulqueue);
                
                
		Label reglabel = new Label("寄存器堆");
		storelabel.setFont(new Font("Tahoma", 20));
		VBox RegBox = new VBox();
		RegBox.setSpacing(5);
		RegBox.setPadding(new Insets(10, 10, 10, 10));                
		RegBox.getChildren().addAll(reglabel, regqueue);

		//Init InputArea 
		MoveLine inst2load = new MoveLine(new Double[] { 330.0, 240.0, 330.0, 270.0, 180.0, 270.0, 180.0, 300.0 });
		Text inputLabel = new Text();
		inputLabel.setText("输入指令:");
		inputLabel.setFill(Color.BLUE);
		inputLabel.setFont(Font.font(null, FontWeight.BOLD, 25));

		TextArea textArea = new TextArea();
		textArea.setMinHeight(300);
		textArea.setMaxWidth(250);

		textArea.setFont(Font.font(30));
		VBox inputVBox = new VBox();
		inputVBox.getChildren().addAll(inputLabel, textArea);
		inputVBox.setSpacing(10);
		inputVBox.setLayoutX(200);
		inputVBox.setLayoutY(200);
		inputVBox.setVisible(false);

		
		//Init Buttons
		GridPane ButtonList = new GridPane();
		String ButtonStyle = "-fx-text-fill: white; " + "-fx-font: 15 arial;"
				+ "-fx-font-weight: bold;-fx-background-color: linear-gradient(#61a2b1, #2A5058);"
				+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );";
		Button LoadButton = new Button("Load");
		Button InputButton = new Button("Input");
		Button StartButton = new Button("Start");
		Button NextButton = new Button("Next");
		
		LoadButton.setStyle(ButtonStyle);
		LoadButton.setOnAction((ActionEvent e) -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("打开指令文件");
			File file = fileChooser.showOpenDialog(primaryStage);
			// 解析并设置指令的代码
		});
		
		InputButton.setStyle(ButtonStyle);
		InputButton.setOnAction((ActionEvent e) -> {
			textArea.setText("");
			inputVBox.setVisible(true);
			InputButton.setDisable(true);
			LoadButton.setDisable(true);
		});

		StartButton.setStyle(ButtonStyle);
		StartButton.setOnAction((ActionEvent e) -> {
			// 解析并开始执行
			inputVBox.setVisible(false);
			InputButton.setDisable(false);
			StartButton.setDisable(false);
			NextButton.setDisable(false);
			instqueue.setEditable(false);

		});

		NextButton.setStyle(ButtonStyle);
		NextButton.setDisable(true);
		NextButton.setOnAction((ActionEvent e) -> {
			// 下一步操作调用的代码
			inst2load.play();
		});

		ButtonList.add(LoadButton,0,0);
		ButtonList.add(InputButton,1,0);
		ButtonList.add(StartButton,0,1);
		ButtonList.add(NextButton,1,1);
                
        // Insets top right bottom left
        HBox root = new HBox();
        
        VBox l1 = new VBox();
        ButtonList.setPadding(new Insets(0,0,30,0));
        l1.getChildren().addAll(ButtonList, LoadBox,StoreBox);
        l1.setPadding(new Insets(0,30,0,0));
        
        VBox l2 = new VBox();
        l2.getChildren().addAll(instBox);
        l2.setPadding(new Insets(0,30,0,0));
        HBox addmul = new HBox();
        AddBox.setPadding(new Insets(0,20,0,0));
        addmul.getChildren().addAll(AddBox,MulBox);
        addmul.setPadding(new Insets(0,30,0,0));
        l2.getChildren().addAll(addmul);

        VBox l3 = new VBox();
        //l3.getChildren().addAll(instBox);
        l3.setPadding(new Insets(0,30,0,0));

        root.getChildren().addAll(l1,l2,l3,RegBox);//instBox, LoadBox);
        //root.getChildren().addAll();//ButtonList);
		Scene scene = new Scene(root);
                
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
