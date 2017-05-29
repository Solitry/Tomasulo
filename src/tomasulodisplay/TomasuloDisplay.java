/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tomasulodisplay;

import java.io.File;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeLineCap;
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
    public LoadQueue loadqueue = new LoadQueue(3);

    @Override
    public void start(Stage primaryStage) {    
        
        Scene scene = new Scene(new Group());  
        primaryStage.setTitle("Tomasulo算法演示模拟平台");  
        primaryStage.setWidth(900);  
        primaryStage.setHeight(700);  
   
        
        Label instlabel = new Label("指令队列");  
        instlabel.setFont(new Font("Tahoma", 20));
        instqueue.setMaxHeight(200);
        instqueue.setMaxWidth(300);
        
        final VBox instBox = new VBox();  
        instBox.setSpacing(5);  
        instBox.setPadding(new Insets(10, 0, 0, 10));  
        instBox.getChildren().addAll(instlabel, instqueue);  
        instBox.setLayoutX(220);
        instBox.setLayoutY(0);
        
        Label loadlabel = new Label("Load 队列");  
        loadlabel.setFont(new Font("Tahoma", 20));
        loadqueue.setMaxHeight(200);
        loadqueue.setMaxWidth(240);
        
        final VBox LoadBox = new VBox();  
        LoadBox.setSpacing(5);  
        LoadBox.setPadding(new Insets(10, 0, 0, 10));  
        LoadBox.getChildren().addAll(loadlabel, loadqueue);  
        LoadBox.setLayoutX(600);
        LoadBox.setLayoutY(50);
        
        
        
        
        MoveLine inst2load = new MoveLine(new Double[]{
        330.0, 240.0,
        330.0, 270.0,
        180.0, 270.0,
        180.0, 300.0});
        
        Text inputLabel = new Text();
        inputLabel.setText("输入指令:");
        inputLabel.setFill(Color.BLUE);
        inputLabel.setFont(Font.font(null, FontWeight.BOLD, 25));
      
        TextArea textArea = new TextArea ();
        textArea.setMinHeight(300);
        textArea.setMaxWidth(250);

        textArea.setFont(Font.font(30));
        VBox inputVBox = new VBox();
        inputVBox.getChildren().addAll(inputLabel, textArea);
        inputVBox.setSpacing(10);
        inputVBox.setLayoutX(200);
        inputVBox.setLayoutY(200);
        inputVBox.setVisible(false);
        
        HBox ButtonList = new HBox();
        Button LoadButton = new Button("Load"); 
        String ButtonStyle = "-fx-text-fill: white; "
                + "-fx-font: 15 arial;"
                + "-fx-font-weight: bold;-fx-background-color: linear-gradient(#61a2b1, #2A5058);"
                + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );";
        LoadButton.setStyle(ButtonStyle);
        LoadButton.setOnAction((ActionEvent e) -> {  
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("打开指令文件");
            File file = fileChooser.showOpenDialog(primaryStage);
            //　解析并设置指令的代码
        });  
        Button InputButton = new Button("Input");         
        
       
        InputButton.setStyle(ButtonStyle);
        InputButton.setOnAction((ActionEvent e) -> {  
            textArea.setText("");
            inputVBox.setVisible(true);
            InputButton.setDisable(true);
            LoadButton.setDisable(true);
        });  
        
        Button StartButton = new Button("Start");  
        StartButton.setStyle(ButtonStyle);
       
        Button NextButton = new Button("Next");  
        NextButton.setDisable(true);
        NextButton.setOnAction((ActionEvent e) -> {  
            // 下一步操作调用的代码
            inst2load.play();
        });  
        NextButton.setStyle(ButtonStyle);
        
        
        StartButton.setOnAction((ActionEvent e) -> {  
            // 解析并开始执行
            inputVBox.setVisible(false);
            InputButton.setDisable(false);
            StartButton.setDisable(false);
            NextButton.setDisable(false);
            instqueue.setEditable(false);
            
        });  
        ButtonList.getChildren().addAll(LoadButton, InputButton, StartButton, NextButton);
   
        
        ((Group) scene.getRoot()).getChildren().addAll(instBox,inst2load,ButtonList);  
        ((Group) scene.getRoot()).getChildren().addAll(inputVBox,LoadBox);  

        primaryStage.setScene(scene);  
        primaryStage.show();  
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
