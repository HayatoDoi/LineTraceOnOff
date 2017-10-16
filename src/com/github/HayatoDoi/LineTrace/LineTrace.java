package com.github.HayatoDoi.LineTrace;

import lejos.nxt.*;
import lejos.nxt.addon.ColorHTSensor;
import lejos.robotics.Color;
import lejos.util.Delay;

public class LineTrace {
	static String[] colorNames = { "Red", "Green", "Blue", "Yellow", "Magenta", "Orange",
		"White", "Black", "Pink", "Gray", "Light gray", "Dark gray", "Cyan" };
	static ColorHTSensor sensor = new ColorHTSensor(SensorPort.S3);

	public static void main(String[] args) {

		NXTRegulatedMotor rightWheel = Motor.A;
		NXTRegulatedMotor leftWheel = Motor.C;
		// スピードの設定
		int speed1 = 100;
		int speed2 = 400;
		int speed3 = 200;
		int speed4 = 300;
		Color color;
		int id;
		int swap;
		rightWheel.setSpeed(speed1);
		leftWheel.setSpeed(speed2);
		// 前進
		rightWheel.forward();
		leftWheel.forward();

		while(true){
			color = sensor.getColor();
			id = sensor.getColorID();
			
			LCD.clear();
			LCD.drawString(colorNames[id], 0, 0);
			// 赤成分を表示
			LCD.drawString("R", 0, 4);
			LCD.drawInt(color.getRed(), 1, 5);
			// 緑成分を表示
			LCD.drawString("G", 4, 4);
			LCD.drawInt(color.getGreen(), 5, 5);
			// 青成分を表示
			LCD.drawString("B", 8, 4);
			LCD.drawInt(color.getBlue(), 9, 5);
			LCD.refresh();
			
			//red
			if( colorNames[id].equals("Red") ) {
				break;
			}
			//green
			if( colorNames[id].equals("Blue") ) {
				while(true) {
					color = sensor.getColor();
					id = sensor.getColorID();
					if(!colorNames[id].equals("Black") && !colorNames[id].equals("Blue") && !colorNames[id].equals("Red")) {
						break;
					}
					rightWheel.setSpeed(speed3);
					leftWheel.setSpeed(speed4);
					Delay.msDelay(5);
				}
				swap = speed1;
				speed1 = speed2;
				speed2 = swap;
				
				swap = speed3;
				speed3 = speed4;
				speed4 = swap;
				
				continue;
				
			}
			//black
			if( colorNames[id].equals("Black") ) {
				rightWheel.setSpeed(speed2);
				leftWheel.setSpeed(speed1);
				Delay.msDelay(5);
			}
			//white
			else {
				rightWheel.setSpeed(speed1);
				leftWheel.setSpeed(speed2);
				Delay.msDelay(2);
			}
//			Delay.msDelay(3);
		}
		// 停止
		rightWheel.stop();
		leftWheel.stop();
	}
}
