package application.shapefileReader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.feature.Feature;
import org.opengis.feature.GeometryAttribute;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Geometry;

public class ShapefileReader {

	private static File getFile(String path) {
		File file = new File(path);

		return file;
	}

	public static ArrayList<Geometry> readShapefile() {
		File shapefile = getFile("C:/Users/msteidel/Desktop/RTM_MWotS_jun14/RTM_MWotS_jun14_clean.shp");

		HashMap<String, URL> connect = new HashMap<String, URL>();
		ArrayList<Geometry> result = new ArrayList<Geometry>();
		try {
			connect.put("url", shapefile.toURI().toURL());
			DataStore dataStore = DataStoreFinder.getDataStore(connect);
			String[] typeNames = dataStore.getTypeNames();
			String typeName = typeNames[0];

			System.out.println("Reading content " + typeName);

			FeatureSource<?, ?> featureSource = dataStore.getFeatureSource(typeName);
			FeatureCollection<?, ?> collection = featureSource.getFeatures();
			SimpleFeatureType schema = (SimpleFeatureType) featureSource.getSchema();
			CoordinateReferenceSystem dataCRS = schema.getCoordinateReferenceSystem();
			CoordinateReferenceSystem worldCRS = CRS.decode("EPSG:4326");
			MathTransform transform = CRS.findMathTransform(dataCRS, worldCRS);
			FeatureIterator<?> iterator = collection.features();

			while (iterator.hasNext()) {
				Feature feature = iterator.next();
				System.out.println(feature.getName().toString());
				GeometryAttribute geometryAttr = feature.getDefaultGeometryProperty();
				Geometry geometry = (Geometry) geometryAttr.getValue();
				if (geometry != null) {
					Geometry resultGeometry = JTS.transform(geometry, transform);
					result.add(resultGeometry);
				}
			}
			iterator.close();
			return result;

		} catch (IOException | FactoryException | MismatchedDimensionException | TransformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
