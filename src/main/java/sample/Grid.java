package sample;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.shape.Shape;
import tower.Tower;

public class Grid extends Label {
	
			public Grid() {
				super();
			}

			
			private double x;
			private double y;
			public Tooltip infoToolTip;
			public Shape radius;
			private String name;
			public double HP;
			public boolean isSlowed = false;
			public Tower tower;
		

			public double getX() {
				return x;
			}

			public double getY() {
				return y;
			}

			public void setXY(double _x, double _y) {
				this.x = _x;
				this.y = _y;
			}

			public String getName() {
				return this.name;
			}

			public void setName(String name) {
				this.name = name;
			}

			

		}



