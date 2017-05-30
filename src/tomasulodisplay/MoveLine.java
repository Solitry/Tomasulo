/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tomasulodisplay;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

/**
 *
 * @author meepo
 */
public class MoveLine extends Group{
    public final int CircleRadius = 5;
    public final int CircleSpeed = 5;
    private Timeline timeline = new Timeline();
    public Circle circle;
    public Polyline line;
    public MoveLine(Double[] pointDoubles) {
        super();
        line = new Polyline();
        line.strokeProperty().setValue(Color.YELLOW);
        line.strokeWidthProperty().set(4);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.getPoints().addAll(pointDoubles);
        
        circle = new Circle(CircleRadius, Color.BLUE);
        circle.setStrokeType(StrokeType.CENTERED);
        circle.setStrokeWidth(3);
        circle.setEffect(new BoxBlur(5, 5, 1));

        int size = pointDoubles.length;
        double millis = 0;
        for (int i = 0; i < size-2; i+=2) {
            double x = pointDoubles[i]-pointDoubles[i+2];
            double y = pointDoubles[i+1]-pointDoubles[i+3];
            double needtime = Math.sqrt(x*x+y*y)*CircleSpeed;
            timeline.getKeyFrames().addAll(
                    new KeyFrame(new Duration(millis), // set start position
                    new KeyValue(circle.translateXProperty(), pointDoubles[i]),
                    new KeyValue(circle.translateYProperty(), pointDoubles[i+1])),
                    new KeyFrame(new Duration(millis+needtime), // set end position 
                    new KeyValue(circle.translateXProperty(), pointDoubles[i+2]),
                    new KeyValue(circle.translateYProperty(), pointDoubles[i+3])));
            millis += needtime;
        }     
        
        timeline.getKeyFrames().add(
                    new KeyFrame(new Duration(millis+1), 
                    new KeyValue(circle.translateXProperty(), -100),
                    new KeyValue(circle.translateYProperty(), -100)));
        getChildren().addAll(line,circle);
    }
    
    public void play() {
        timeline.play();
    }
}