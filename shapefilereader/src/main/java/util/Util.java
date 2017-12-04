package util;

import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

public class Util {

	public static ArrayList<Coordinate> convertLineStringIntoCoordinate(ArrayList<Geometry> lineStrings) {

		ArrayList<Coordinate> result = new ArrayList<Coordinate>();

		for (Geometry geo : lineStrings) {
			for (int i = 0; i < geo.getCoordinates().length; i++) {
				Coordinate coord = geo.getCoordinates()[i];
				result.add(coord);
			}
		}

		return result;
	}

}
