package Adatbeolvaso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Adat 
{
	private File adatok;
	private final int kernev = 2;
	private final int veznev = 4;
	private ArrayList<String[]> dinamikustomb;
	private HashMap<Integer, String[]> elsohash;
	
	public Adat(String adatokeleres)
	{
		adatok = new File(adatokeleres);
		dinamikustomb = new ArrayList<>();
		elsohash = new HashMap<Integer, String[]>();
	}
	
	public ArrayList<String[]> getdinamikustomb()
	{
		return dinamikustomb;
	}
	
	public HashMap<Integer, String[]> getHash()
	{
		return elsohash;
	}
	
	public void beolvasas(String elvalaszto)
	{
		try
		{
			BufferedReader beolvaso = new BufferedReader(new FileReader(adatok));
			
			
			String buffer = beolvaso.readLine();
			String[] adathalmaz;
			Integer hashcode;
			
			while((buffer = beolvaso.readLine()) != null)
			{
				
				adathalmaz = buffer.split(elvalaszto);
				dinamikustomb.add(adathalmaz);
				
				hashcode = (adathalmaz[kernev] + " " + adathalmaz[veznev]).hashCode();
				elsohash.put(hashcode, adathalmaz);
				
			}
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
	
	
	
	

}
