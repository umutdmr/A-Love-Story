import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;


public class project3main 
{
	public static class City
	{
		int name;
		int distance;
		public City(int name, int distance)
		{
			this.distance = distance;
			this.name = name;
		}
		public String toString()
		{
			Integer tmp = name;
			return "c".concat(tmp.toString());
		}
		
	}
	public static ArrayList<String> minPathToLeyla(int noOfCitites, ArrayList<ArrayList<City>> city, int mecnun, int leyla, int timeLimit)
	{
		ArrayList<String> res = new ArrayList<String>();
		try
		{
			
			int[] distancesArray = new int[noOfCitites];
			
			for(int i = 0; i < noOfCitites; i++)
			{
				distancesArray[i] = Integer.MAX_VALUE;
			}
			
			int[] adjCities = new int[noOfCitites];
			distancesArray[mecnun] = 0;
			adjCities[mecnun] = -1;
			
			PriorityQueue<City> ct = new PriorityQueue<City>((c1, c2) -> c1.distance - c2.distance);
			ct.add(new City(mecnun, 0));
			
			while(ct.size() > 0)
			{
				City currentCity = ct.poll();
				
				for(City c: city.get(currentCity.name))
				{
					if(distancesArray[currentCity.name] + c.distance < distancesArray[c.name])
					{
						distancesArray[c.name] = c.distance + distancesArray[currentCity.name];
						adjCities[c.name] = currentCity.name;
						ct.add(new City(c.name, distancesArray[c.name]));
					}
				}
				
			}
			
			ArrayList<Integer> tmp = new ArrayList<>();
			
			
			int shortestDis = distancesArray[leyla];
			
			tmp.add(leyla);
			
			while(adjCities[leyla] != -1)
			{
				tmp.add(adjCities[leyla]);
				leyla = adjCities[leyla];
			}
			if(timeLimit < shortestDis)
			{
				res.add("false");
				
			}
			else
			{
				res.add("true");
			}
			for(int i = tmp.size() - 1; i >= 0; i--)
			{
				Integer num = tmp.get(i);
				
				num++;
				String str = "c".concat(num.toString());
				res.add(str);
			}
			
			
			res.add("false");
			return res;
			
			
		}
		catch (Exception e) {
			
			return null;
		}
	}
	public static int convert(String city)
	{
		city = city.split("c")[1];
		int res = Integer.parseInt(city);
		return res;
	}
	
	public static class Road
	{
		int from;
		int to;
		int length;
		public Road(int from, int to, int length)
		{
			this.from = from;
			this.to = to;
			this.length = length;
		}
	}
	
	
	public static class HoneymoonCities
	{
		int cities;
		ArrayList<Road> roads = new ArrayList<Road>();
		 public HoneymoonCities(int cities)
		 {
			 this.cities = cities;
		 }
		 
		 public void addRoad(int from, int to, int length)
		 {
			 Road road = new Road(from, to, length);
			 Road reverseRoad = new Road(to, from, length);
			 roads.add(road);
			 roads.add(reverseRoad);
		 }
		 
		 public void makeArray(int[] adjCity)
		 {
			 for(int i = 0; i < adjCity.length; i++)
			 {
				 adjCity[i] = i;
			 }
		 }
		 public int findCity(int[] adjCity, int city)
		 {
			 if(adjCity[city] != city)
			 {
				 return findCity(adjCity, adjCity[city]);
			 }
			 return city;
		 }
		 
		 public void addTogether(int[] adjCity, int path1, int path2)
		 {
			 
			 int xForSet = findCity(adjCity, path1);
			 int yForSet = findCity(adjCity, path2);
			 
			 adjCity[yForSet] = xForSet;
		 }
		 
		 public int findHoneymoonPath()
		 {
			 try
			 {
				 PriorityQueue<Road> rd = new PriorityQueue<Road>(roads.size(), Comparator.comparingInt(r -> r.length));
				 for(int i = 0; i < roads.size(); i++)
				 {
					 rd.add(roads.get(i));
				 }
				 
				 int[] adjCity = new int[cities];
				 makeArray(adjCity);
				 
				 ArrayList<Road> paths = new ArrayList<Road>();
				 
				 int pointer = 0;
				 
				 while(pointer < cities - 1)
				 {
					Road r = rd.remove(); 
					
					int path1 = findCity(adjCity, r.from);
					int path2 = findCity(adjCity, r.to);
					if(path1 == path2)
					{
						
					}
					else
					{
						paths.add(r);
						pointer++;
						addTogether(adjCity, path1, path2);
					}
				 }
					
				 int res = 0;
				 for(Road r: paths)
		         {
		                res += r.length;
		         }
		         return res * 2;
			 }
			 catch (Exception e) 
			 {
				return -2;
			}
		 }
	}
	public static int convertD(String city)
	{
		city = city.split("d")[1];
		int res = Integer.parseInt(city);
		return res;
	}
	public static void main(String[] args) throws FileNotFoundException
    {
		
        Scanner in = new Scanner(new File(args[0]));
        PrintStream out = new PrintStream(new File(args[1]));
        int timeLimit = in.nextInt();
        int noOfCities = in.nextInt();
        int mecnun = convert(in.next());
        String leylaCity = in.next();
        int leyla = convert(leylaCity);
        in.nextLine();
        //part1
        ArrayList<ArrayList<City>> cityMap = new ArrayList<ArrayList<City>>();
        for(int i = 0; i < leyla; i++)
        {
        	cityMap.add(new ArrayList<City>());
        	
        }
        for(int i = 1; i < leyla; i++)
        {
        	
        	String s = in.nextLine();
        	String[] pathArray = s.split(" ");
        	
        	int base = convert(pathArray[0])-1;
        	
        	if(pathArray.length > 1)
        	{
        		
        		for(int j = 1; j <  pathArray.length; j+=2)
        		{
        		
        			int destination = convert(pathArray[j]);
        			
        			int length = Integer.parseInt(pathArray[j+1]);
        			cityMap.get(base).add(new City(destination-1, length));
        		}
        		
        	}
        	
        }
        
        ArrayList<String> path = minPathToLeyla(noOfCities, cityMap, mecnun-1, leyla-1, timeLimit);
        String val = path.get(0);
        if(val == "false")
        {
        	out.print("-1");
        }
        else
        {
        	out.print(path.get(1));
        	for(int i = 2; i < path.size(); i++)
        	{
        		out.print(" " + path.get(i));
        	}
        	
        }
        out.println();
        
        //part2
        
        int noOfCitiesForHoneyMoon = noOfCities - leyla + 1;
        HoneymoonCities honeymoonCities = new HoneymoonCities(noOfCitiesForHoneyMoon);
        
        leyla = 0;
        while(in.hasNext())
        {
        	String s = in.nextLine();
        	String[] pathArray = s.split(" ");
        	int from;
        	if(pathArray[0].startsWith("c"))
        	{
        		from = leyla;
        	}
        	else
        	{
        		from = convertD(pathArray[0]);
        	}
        	
        	if(pathArray.length > 1)
        	{
        		for(int j = 1; j <  pathArray.length; j+=2)
        		{
        			int to = convertD(pathArray[j]);
        			int length = Integer.parseInt(pathArray[j+1]);
        			honeymoonCities.addRoad(from, to, length);
        		}
        	}
        }
        
        int taxToPaid = honeymoonCities.findHoneymoonPath();
        if(val == "false")
        {
        	out.println("-1");
        }
        else
        {
        	out.println(taxToPaid);
        }
        
        
        in.close();
        out.close();
	}

}
