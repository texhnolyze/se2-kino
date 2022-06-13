package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge;

import java.util.HashSet;
import java.util.Set;


/**
 * Abstrakte Implementation des Observer-Patterns
 */
public abstract class BeobachtbaresWerkzeug
{
	private Set<WerkzeugBeobachter> _alleBeobachter;
	
	/**
	 * Konstruktor 
	 */
	public BeobachtbaresWerkzeug()
	{
		_alleBeobachter = new HashSet<WerkzeugBeobachter>();
	}
	
	/**
	 * Überprüft, ob ein gegebener WerkzeugBeobachter als Beobachter registriert ist.
	 * 
	 * @param beobachter  für welchen der Registrierstatus abgefragt werden soll
	 * 
	 * @return boolean  true wenn registriert, false wenn nicht registriert
	 * 
	 * @require beoachter != null
	 */
	public boolean istRegistriert(WerkzeugBeobachter beobachter)
	{
		assert beobachter != null : "Vorbedingung verletzt: beobachter != null";
		
		return _alleBeobachter.contains(beobachter);
	}
	
	/**
	 * Registriere einen neuen Beobachter.
	 * 
	 * @param beobachter  welcher beim beobachtbaren Werkzeug registriert werden soll
	 * 
	 * @require beobachter != null
	 * @require !istRegistriert(beobachter)
	 * 
	 * @ensure istRegistriert(beobachter)
	 */
	public void registriereBeobachter(WerkzeugBeobachter beobachter)
	{
		assert beobachter != null : "Vorbedingung verletzt: beobachter != null";
		assert !istRegistriert(beobachter) : "Vorbedingung verletzt: !istRegistriert(beobachter)";
		
		_alleBeobachter.add(beobachter);
	}
	
	/**
	 * Entferne einen Beobachter aus der Liste der Beobachter.
	 * 
	 * @param beobachter  welcher beim beobachtbaren Werkzeug entfernt werden soll
	 * 
	 * @require beobachter != null
	 * @require istRegistriert(beobachter)
	 * 
	 * @ensure !istRegistriert(beobachter)
	 */
	public void entferneBeobachter(WerkzeugBeobachter beobachter)
	{
		assert beobachter != null : "Vorbedingung verletzt: beobachter != null";
		assert istRegistriert(beobachter) : "Vorbedingung verletzt: istRegistriert(beobachter)";
		
		_alleBeobachter.remove(beobachter);
	}
	
	/**
	 * Informiert alle bis zum Zeitpunkt des Aufrufs registrierten WerkzeugBeobachter
	 * darüber, dass im Werkzeug eine Änderung stattgefunden hat.
	 */
	protected void informiereUeberAenderung()
	{
		for (WerkzeugBeobachter beobachter : _alleBeobachter)
		{
			beobachter.reagiereAufAenderung(this);
		}
	}
}
