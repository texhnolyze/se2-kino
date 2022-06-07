package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge;

import java.util.HashSet;
import java.util.Set;

/**
 * Basisklasse für Werkzeuge, die ihr andere Werkzeuge bei Änderungen
 * benachrichtigen möchten.
 * 
 * Diese Klasse implementiert die Schnittstelle, über die sich Beobachter an dem
 * Subwerkzeug mit einer Funktion registrieren können.
 *
 * Erbende Klassen rufen die Methode #informiereUeberAenderung() auf, um alle
 * Beobachter zu benachrichtigen. Erbende Klassen müssen dokumentieren, in
 * welchen Fällen sie ihre Beobachter informieren.
 * 
 * Diese Klasse entspricht der Klasse "Beobachtbar" im Beobachter-Muster.
 */
public abstract class BeobachtbaresWerkzeug
{
    private final Set<Runnable> _beobachter;

    /**
     * Initialisiert ein beobachtbares Werkzeug.
     */
    public BeobachtbaresWerkzeug()
    {
        _beobachter = new HashSet<>();
    }

    /**
     * Registriert einen Beobachter an diesem Werkzeug. Der Beobachter wird
     * informiert, wenn sich bei diesem Werkzeug etwas ändert.
     * 
     * @require beobachter != null
     */
    public void registriereBeobachter(Runnable beobachter)
    {
        assert beobachter != null : "Vorbedingung verletzt: beobachter != null";
        _beobachter.add(beobachter);
    }

    /**
     * Entfernt einen Beobachter dieses Werkzeugs. Wenn der Beobachter gar
     * nicht registriert war, passiert nichts.
     */
    public void entferneBeobachter(Runnable beobachter)
    {
        _beobachter.remove(beobachter);
    }

    /**
     * Informiert alle an diesem Werkzeug registrierten Beobachter über eine
     * Änderung.
     * 
     * Diese Methode muss von der erbenden Klasse immer dann aufgerufen werden,
     * wenn eine Änderung geschehen ist, die für Beobachter interessant ist.
     */
    protected void informiereUeberAenderung()
    {
        _beobachter.forEach(Runnable::run);
    }
}
