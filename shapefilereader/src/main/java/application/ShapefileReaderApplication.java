package application;

import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

import application.shapefileReader.ShapefileReader;
import util.Util;

public class ShapefileReaderApplication {

	public static void main(String[] args) {
		ArrayList<Geometry> rtmList = ShapefileReader.readShapefile();
		ArrayList<Coordinate> rtmAsCoordsList = Util.convertLineStringIntoCoordinate(rtmList);
		System.out.println(rtmAsCoordsList.size());
	}

}
