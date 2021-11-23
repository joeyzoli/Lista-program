import Adatbeolvaso.*;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import javax.swing.DefaultListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ablak extends JFrame
{

	private JPanel contentPane;
	private JTextField keresomezo;
	private JTextField nevmezo;
	private JTextField emailmezo;
	private JTextField szulidomezo;
	private JTextField telefonmezo;
	private JTextField cimmezo;
	static private JList<String> lista;
	private static Long timer_start;
	private static Adat adatbeolvaso;
	
	private static Object zar_1 = new Object();
	
	private final int kernev = 2;
	private final int veznev = 4;
	private final int email = 6;
	private final int szuletesiido = 10;
	private final int telefonszam = 28;
	private final int megye = 30;
	private final int varos = 31;
	private final int iranyitoszam = 33;
	ArrayList<String[]> kereseseredmenytomb;
	Elemkivalaszto kivalasztva = new Elemkivalaszto();
	Elemkivalaszto2 kivalasztva2 = new Elemkivalaszto2();
	DefaultListModel<String> modell;
	DefaultListModel<String> keresesmodell;
	

	/**
	 * Create the frame.
	 */
	public ablak() 
	{
		setTitle("Adatkezel\u0151 ablak");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 735, 612);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel beolvaso = new JPanel();
		beolvaso.setBounds(5, 5, 709, 47);
		contentPane.add(beolvaso);
		beolvaso.setLayout(null);
		
		kereseseredmenytomb = new ArrayList<>();										//kereséseredménytömb létrehozása

		keresomezo = new JTextField();
		keresomezo.setBounds(10, 11, 316, 20);
		keresomezo.setHorizontalAlignment(SwingConstants.LEFT);
		
		keresomezo.addActionListener(new Kereses3());
		keresomezo.getDocument().addDocumentListener(new Mezofigyelo());
		keresomezo.setColumns(35);
		beolvaso.add(keresomezo);
		JButton keresgomb2 = new JButton("Hash");
		JButton keresgomb = new JButton("Csak n\u00E9v");
		
		keresgomb.addActionListener(new Kereses());										//gombhoz hozzáadjuk a Listenert
		keresgomb2.addActionListener(new Kereses2());									//gombhoz hozzáadjuk a Listenert
		
		keresgomb2.setBounds(450, 10, 85, 23);	
		keresgomb.setBounds(336, 10, 85, 23);
		beolvaso.add(keresgomb);
		beolvaso.add(keresgomb2);
		
		JButton keres3 = new JButton("Eg\u00E9sz ");
		keres3.setBounds(570, 10, 89, 23);
		keres3.addActionListener(new Kereses3());										//gombhoz hozzáadjuk a Listenert
		beolvaso.add(keres3);
		
		lista = new JList<String>();
		lista.setBounds(15, 68, 309, 494);
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);					//1x-es kijelõlés beállítása
		//lista.addListSelectionListener(kivalasztva);									//listakiválasztó metódus hozzáadása
		contentPane.add(lista);
		
		JScrollPane gorgetes = new JScrollPane(lista);
		gorgetes.setBounds(15, 68, 309, 494);
		contentPane.add(gorgetes);
		
		JPanel panel = new JPanel();
		panel.setBounds(334, 70, 380, 492);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel nev = new JLabel("N\u00E9v:");
		nev.setBounds(31, 28, 61, 28);
		panel.add(nev);
		
		JLabel email = new JLabel("E-mail:");
		email.setBounds(31, 92, 46, 14);
		panel.add(email);
		
		JLabel szulido = new JLabel("Sz\u00FClet\u00E9si id\u0151:");
		szulido.setBounds(31, 174, 88, 14);
		panel.add(szulido);
		
		JLabel telefon = new JLabel("Telefon:");
		telefon.setBounds(31, 318, 46, 14);
		panel.add(telefon);
		
		JLabel cim = new JLabel("C\u00EDm:");
		cim.setBounds(31, 416, 46, 14);
		panel.add(cim);
		
		nevmezo = new JTextField();
		nevmezo.setBounds(129, 32, 185, 20);
		panel.add(nevmezo);
		nevmezo.setColumns(10);
		
		emailmezo = new JTextField();
		emailmezo.setBounds(129, 89, 185, 20);
		panel.add(emailmezo);
		emailmezo.setColumns(10);
		
		szulidomezo = new JTextField();
		szulidomezo.setBounds(129, 171, 185, 20);
		panel.add(szulidomezo);
		szulidomezo.setColumns(10);
		
		telefonmezo = new JTextField();
		telefonmezo.setBounds(129, 315, 185, 20);
		panel.add(telefonmezo);
		telefonmezo.setColumns(10);
		
		cimmezo = new JTextField();
		cimmezo.setBounds(129, 413, 185, 20);
		panel.add(cimmezo);
		cimmezo.setColumns(10);
		/*
		adatbeolvaso = new Adat(".\\adat\\50000 Records.csv");						//új adat objektum létrehozása, megadva neki melyik fájlt kell beolvasni
		measureTime(true);															//idõmérõ indítása
		adatbeolvaso.beolvasas(",");												//adatbázis beolvasása és feldolgozása metódus meghívása az Adat osztályból
		
		System.out.println("Az adatbázis beolvasásának ideje: " + (measureTime(false) / 1000000) + "ms");		//idõmérõ lezárása és az eredmény kiiratása
		*/
	}
	
	void listakeszito()
	{
		 ListModel<String> sajatlistmodel = new ListModel<String>()		// saját ListModelt hozunk létre
				 {
			 		
				     public int getSize()							// Megvalósítjuk a getSize() abstrakt metódust
				     {
				    	 return adatbeolvaso.getdinamikustomb().size(); 					// Ami visszaadja a List, adatlista méretét, hogy abban hány elem van
				     }
				     public String getElementAt(int index) 			// Megvalósítjuk az elemlekérdezõ abstract metódust
				     { 
				    	 String tmp_str = "";
				    	 
				    	 tmp_str = adatbeolvaso.getdinamikustomb().get(index)[kernev] + " " + adatbeolvaso.getdinamikustomb().get(index)[veznev];  //csak a kereszt és vezetéknevet iratjuk ki
				    			 
				    	 return tmp_str;							// Majd visszaadja az összefûzött Stringet
			    	 }
		    	 
				 /* ListDataListenerek is kelleni fognak, de azokat most nem implementáljuk le*/
				 /* ListDataListenerek is kelleni fognak, de azokat most nem implementáljuk le*/
			     public void addListDataListener(ListDataListener l) {}
			     public void removeListDataListener(ListDataListener l) {}
				 };

				lista.setModel(sajatlistmodel);													//listamodel beállítása
				lista.addListSelectionListener(kivalasztva);									//listakiválasztó metódus hozzáadása
				 
	}
	
	static public float measureTime(boolean run)					//idõmérõ metódus
	{
		long current_time = System.nanoTime();						//a rendszeridõt nekiadjuk egy változónak
				
		if (run == true)											//ha igazra állítjuk elindul
		{
				timer_start = System.nanoTime();					//idõzítõ indulási értéke a rendszer aktuális ideje
				return (-1.0f);
		}
		else
		{
			long elapsed_time = current_time - timer_start;			//ha false lesz az érték
			return (elapsed_time);									//visszatér a különbséggel
		}
	}
	
	class Elemkivalaszto implements ListSelectionListener			//elem kiválasztásakor mi történjen osztály
	{
		public void valueChanged(ListSelectionEvent e)				//ezt a metódust kell leimplementálni
		{
			
			if(e.getValueIsAdjusting() == true)						//amint befejezték a kijelölést csak akkor indul, nem közben
			{
												
				int kivalasztva = lista.getSelectedIndex();			//a változó a kiválasztott elem indexét kapja meg.
				
				String[] szulidotomb = adatbeolvaso.getdinamikustomb().get(kivalasztva)[szuletesiido].split("/");		//születési idõt feldaraboljuk mert nem jó a formátuma
				
				
				nevmezo.setText(adatbeolvaso.getdinamikustomb().get(kivalasztva)[kernev] + " " + adatbeolvaso.getdinamikustomb().get(kivalasztva)[veznev]);
				emailmezo.setText(adatbeolvaso.getdinamikustomb().get(kivalasztva)[email]);
				szulidomezo.setText(szulidotomb[2] + " - " + szulidotomb[0] + " - " + szulidotomb[1]);
				telefonmezo.setText(adatbeolvaso.getdinamikustomb().get(kivalasztva)[telefonszam]);
				cimmezo.setText(adatbeolvaso.getdinamikustomb().get(kivalasztva)[iranyitoszam] + " " + adatbeolvaso.getdinamikustomb().get(kivalasztva)[varos] + " " + adatbeolvaso.getdinamikustomb().get(kivalasztva)[megye] );
				
			}
		}
	}
	
	class Elemkivalaszto2 implements ListSelectionListener				//elem kiválasztásakor mi történjen osztály
	{
		public void valueChanged(ListSelectionEvent e)					//ezt a metódust kell leimplementálni
		{
			if(e.getValueIsAdjusting() == true)							//amint befejezték a kijelölést csak akkor indul, nem közben
			{
											
				int kivalasztva = lista.getSelectedIndex();				//a változó a kiválasztott elem értékét kapja meg.
				
				String[] szulidotomb2 = kereseseredmenytomb.get(kivalasztva)[szuletesiido].split("/");			//születési idõ feldarabolása mert nem jó a formátuma
				
				
				nevmezo.setText(kereseseredmenytomb.get(kivalasztva)[kernev] + " " + kereseseredmenytomb.get(kivalasztva)[veznev]);				//mezõ kitoltése a megfelelõ adattal
				emailmezo.setText(kereseseredmenytomb.get(kivalasztva)[email]);																	//mezõ kitoltése a megfelelõ adattal
				szulidomezo.setText(szulidotomb2[2] + " - " + szulidotomb2[0] + " - " + szulidotomb2[1]);										//mezõ kitoltése a megfelelõ adattal
				telefonmezo.setText(kereseseredmenytomb.get(kivalasztva)[telefonszam]);															//mezõ kitoltése a megfelelõ adattal
				cimmezo.setText(kereseseredmenytomb.get(kivalasztva)[iranyitoszam] + " " + kereseseredmenytomb.get(kivalasztva)[varos] + " " + kereseseredmenytomb.get(kivalasztva)[megye] );			//mezõ kitoltése a megfelelõ adattal
				
			}
		}
	}
	
	
	
	class Kereses implements ActionListener				/*** Csak a vezetéknevek és keresztnevek között keres  ***/ 
	{
		 public void actionPerformed(ActionEvent e)
		 {
			 kereseseredmenytomb.clear();										//kereséseredménytömb ürítése
			 measureTime(true);													//idõmérõ indítása
			String keresettszo = keresomezo.getText().toLowerCase();			//keresõmezõbe írt szöveget kisbetûkre alakítja és eltárolja egy változóban
			String[] szulidotomb;
			Boolean eredmeny = false;											//keresés eredményére használt logikai változó
			String[] koztesszo = keresettszo.split(" ");						//a szóköznél kettébontja a keresendõ nevet		
			
			if(koztesszo.length > 1)											//ellenörzi, hogy valóban 2 név lett-e beírva ha nem le sem fut a keresés
			{
				for(int szamlalo = 0; szamlalo < adatbeolvaso.getdinamikustomb().size(); szamlalo++)		//sima for ciklus véggmegy az egész adatbázison
				{	
						
						if(adatbeolvaso.getdinamikustomb().get(szamlalo)[kernev].toLowerCase().equals(koztesszo[0]) && adatbeolvaso.getdinamikustomb().get(szamlalo)[veznev].toLowerCase().equals(koztesszo[1]))		//átalakítja kisbetûsre a neveket majd összehasonlítja a keresõmezõben megadott névvel van e egyezé
						{
							szulidotomb = adatbeolvaso.getdinamikustomb().get(szamlalo)[szuletesiido].split("/");
							
							nevmezo.setText(adatbeolvaso.getdinamikustomb().get(szamlalo)[kernev] + " " + adatbeolvaso.getdinamikustomb().get(szamlalo)[veznev]);
							emailmezo.setText(adatbeolvaso.getdinamikustomb().get(szamlalo)[email]);
							szulidomezo.setText(szulidotomb[2] + " - " + szulidotomb[0] + " - " + szulidotomb[1]);
							telefonmezo.setText(adatbeolvaso.getdinamikustomb().get(szamlalo)[telefonszam]);
							cimmezo.setText(adatbeolvaso.getdinamikustomb().get(szamlalo)[iranyitoszam] + " " + adatbeolvaso.getdinamikustomb().get(szamlalo)[varos] + " " + adatbeolvaso.getdinamikustomb().get(szamlalo)[megye] );
							
							eredmeny = true;									//keresés eredményét jelzõ logiaki változót igazra állítja
							
							kereseseredmenytomb.add(adatbeolvaso.getdinamikustomb().get(szamlalo));			//ahol egyezés volt hozzáadja a kereséseredmény tömbhöz
						}
						
				}
			}
			System.out.println("A nevek közti keresési idõ: " + (measureTime(false) / 1000000) + "ms");			//idõmérõ leállítása és az eredmény kiiratása a konzolra
			
			if(eredmeny == false)
			{
				JOptionPane.showMessageDialog(null, "Nincs ilyen név. Figyelj a kis és nagybetükre", "Tájékoztató Üzenet", 1);		//hibaüzenet ablak megnyitása ha nem volt találat
			}
			
			if(eredmeny == true)			//ha volt találat
			{
				Listacserelo();				//ez a metódus betölti au új listát és hozzá az új elemkiválasztót
			} 
		 } 
	}
	
	class Kereses2 implements ActionListener			/***  Hash keresés ***/
	{
		 public void actionPerformed(ActionEvent e)
		 {
			kereseseredmenytomb.clear();												//kereséseredménytömb ürítése
			measureTime(true);
			String keresettszo = keresomezo.getText();
			String[] szulidotomb;
				
			String[] talalat = adatbeolvaso.getHash().get(keresettszo.hashCode());		//hash kód ellenörzése, ha van az eredmény a tömbe kerül
				
					if(talalat != null)													//ha volt találat akkor lefut
					{
						szulidotomb = talalat[szuletesiido].split("/");
						
						nevmezo.setText(talalat[kernev] + " " + talalat[veznev]);
						emailmezo.setText(talalat[email]);
						szulidomezo.setText(szulidotomb[2] + " - " + szulidotomb[0] + " - " + szulidotomb[1]);
						telefonmezo.setText(talalat[telefonszam]);
						cimmezo.setText(talalat[iranyitoszam] + " " + talalat[varos] + " " + talalat[megye] );
						
						kereseseredmenytomb.add(talalat);								//kereséseredménytömbhöz hozzáadja a találatot
						Listacserelo();													//betölti az új listát a találattal

					}

					else																//ha nincsen ilyen hash akkor fut le
					{
						JOptionPane.showMessageDialog(null, "Nincs ilyen név az adatbázisban", "Tájékoztató Üzenet", 1);
					}
					
					System.out.println("A hash keresési idõ: " + (measureTime(false) / 1000000) + "ms");			//idõmérõ leállítása és eredmény kiiratása
		 }
	}
	
	class Kereses3 implements ActionListener				/*** Az egész adatbázisban keres, minden rekordot ellenõrizve ***/
	{
		 public void actionPerformed(ActionEvent e)
		 {
			kereseseredmenytomb.clear();														//kereséseredménytömb ürítése
			measureTime(true);																	//idõmérõ indítása
			String keresettszo = keresomezo.getText().toLowerCase();							//átalakítja a keresõmezõben levõ szüveget kisbetüsre és hozzáadja a változónak
			String[] szulidotomb;
			Boolean eredmeny = false;
			
			/*** egész adatbázisban keres  ***/
			
			for(int szamlalo = 0; szamlalo < adatbeolvaso.getdinamikustomb().size(); szamlalo++)								//for ciklus végigmegy az egész listán
			{
				for(int szamlalo2 = 0; szamlalo2 < adatbeolvaso.getdinamikustomb().get(szamlalo).length; szamlalo2++)			//for ciklus az egyes tömb elemein megy végig
				{
					
					if(adatbeolvaso.getdinamikustomb().get(szamlalo)[szamlalo2].toLowerCase().equals(keresettszo))				//ha talált egyezést valahol akkor fut le
					{
						if(kereseseredmenytomb.size() != 0)																			//leellenörzi, hogy üres-e a kereséseredménytömb
						{
							if(kereseseredmenytomb.get(kereseseredmenytomb.size()-1)[email].contains(adatbeolvaso.getdinamikustomb().get(szamlalo)[email]) == false) 		//lellenörzi, hogy a találat tömb email címe szerepel-e már a keresési eredmények között, és ha nem hozzáadja a találatot
							{
								kereseseredmenytomb.add(adatbeolvaso.getdinamikustomb().get(szamlalo));
							}
						}
						else																									//ha még üres a kereséseredmény akkor fut le, nehogy indexelési hiba legyen
						{
							kereseseredmenytomb.add(adatbeolvaso.getdinamikustomb().get(szamlalo));
						}
						
							szulidotomb = adatbeolvaso.getdinamikustomb().get(szamlalo)[szuletesiido].split("/");
							
							nevmezo.setText(adatbeolvaso.getdinamikustomb().get(szamlalo)[kernev] + " " + adatbeolvaso.getdinamikustomb().get(szamlalo)[veznev]);
							emailmezo.setText(adatbeolvaso.getdinamikustomb().get(szamlalo)[email]);
							szulidomezo.setText(szulidotomb[2] + " - " + szulidotomb[0] + " - " + szulidotomb[1]);
							telefonmezo.setText(adatbeolvaso.getdinamikustomb().get(szamlalo)[telefonszam]);
							cimmezo.setText(adatbeolvaso.getdinamikustomb().get(szamlalo)[iranyitoszam] + " " + adatbeolvaso.getdinamikustomb().get(szamlalo)[varos] + " " + adatbeolvaso.getdinamikustomb().get(szamlalo)[megye] );
							
							eredmeny = true;	
					}
				}	
			}
			
			System.out.println("A keresési idõ a teljes adatbáziosban:: " + (measureTime(false) / 1000000) + "ms");					//idõmérõ leállítása és eredmény kiiratása a konzolra
			
			if(eredmeny == false)
			{
				JOptionPane.showMessageDialog(null, "Nem volt találat az adatbázisban!", "Tájékoztató Üzenet", 1);		//ha nem volt találat felugrik a tájékoztató üzenet
			}
			
			if(eredmeny == true)					//ha volt találat
			{
				Listacserelo();						//betölti a metódus az új listát
			}
		 }
	}
	
	class Mezofigyelo implements DocumentListener														//keresõmezõ változásait figyelõ Listener
	{
			
			public void insertUpdate(DocumentEvent e) 													//beírt karakterek esetén fut le
			{
				String kereses = keresomezo.getText().toLowerCase();									//átalakítja kisbetûkre a beírt szüveget
				kereseseredmenytomb.clear();															//kereséseredménytömb ürítése
				int szamlalo2 = 0;																		//segédváltozó létrehozása
				
				
				for(int szamlalo = 0; szamlalo < adatbeolvaso.getdinamikustomb().size(); szamlalo++)		//for ciklus végigmegy az egészen
				{	
					if(adatbeolvaso.getdinamikustomb().get(szamlalo)[kernev].toLowerCase().indexOf(kereses) == szamlalo2)			//leellenörzi, hogy a beírt karakter indexe megegyezik e valamelyik név azanos indexével
					{
						kereseseredmenytomb.add(adatbeolvaso.getdinamikustomb().get(szamlalo));										//ahol egyezés van hozzáadja a kereséseredménytömbhöz
					}
				}
				szamlalo2 += 1;																			//segédváltozót növelem 1-el, hogy megnézzem a következõ karakteret
			
				Listacserelo();																		//majd kiiratom az aktuális eredményeket
			}

			public void removeUpdate(DocumentEvent e) 				//törlésnél fut le
			{
				String ures = keresomezo.getText();
				
				if(ures.equals(""))									//amennyiben üres a szövegmezõ 
				{
					Listavisszacserelo();							//visszaállítja az eredeti listát
				}
				
			}
			
			public void changedUpdate(DocumentEvent e) 
			{
																		
			}	
		}
	
	public void Listacserelo()																//listacserélõ metódus. a keresési eredményeket tölti be a Jlistbe
	{
		ListModel<String> keresesilistmodel = new ListModel<String>()						// saját ListModelt hozunk létre
				 {
			 		
				     public int getSize()													// Megvalósítjuk a getSize() abstrakt metódust
				     {
				    
				    	 return kereseseredmenytomb.size(); 								// Ami visszaadja a List, adatlista méretét, hogy abban hány elem van
				    	 
				     }
				     public String getElementAt(int index) 									// Megvalósítjuk az elemlekérdezõ abstract metódust
				     { 
				    	 String tmp_str = "";
				 
				    	tmp_str = kereseseredmenytomb.get(index)[kernev] + " " + kereseseredmenytomb.get(index)[veznev];		//csak a kereszt és vezetéknevek tölti be
				    			 
				    	return tmp_str;														// Majd visszaadja az összefûzött Stringet
			    	 }
		    	 
				 /* ListDataListenerek is kelleni fognak, de azokat most nem implementáljuk le*/
				 /* ListDataListenerek is kelleni fognak, de azokat most nem implementáljuk le*/
				 public void addListDataListener(ListDataListener l) {}
				 public void removeListDataListener(ListDataListener l) {}
				 }; 
			 
		lista.setModel(keresesilistmodel);
		lista.removeListSelectionListener(kivalasztva);
		lista.addListSelectionListener(kivalasztva2);
	}
	
	public void Listavisszacserelo()
	{
		ListModel<String> sajatlistmodel = new ListModel<String>()								// saját ListModelt hozunk létre
				 {
			 		
				     public int getSize()														// Megvalósítjuk a getSize() abstrakt metódust
				     {
				    	 return adatbeolvaso.getdinamikustomb().size(); 						// Ami visszaadja a List, adatlista méretét, hogy abban hány elem van
				     }
				     public String getElementAt(int index) 										// Megvalósítjuk az elemlekérdezõ abstract metódust
				     { 
				    	 String tmp_str = "";
				    	 
				    	 tmp_str = adatbeolvaso.getdinamikustomb().get(index)[kernev] + " " + adatbeolvaso.getdinamikustomb().get(index)[veznev];
				    			 
				    	 return tmp_str;														// Majd visszaadja az összefûzött Stringet
			    	 }
		    	 
				 /* ListDataListenerek is kelleni fognak, de azokat most nem implementáljuk le*/
				 /* ListDataListenerek is kelleni fognak, de azokat most nem implementáljuk le*/
			     public void addListDataListener(ListDataListener l) {}
			     public void removeListDataListener(ListDataListener l) {}
				 };
				 
				 lista.setModel(sajatlistmodel);
				 lista.removeListSelectionListener(kivalasztva2);
				 lista.addListSelectionListener(kivalasztva);
	}
	
	static class Beolvasoszal implements Runnable
	{
		public void run()
		{
			adatbeolvaso = new Adat(".\\adat\\50000 Records.csv");						//új adat objektum létrehozása, megadva neki melyik fájlt kell beolvasni
			measureTime(true);															//idõmérõ indítása
			adatbeolvaso.beolvasas(",");												//adatbázis beolvasása és feldolgozása metódus meghívása az Adat osztályból
			
			System.out.println("Az adatbázis beolvasásának ideje: " + (measureTime(false) / 1000000) + "ms");		//idõmérõ lezárása és az eredmény kiiratása
			
			synchronized(zar_1)
			{
				zar_1.notify();		// Értesítjük a zar_1-t, hogy mehet	
			}
		}
	}
	
	public static void main(String[] args) 
	{
		Beolvasoszal beolvasas = new Beolvasoszal();
		Thread szal1 = new Thread(beolvasas);
		szal1.start();
		
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					
					measureTime(true);	
					ablak frame = new ablak();							//ablak létrehozása
					frame.setSize(800, 600);							//ablak méretének megadása
					synchronized(zar_1)
					{
						try
						{
							zar_1.wait();			// Várakozunk a jelzésre
						}
						catch (InterruptedException e)
						{
							System.out.print(e);
						}
						frame.listakeszito();								//listakészító metódus meghívása.
					}
					
					
					frame.setVisible(true);								//láthatóvá tesszük az ablakot
					System.out.println("Ablak létrehozásának ideje: " + (measureTime(false) / 1000000) + "ms");			//kiiratom a mért eredményt
					
					
				} 
				catch (Exception e) 									//kivétel elkapása
				{
					e.printStackTrace();								//elkapás esetén történik - kiírja a konzolra 
				}
			}
		});

	}
}
