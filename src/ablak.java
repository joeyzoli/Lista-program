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
		
		kereseseredmenytomb = new ArrayList<>();										//keres�seredm�nyt�mb l�trehoz�sa

		keresomezo = new JTextField();
		keresomezo.setBounds(10, 11, 316, 20);
		keresomezo.setHorizontalAlignment(SwingConstants.LEFT);
		
		keresomezo.addActionListener(new Kereses3());
		keresomezo.getDocument().addDocumentListener(new Mezofigyelo());
		keresomezo.setColumns(35);
		beolvaso.add(keresomezo);
		JButton keresgomb2 = new JButton("Hash");
		JButton keresgomb = new JButton("Csak n\u00E9v");
		
		keresgomb.addActionListener(new Kereses());										//gombhoz hozz�adjuk a Listenert
		keresgomb2.addActionListener(new Kereses2());									//gombhoz hozz�adjuk a Listenert
		
		keresgomb2.setBounds(450, 10, 85, 23);	
		keresgomb.setBounds(336, 10, 85, 23);
		beolvaso.add(keresgomb);
		beolvaso.add(keresgomb2);
		
		JButton keres3 = new JButton("Eg\u00E9sz ");
		keres3.setBounds(570, 10, 89, 23);
		keres3.addActionListener(new Kereses3());										//gombhoz hozz�adjuk a Listenert
		beolvaso.add(keres3);
		
		lista = new JList<String>();
		lista.setBounds(15, 68, 309, 494);
		lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);					//1x-es kijel�l�s be�ll�t�sa
		//lista.addListSelectionListener(kivalasztva);									//listakiv�laszt� met�dus hozz�ad�sa
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
		adatbeolvaso = new Adat(".\\adat\\50000 Records.csv");						//�j adat objektum l�trehoz�sa, megadva neki melyik f�jlt kell beolvasni
		measureTime(true);															//id�m�r� ind�t�sa
		adatbeolvaso.beolvasas(",");												//adatb�zis beolvas�sa �s feldolgoz�sa met�dus megh�v�sa az Adat oszt�lyb�l
		
		System.out.println("Az adatb�zis beolvas�s�nak ideje: " + (measureTime(false) / 1000000) + "ms");		//id�m�r� lez�r�sa �s az eredm�ny kiirat�sa
		*/
	}
	
	void listakeszito()
	{
		 ListModel<String> sajatlistmodel = new ListModel<String>()		// saj�t ListModelt hozunk l�tre
				 {
			 		
				     public int getSize()							// Megval�s�tjuk a getSize() abstrakt met�dust
				     {
				    	 return adatbeolvaso.getdinamikustomb().size(); 					// Ami visszaadja a List, adatlista m�ret�t, hogy abban h�ny elem van
				     }
				     public String getElementAt(int index) 			// Megval�s�tjuk az elemlek�rdez� abstract met�dust
				     { 
				    	 String tmp_str = "";
				    	 
				    	 tmp_str = adatbeolvaso.getdinamikustomb().get(index)[kernev] + " " + adatbeolvaso.getdinamikustomb().get(index)[veznev];  //csak a kereszt �s vezet�knevet iratjuk ki
				    			 
				    	 return tmp_str;							// Majd visszaadja az �sszef�z�tt Stringet
			    	 }
		    	 
				 /* ListDataListenerek is kelleni fognak, de azokat most nem implement�ljuk le*/
				 /* ListDataListenerek is kelleni fognak, de azokat most nem implement�ljuk le*/
			     public void addListDataListener(ListDataListener l) {}
			     public void removeListDataListener(ListDataListener l) {}
				 };

				lista.setModel(sajatlistmodel);													//listamodel be�ll�t�sa
				lista.addListSelectionListener(kivalasztva);									//listakiv�laszt� met�dus hozz�ad�sa
				 
	}
	
	static public float measureTime(boolean run)					//id�m�r� met�dus
	{
		long current_time = System.nanoTime();						//a rendszerid�t nekiadjuk egy v�ltoz�nak
				
		if (run == true)											//ha igazra �ll�tjuk elindul
		{
				timer_start = System.nanoTime();					//id�z�t� indul�si �rt�ke a rendszer aktu�lis ideje
				return (-1.0f);
		}
		else
		{
			long elapsed_time = current_time - timer_start;			//ha false lesz az �rt�k
			return (elapsed_time);									//visszat�r a k�l�nbs�ggel
		}
	}
	
	class Elemkivalaszto implements ListSelectionListener			//elem kiv�laszt�sakor mi t�rt�njen oszt�ly
	{
		public void valueChanged(ListSelectionEvent e)				//ezt a met�dust kell leimplement�lni
		{
			
			if(e.getValueIsAdjusting() == true)						//amint befejezt�k a kijel�l�st csak akkor indul, nem k�zben
			{
												
				int kivalasztva = lista.getSelectedIndex();			//a v�ltoz� a kiv�lasztott elem index�t kapja meg.
				
				String[] szulidotomb = adatbeolvaso.getdinamikustomb().get(kivalasztva)[szuletesiido].split("/");		//sz�let�si id�t feldaraboljuk mert nem j� a form�tuma
				
				
				nevmezo.setText(adatbeolvaso.getdinamikustomb().get(kivalasztva)[kernev] + " " + adatbeolvaso.getdinamikustomb().get(kivalasztva)[veznev]);
				emailmezo.setText(adatbeolvaso.getdinamikustomb().get(kivalasztva)[email]);
				szulidomezo.setText(szulidotomb[2] + " - " + szulidotomb[0] + " - " + szulidotomb[1]);
				telefonmezo.setText(adatbeolvaso.getdinamikustomb().get(kivalasztva)[telefonszam]);
				cimmezo.setText(adatbeolvaso.getdinamikustomb().get(kivalasztva)[iranyitoszam] + " " + adatbeolvaso.getdinamikustomb().get(kivalasztva)[varos] + " " + adatbeolvaso.getdinamikustomb().get(kivalasztva)[megye] );
				
			}
		}
	}
	
	class Elemkivalaszto2 implements ListSelectionListener				//elem kiv�laszt�sakor mi t�rt�njen oszt�ly
	{
		public void valueChanged(ListSelectionEvent e)					//ezt a met�dust kell leimplement�lni
		{
			if(e.getValueIsAdjusting() == true)							//amint befejezt�k a kijel�l�st csak akkor indul, nem k�zben
			{
											
				int kivalasztva = lista.getSelectedIndex();				//a v�ltoz� a kiv�lasztott elem �rt�k�t kapja meg.
				
				String[] szulidotomb2 = kereseseredmenytomb.get(kivalasztva)[szuletesiido].split("/");			//sz�let�si id� feldarabol�sa mert nem j� a form�tuma
				
				
				nevmezo.setText(kereseseredmenytomb.get(kivalasztva)[kernev] + " " + kereseseredmenytomb.get(kivalasztva)[veznev]);				//mez� kitolt�se a megfelel� adattal
				emailmezo.setText(kereseseredmenytomb.get(kivalasztva)[email]);																	//mez� kitolt�se a megfelel� adattal
				szulidomezo.setText(szulidotomb2[2] + " - " + szulidotomb2[0] + " - " + szulidotomb2[1]);										//mez� kitolt�se a megfelel� adattal
				telefonmezo.setText(kereseseredmenytomb.get(kivalasztva)[telefonszam]);															//mez� kitolt�se a megfelel� adattal
				cimmezo.setText(kereseseredmenytomb.get(kivalasztva)[iranyitoszam] + " " + kereseseredmenytomb.get(kivalasztva)[varos] + " " + kereseseredmenytomb.get(kivalasztva)[megye] );			//mez� kitolt�se a megfelel� adattal
				
			}
		}
	}
	
	
	
	class Kereses implements ActionListener				/*** Csak a vezet�knevek �s keresztnevek k�z�tt keres  ***/ 
	{
		 public void actionPerformed(ActionEvent e)
		 {
			 kereseseredmenytomb.clear();										//keres�seredm�nyt�mb �r�t�se
			 measureTime(true);													//id�m�r� ind�t�sa
			String keresettszo = keresomezo.getText().toLowerCase();			//keres�mez�be �rt sz�veget kisbet�kre alak�tja �s elt�rolja egy v�ltoz�ban
			String[] szulidotomb;
			Boolean eredmeny = false;											//keres�s eredm�ny�re haszn�lt logikai v�ltoz�
			String[] koztesszo = keresettszo.split(" ");						//a sz�k�zn�l kett�bontja a keresend� nevet		
			
			if(koztesszo.length > 1)											//ellen�rzi, hogy val�ban 2 n�v lett-e be�rva ha nem le sem fut a keres�s
			{
				for(int szamlalo = 0; szamlalo < adatbeolvaso.getdinamikustomb().size(); szamlalo++)		//sima for ciklus v�ggmegy az eg�sz adatb�zison
				{	
						
						if(adatbeolvaso.getdinamikustomb().get(szamlalo)[kernev].toLowerCase().equals(koztesszo[0]) && adatbeolvaso.getdinamikustomb().get(szamlalo)[veznev].toLowerCase().equals(koztesszo[1]))		//�talak�tja kisbet�sre a neveket majd �sszehasonl�tja a keres�mez�ben megadott n�vvel van e egyez�
						{
							szulidotomb = adatbeolvaso.getdinamikustomb().get(szamlalo)[szuletesiido].split("/");
							
							nevmezo.setText(adatbeolvaso.getdinamikustomb().get(szamlalo)[kernev] + " " + adatbeolvaso.getdinamikustomb().get(szamlalo)[veznev]);
							emailmezo.setText(adatbeolvaso.getdinamikustomb().get(szamlalo)[email]);
							szulidomezo.setText(szulidotomb[2] + " - " + szulidotomb[0] + " - " + szulidotomb[1]);
							telefonmezo.setText(adatbeolvaso.getdinamikustomb().get(szamlalo)[telefonszam]);
							cimmezo.setText(adatbeolvaso.getdinamikustomb().get(szamlalo)[iranyitoszam] + " " + adatbeolvaso.getdinamikustomb().get(szamlalo)[varos] + " " + adatbeolvaso.getdinamikustomb().get(szamlalo)[megye] );
							
							eredmeny = true;									//keres�s eredm�ny�t jelz� logiaki v�ltoz�t igazra �ll�tja
							
							kereseseredmenytomb.add(adatbeolvaso.getdinamikustomb().get(szamlalo));			//ahol egyez�s volt hozz�adja a keres�seredm�ny t�mbh�z
						}
						
				}
			}
			System.out.println("A nevek k�zti keres�si id�: " + (measureTime(false) / 1000000) + "ms");			//id�m�r� le�ll�t�sa �s az eredm�ny kiirat�sa a konzolra
			
			if(eredmeny == false)
			{
				JOptionPane.showMessageDialog(null, "Nincs ilyen n�v. Figyelj a kis �s nagybet�kre", "T�j�koztat� �zenet", 1);		//hiba�zenet ablak megnyit�sa ha nem volt tal�lat
			}
			
			if(eredmeny == true)			//ha volt tal�lat
			{
				Listacserelo();				//ez a met�dus bet�lti au �j list�t �s hozz� az �j elemkiv�laszt�t
			} 
		 } 
	}
	
	class Kereses2 implements ActionListener			/***  Hash keres�s ***/
	{
		 public void actionPerformed(ActionEvent e)
		 {
			kereseseredmenytomb.clear();												//keres�seredm�nyt�mb �r�t�se
			measureTime(true);
			String keresettszo = keresomezo.getText();
			String[] szulidotomb;
				
			String[] talalat = adatbeolvaso.getHash().get(keresettszo.hashCode());		//hash k�d ellen�rz�se, ha van az eredm�ny a t�mbe ker�l
				
					if(talalat != null)													//ha volt tal�lat akkor lefut
					{
						szulidotomb = talalat[szuletesiido].split("/");
						
						nevmezo.setText(talalat[kernev] + " " + talalat[veznev]);
						emailmezo.setText(talalat[email]);
						szulidomezo.setText(szulidotomb[2] + " - " + szulidotomb[0] + " - " + szulidotomb[1]);
						telefonmezo.setText(talalat[telefonszam]);
						cimmezo.setText(talalat[iranyitoszam] + " " + talalat[varos] + " " + talalat[megye] );
						
						kereseseredmenytomb.add(talalat);								//keres�seredm�nyt�mbh�z hozz�adja a tal�latot
						Listacserelo();													//bet�lti az �j list�t a tal�lattal

					}

					else																//ha nincsen ilyen hash akkor fut le
					{
						JOptionPane.showMessageDialog(null, "Nincs ilyen n�v az adatb�zisban", "T�j�koztat� �zenet", 1);
					}
					
					System.out.println("A hash keres�si id�: " + (measureTime(false) / 1000000) + "ms");			//id�m�r� le�ll�t�sa �s eredm�ny kiirat�sa
		 }
	}
	
	class Kereses3 implements ActionListener				/*** Az eg�sz adatb�zisban keres, minden rekordot ellen�rizve ***/
	{
		 public void actionPerformed(ActionEvent e)
		 {
			kereseseredmenytomb.clear();														//keres�seredm�nyt�mb �r�t�se
			measureTime(true);																	//id�m�r� ind�t�sa
			String keresettszo = keresomezo.getText().toLowerCase();							//�talak�tja a keres�mez�ben lev� sz�veget kisbet�sre �s hozz�adja a v�ltoz�nak
			String[] szulidotomb;
			Boolean eredmeny = false;
			
			/*** eg�sz adatb�zisban keres  ***/
			
			for(int szamlalo = 0; szamlalo < adatbeolvaso.getdinamikustomb().size(); szamlalo++)								//for ciklus v�gigmegy az eg�sz list�n
			{
				for(int szamlalo2 = 0; szamlalo2 < adatbeolvaso.getdinamikustomb().get(szamlalo).length; szamlalo2++)			//for ciklus az egyes t�mb elemein megy v�gig
				{
					
					if(adatbeolvaso.getdinamikustomb().get(szamlalo)[szamlalo2].toLowerCase().equals(keresettszo))				//ha tal�lt egyez�st valahol akkor fut le
					{
						if(kereseseredmenytomb.size() != 0)																			//leellen�rzi, hogy �res-e a keres�seredm�nyt�mb
						{
							if(kereseseredmenytomb.get(kereseseredmenytomb.size()-1)[email].contains(adatbeolvaso.getdinamikustomb().get(szamlalo)[email]) == false) 		//lellen�rzi, hogy a tal�lat t�mb email c�me szerepel-e m�r a keres�si eredm�nyek k�z�tt, �s ha nem hozz�adja a tal�latot
							{
								kereseseredmenytomb.add(adatbeolvaso.getdinamikustomb().get(szamlalo));
							}
						}
						else																									//ha m�g �res a keres�seredm�ny akkor fut le, nehogy indexel�si hiba legyen
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
			
			System.out.println("A keres�si id� a teljes adatb�ziosban:: " + (measureTime(false) / 1000000) + "ms");					//id�m�r� le�ll�t�sa �s eredm�ny kiirat�sa a konzolra
			
			if(eredmeny == false)
			{
				JOptionPane.showMessageDialog(null, "Nem volt tal�lat az adatb�zisban!", "T�j�koztat� �zenet", 1);		//ha nem volt tal�lat felugrik a t�j�koztat� �zenet
			}
			
			if(eredmeny == true)					//ha volt tal�lat
			{
				Listacserelo();						//bet�lti a met�dus az �j list�t
			}
		 }
	}
	
	class Mezofigyelo implements DocumentListener														//keres�mez� v�ltoz�sait figyel� Listener
	{
			
			public void insertUpdate(DocumentEvent e) 													//be�rt karakterek eset�n fut le
			{
				String kereses = keresomezo.getText().toLowerCase();									//�talak�tja kisbet�kre a be�rt sz�veget
				kereseseredmenytomb.clear();															//keres�seredm�nyt�mb �r�t�se
				int szamlalo2 = 0;																		//seg�dv�ltoz� l�trehoz�sa
				
				
				for(int szamlalo = 0; szamlalo < adatbeolvaso.getdinamikustomb().size(); szamlalo++)		//for ciklus v�gigmegy az eg�szen
				{	
					if(adatbeolvaso.getdinamikustomb().get(szamlalo)[kernev].toLowerCase().indexOf(kereses) == szamlalo2)			//leellen�rzi, hogy a be�rt karakter indexe megegyezik e valamelyik n�v azanos index�vel
					{
						kereseseredmenytomb.add(adatbeolvaso.getdinamikustomb().get(szamlalo));										//ahol egyez�s van hozz�adja a keres�seredm�nyt�mbh�z
					}
				}
				szamlalo2 += 1;																			//seg�dv�ltoz�t n�velem 1-el, hogy megn�zzem a k�vetkez� karakteret
			
				Listacserelo();																		//majd kiiratom az aktu�lis eredm�nyeket
			}

			public void removeUpdate(DocumentEvent e) 				//t�rl�sn�l fut le
			{
				String ures = keresomezo.getText();
				
				if(ures.equals(""))									//amennyiben �res a sz�vegmez� 
				{
					Listavisszacserelo();							//vissza�ll�tja az eredeti list�t
				}
				
			}
			
			public void changedUpdate(DocumentEvent e) 
			{
																		
			}	
		}
	
	public void Listacserelo()																//listacser�l� met�dus. a keres�si eredm�nyeket t�lti be a Jlistbe
	{
		ListModel<String> keresesilistmodel = new ListModel<String>()						// saj�t ListModelt hozunk l�tre
				 {
			 		
				     public int getSize()													// Megval�s�tjuk a getSize() abstrakt met�dust
				     {
				    
				    	 return kereseseredmenytomb.size(); 								// Ami visszaadja a List, adatlista m�ret�t, hogy abban h�ny elem van
				    	 
				     }
				     public String getElementAt(int index) 									// Megval�s�tjuk az elemlek�rdez� abstract met�dust
				     { 
				    	 String tmp_str = "";
				 
				    	tmp_str = kereseseredmenytomb.get(index)[kernev] + " " + kereseseredmenytomb.get(index)[veznev];		//csak a kereszt �s vezet�knevek t�lti be
				    			 
				    	return tmp_str;														// Majd visszaadja az �sszef�z�tt Stringet
			    	 }
		    	 
				 /* ListDataListenerek is kelleni fognak, de azokat most nem implement�ljuk le*/
				 /* ListDataListenerek is kelleni fognak, de azokat most nem implement�ljuk le*/
				 public void addListDataListener(ListDataListener l) {}
				 public void removeListDataListener(ListDataListener l) {}
				 }; 
			 
		lista.setModel(keresesilistmodel);
		lista.removeListSelectionListener(kivalasztva);
		lista.addListSelectionListener(kivalasztva2);
	}
	
	public void Listavisszacserelo()
	{
		ListModel<String> sajatlistmodel = new ListModel<String>()								// saj�t ListModelt hozunk l�tre
				 {
			 		
				     public int getSize()														// Megval�s�tjuk a getSize() abstrakt met�dust
				     {
				    	 return adatbeolvaso.getdinamikustomb().size(); 						// Ami visszaadja a List, adatlista m�ret�t, hogy abban h�ny elem van
				     }
				     public String getElementAt(int index) 										// Megval�s�tjuk az elemlek�rdez� abstract met�dust
				     { 
				    	 String tmp_str = "";
				    	 
				    	 tmp_str = adatbeolvaso.getdinamikustomb().get(index)[kernev] + " " + adatbeolvaso.getdinamikustomb().get(index)[veznev];
				    			 
				    	 return tmp_str;														// Majd visszaadja az �sszef�z�tt Stringet
			    	 }
		    	 
				 /* ListDataListenerek is kelleni fognak, de azokat most nem implement�ljuk le*/
				 /* ListDataListenerek is kelleni fognak, de azokat most nem implement�ljuk le*/
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
			adatbeolvaso = new Adat(".\\adat\\50000 Records.csv");						//�j adat objektum l�trehoz�sa, megadva neki melyik f�jlt kell beolvasni
			measureTime(true);															//id�m�r� ind�t�sa
			adatbeolvaso.beolvasas(",");												//adatb�zis beolvas�sa �s feldolgoz�sa met�dus megh�v�sa az Adat oszt�lyb�l
			
			System.out.println("Az adatb�zis beolvas�s�nak ideje: " + (measureTime(false) / 1000000) + "ms");		//id�m�r� lez�r�sa �s az eredm�ny kiirat�sa
			
			synchronized(zar_1)
			{
				zar_1.notify();		// �rtes�tj�k a zar_1-t, hogy mehet	
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
					ablak frame = new ablak();							//ablak l�trehoz�sa
					frame.setSize(800, 600);							//ablak m�ret�nek megad�sa
					synchronized(zar_1)
					{
						try
						{
							zar_1.wait();			// V�rakozunk a jelz�sre
						}
						catch (InterruptedException e)
						{
							System.out.print(e);
						}
						frame.listakeszito();								//listak�sz�t� met�dus megh�v�sa.
					}
					
					
					frame.setVisible(true);								//l�that�v� tessz�k az ablakot
					System.out.println("Ablak l�trehoz�s�nak ideje: " + (measureTime(false) / 1000000) + "ms");			//kiiratom a m�rt eredm�nyt
					
					
				} 
				catch (Exception e) 									//kiv�tel elkap�sa
				{
					e.printStackTrace();								//elkap�s eset�n t�rt�nik - ki�rja a konzolra 
				}
			}
		});

	}
}
