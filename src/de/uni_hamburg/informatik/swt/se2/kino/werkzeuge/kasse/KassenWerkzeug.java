package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.kasse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import de.uni_hamburg.informatik.swt.se2.kino.fachwerte.Datum;
import de.uni_hamburg.informatik.swt.se2.kino.materialien.Kino;
import de.uni_hamburg.informatik.swt.se2.kino.materialien.Tagesplan;
import de.uni_hamburg.informatik.swt.se2.kino.materialien.Vorstellung;
import de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.BeobachtbaresWerkzeug;
import de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.WerkzeugBeobachter;
import de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.datumsauswaehler.DatumAuswaehlWerkzeug;
import de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.platzverkauf.PlatzVerkaufsWerkzeug;
import de.uni_hamburg.informatik.swt.se2.kino.werkzeuge.vorstellungsauswaehler.VorstellungsAuswaehlWerkzeug;

/**
 * Das Kassenwerkzeug. Mit diesem Werkzeug kann die Benutzerin oder der Benutzer
 * eine Vorstellung auswählen und Karten für diese Vorstellung verkaufen und
 * stornieren.
 * 
 * @author SE2-Team
 * @version SoSe 2021
 */
public class KassenWerkzeug
{
    // Das Material dieses Werkzeugs
    private Kino _kino;

    // UI dieses Werkzeugs
    private KassenWerkzeugUI _ui;

    // Die Subwerkzeuge
    private PlatzVerkaufsWerkzeug _platzVerkaufsWerkzeug;
    private DatumAuswaehlWerkzeug _datumAuswaehlWerkzeug;
    private VorstellungsAuswaehlWerkzeug _vorstellungAuswaehlWerkzeug;
    private Collection<? extends WerkzeugBeobachter<? extends BeobachtbaresWerkzeug>> _beobachter;

    /**
     * Initialisiert das Kassenwerkzeug.
     * 
     * @param kino das Kino, mit dem das Werkzeug arbeitet.
     * 
     * @require kino != null
     */
    public KassenWerkzeug(Kino kino)
    {
        assert kino != null : "Vorbedingung verletzt: kino != null";

        _kino = kino;

        // Subwerkzeuge erstellen
        _platzVerkaufsWerkzeug = new PlatzVerkaufsWerkzeug();
        _datumAuswaehlWerkzeug = new DatumAuswaehlWerkzeug();
        _vorstellungAuswaehlWerkzeug = new VorstellungsAuswaehlWerkzeug();

        _beobachter = registriereBeobachter();

        // UI erstellen (mit eingebetteten UIs der direkten Subwerkzeuge)
        _ui = new KassenWerkzeugUI(_platzVerkaufsWerkzeug.getUIPanel(),
                _datumAuswaehlWerkzeug.getUIPanel(),
                _vorstellungAuswaehlWerkzeug.getUIPanel());

        registriereUIAktionen();
        setzeTagesplanFuerAusgewaehltesDatum();
        setzeAusgewaehlteVorstellung();

        _ui.zeigeFenster();
    }

    /**
     * Registriere Kassenwerkzeug bei seinen Subwerkzeugen als Beobachter
     */
    private Collection<? extends WerkzeugBeobachter<? extends BeobachtbaresWerkzeug>> registriereBeobachter()
    {
        class DatumAuswahlBeobachter implements WerkzeugBeobachter<DatumAuswaehlWerkzeug>
        {
            @Override
            public void reagiereAufAenderung(DatumAuswaehlWerkzeug werkzeug)
            {
                setzeTagesplanFuerAusgewaehltesDatum();
            }
        }

        class VorstellungsAuswahlBeobachter implements WerkzeugBeobachter<VorstellungsAuswaehlWerkzeug>
        {
            @Override
            public void reagiereAufAenderung(VorstellungsAuswaehlWerkzeug werkzeug)
            {
                setzeAusgewaehlteVorstellung();
            }
        }

        return new HashSet<>(){{
            add(new DatumAuswahlBeobachter());
            add(new VorstellungsAuswahlBeobachter());
        }};
	}
    
	/**
     * Fügt die Funktionalitat zum Beenden-Button hinzu.
     */
    private void registriereUIAktionen()
    {
        _ui.getBeendenButton().addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                reagiereAufBeendenButton();
            }
        });
    }

    /**
     * Setzt den in diesem Werkzeug angezeigten Tagesplan basierend auf dem
     * derzeit im DatumsAuswahlWerkzeug ausgewählten Datum.
     */
    private void setzeTagesplanFuerAusgewaehltesDatum()
    {
        Tagesplan tagesplan = _kino.getTagesplan(getAusgewaehltesDatum());
        _vorstellungAuswaehlWerkzeug.setTagesplan(tagesplan);
    }

    /**
     * Passt die Anzeige an, wenn eine andere Vorstellung gewählt wurde.
     */
    private void setzeAusgewaehlteVorstellung()
    {
        _platzVerkaufsWerkzeug.setVorstellung(getAusgewaehlteVorstellung());
    }

    /**
     * Beendet die Anwendung.
     */
    private void reagiereAufBeendenButton()
    {
        _ui.schliesseFenster();
    }

    /**
     * Gibt das derzeit gewählte Datum zurück.
     */
    private Datum getAusgewaehltesDatum()
    {
        return _datumAuswaehlWerkzeug.getSelektiertesDatum();
    }

    /**
     * Gibt die derzeit ausgewaehlte Vorstellung zurück. Wenn keine Vorstellung
     * ausgewählt ist, wird <code>null</code> zurückgegeben.
     */
    private Vorstellung getAusgewaehlteVorstellung()
    {
        return _vorstellungAuswaehlWerkzeug.getAusgewaehlteVorstellung();
    }
}
