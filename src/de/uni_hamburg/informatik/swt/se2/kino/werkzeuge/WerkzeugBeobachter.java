package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge;

/**
 * Interface, welches Beobachter implementieren müssen
 * @author philipp
 *
 */
public interface WerkzeugBeobachter 
{
    /**
     * Reagiert auf Veränderungen in den beobachtbaren Werkzeugen
     * 
     * @param werkzeug  das Werkzeug, in welchem die Veränderung aufgetreten ist
     * 
     * @require werkzeug != null
     */
	void reagiereAufAenderung(BeobachtbaresWerkzeug werkzeug);
}
