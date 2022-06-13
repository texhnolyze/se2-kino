package de.uni_hamburg.informatik.swt.se2.kino.werkzeuge;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BeobachtbaresWerkzeugTest {

	private BeobachtbaresWerkzeug _dummyWerkzeug;
	private WerkzeugBeobachter _beobachter1;
	private WerkzeugBeobachter _beobachter2;
	private WerkzeugBeobachter _beobachter3;
	
	private class DummyWerkzeugBeobachter implements WerkzeugBeobachter
	{
		private boolean _called;

		@Override
		public void reagiereAufAenderung(BeobachtbaresWerkzeug werkzeug) {
			_called = true;
		}
		
		public boolean wurdeAufgerufen()
		{
			return _called;
		}
	}
	
	
	@Before
	public void setUp() throws Exception
	{
		
		_dummyWerkzeug = new BeobachtbaresWerkzeug() {};
		_beobachter1 = new DummyWerkzeugBeobachter();
		_beobachter2 = new DummyWerkzeugBeobachter();
		_beobachter3 = new DummyWerkzeugBeobachter();
	}

	@Test
	public void testIstRegistriert() 
	{
		assertEquals(false, _dummyWerkzeug.istRegistriert(_beobachter1));
		
		_dummyWerkzeug.registriereBeobachter(_beobachter1);
		
		assertEquals(true, _dummyWerkzeug.istRegistriert(_beobachter1));
		
		_dummyWerkzeug.entferneBeobachter(_beobachter1);
		
		assertEquals(false, _dummyWerkzeug.istRegistriert(_beobachter1));
	}
	
	@Test
	public void testRegistriereBeobachter()
	{
		assertEquals(false, _dummyWerkzeug.istRegistriert(_beobachter1));
		assertEquals(false, _dummyWerkzeug.istRegistriert(_beobachter2));
		assertEquals(false, _dummyWerkzeug.istRegistriert(_beobachter3));
		
		_dummyWerkzeug.registriereBeobachter(_beobachter1);
		_dummyWerkzeug.registriereBeobachter(_beobachter2);
		_dummyWerkzeug.registriereBeobachter(_beobachter3);
		
		assertEquals(true, _dummyWerkzeug.istRegistriert(_beobachter1));
		assertEquals(true, _dummyWerkzeug.istRegistriert(_beobachter2));
		assertEquals(true, _dummyWerkzeug.istRegistriert(_beobachter3));
	}
	
	@Test
	public void testEntferneBeobachter()
	{
		_dummyWerkzeug.registriereBeobachter(_beobachter1);
		_dummyWerkzeug.registriereBeobachter(_beobachter2);
		_dummyWerkzeug.registriereBeobachter(_beobachter3);
		
		assertEquals(true, _dummyWerkzeug.istRegistriert(_beobachter1));
		assertEquals(true, _dummyWerkzeug.istRegistriert(_beobachter2));
		assertEquals(true, _dummyWerkzeug.istRegistriert(_beobachter3));
		
		_dummyWerkzeug.entferneBeobachter(_beobachter2);
		
		assertEquals(true, _dummyWerkzeug.istRegistriert(_beobachter1));
		assertEquals(false, _dummyWerkzeug.istRegistriert(_beobachter2));
		assertEquals(true, _dummyWerkzeug.istRegistriert(_beobachter3));
		
		_dummyWerkzeug.entferneBeobachter(_beobachter1);
		
		assertEquals(false, _dummyWerkzeug.istRegistriert(_beobachter1));
		assertEquals(false, _dummyWerkzeug.istRegistriert(_beobachter2));
		assertEquals(true, _dummyWerkzeug.istRegistriert(_beobachter3));
		
		_dummyWerkzeug.entferneBeobachter(_beobachter3);
		
		assertEquals(false, _dummyWerkzeug.istRegistriert(_beobachter1));
		assertEquals(false, _dummyWerkzeug.istRegistriert(_beobachter2));
		assertEquals(false, _dummyWerkzeug.istRegistriert(_beobachter3));
	}
	
	@Test
	public void testInformiereUeberAenderung()
	{
		_dummyWerkzeug.registriereBeobachter(_beobachter1);
		_dummyWerkzeug.registriereBeobachter(_beobachter2);
		
		_dummyWerkzeug.informiereUeberAenderung();
		
		assertEquals(true, ((DummyWerkzeugBeobachter) _beobachter1).wurdeAufgerufen());
		assertEquals(true, ((DummyWerkzeugBeobachter) _beobachter2).wurdeAufgerufen());
		assertEquals(false, ((DummyWerkzeugBeobachter) _beobachter3).wurdeAufgerufen());		
	}
}
