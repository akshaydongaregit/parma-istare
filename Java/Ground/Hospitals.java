import java.security.AllPermission;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Hospitals {

	
	public static void main(String ... args) {
	
		Scanner in = new Scanner(System.in);
		
		int citiesCount = in.nextInt();
		int roadCount = in.nextInt();
		Integer[] roadsFrom = new Integer[roadCount];
		Integer[] roadsTo = new Integer[roadCount];
		
		for(int i=0;i<roadCount;i++) {
			roadsFrom[i] = in.nextInt();
			roadsTo[i] = in.nextInt();
		}

		System.out.println("Minimum hospitals count : " + hospitals(citiesCount, Arrays.asList(roadsFrom), Arrays.asList(roadsTo)));
		
	}
	
	public static int hospitals(int cityNodes , List<Integer> cityFrom , List<Integer> cityTo) {

		int[][] roads = new int[cityFrom.size()][2];
		
		for(int i=0;i<cityFrom.size();i++) {
			roads[i][0] = cityFrom.get(i).intValue();
			roads[i][1] = cityTo.get(i).intValue();
		}

		// Connectivity count , isCovered , hasHospital
		int[][] cities = new int[cityNodes+1][3];
		
		//build new hospital until all cities are covered
		int hospCount = 0;
		while( !allCovered(cities)) {
			cities = calculateUncoveredCitiesConnectivityCount(cities, roads);
			cities = buildHospital(cities, roads);
			//printCities(cities);
			hospCount++;
		}

		return hospCount;
	}
	
	public static boolean allCovered(int[][] cities) {
		for(int i = 1 ; i<cities.length; i++)
			if( cities[i][1] == 0 )  return false ;
		return true;
	}
	
	public static int[][] buildHospital(int[][] cities , int[][] roads) {
		
		//find city to build hospital
		int city = 0;
		for(int i=1;i<cities.length;i++)
			if( cities[i][2] == 0 && cities[i][0] >= cities[city][0] )
				city = i;
		
		//build hospital there .
		for(int i=0;i<roads.length;i++) {
			if(roads[i][0] == city )
				cities[ roads[i][1] ][ 1 ] = 1;
			if(roads[i][1] == city )
				cities[ roads[i][0] ][ 1 ] = 1;
		}
		cities[city][2] = 1;
		
		return cities;
	}
	
	public static void printCities(int[][] cities) {
		for(int i=0;i<cities.length;i++) {
			System.out.println("city : "+i+" [ "+ cities[i][0] +" , "+ cities[i][1] + " , " + cities[i][2] +" ]");
		}
	}
	public static int[][] calculateUncoveredCitiesConnectivityCount(int[][] cities , int[][] roads) {
		
		//first reset count of cities
		for(int i=0;i<cities.length;i++) 
			cities[i][0] = 0;
					
		for(int i=0;i<roads.length;i++) {
			if( cities[roads[i][1]][1] == 0) cities[roads[i][0]][0]++;
			if( cities[roads[i][0]][1] == 0) cities[roads[i][1]][0]++;
		}
		
		return cities;
	}
	
}
