package application;

import javax.sound.midi.*;

public class Player {
	private Sequencer piano;
	private Composition piece;
	private int tempo;
	private Sequence s;
	private Track t;

	public Player(Composition piece, int tempo){
		this.piece = piece;
		this.tempo = tempo;
		try{
			piano = MidiSystem.getSequencer();
		}catch(Exception e){
			System.out.println("Midi exception");
		}
	}

	public void playAll(){
		this.playAll(0);
	}

	public void playAll(int startIndex){
		try{
			piano.open();
			s = new Sequence(Sequence.PPQ, 4);
			t = s.createTrack();
			int startBeat, endBeat;
			if(startIndex >= 0){
				startBeat = startIndex;
				endBeat = piece.getSize();
			}else{
				startBeat = 0;
				endBeat = piece.getSize() - startIndex;
				endBeat -= 2;
				if (endBeat == 0)
					this.addChord(piece.getChord(0), 0);
			}

			for(int beat = startBeat; beat < endBeat; beat ++){
				Note temp = piece.getNote(beat);
				if(temp != null)
					this.addNote(temp, beat);
				this.addChord(piece.getChord(beat), beat);
			}
			piano.setSequence(s);
			piano.start();
		}catch(Exception e){
			e.printStackTrace();

		}
	}

	public void playBeat(int i){
		try{
			piano.open();
			s = new Sequence(Sequence.PPQ, 4);
			t = s.createTrack();
			this.addNote(piece.getNote(i), 0);
			this.addChord(piece.getChord(i), 0);
			piano.setSequence(s);
			piano.start();
		}catch(Exception e){

		}
	}

	public void playChord(Chord c){
		try{
			piano.open();
			s = new Sequence(Sequence.PPQ, 4);
			this.t = s.createTrack();
			this.addChord(c, 0);
			piano.setSequence(s);
			piano.start();

		}catch(Exception e){
		}
	}

	public void playNote(Note n){
		try{
			piano.open();
			s = new Sequence(Sequence.PPQ, 4);
			this.t = s.createTrack();
			this.addNote(n, 0);
			piano.setSequence(s);
			piano.start();

		}catch(Exception e){

		}
	}

	public void playAllChords(){
		try{
			piano.open();
			s = new Sequence(Sequence.PPQ, 4);
			t = s.createTrack();
			for(int beat = 0; beat < piece.getSize(); beat ++){
				this.addChord(piece.getChord(beat), beat);
			}
			piano.setSequence(s);
			piano.start();
		}catch(Exception e){

		}
	}

	public void playAllNotes(){
		try{
			piano.open();
			s = new Sequence(Sequence.PPQ, 4);
			t = s.createTrack();
			for(int beat = 0; beat < piece.getSize(); beat ++){
				Note temp = piece.getNote(beat);
				if(temp != null)
					this.addNote(temp, beat);
			}
			piano.setSequence(s);
			piano.start();
			while(piano.isRunning()){

			}
			piano.stop();
		}catch(Exception e){

		}
	}

	private void addNote(Note n, int time) throws InvalidMidiDataException{
		ShortMessage a = new ShortMessage();
		a.setMessage(144, 1, n.getNum(), 100);
		MidiEvent noteOn = new MidiEvent(a, tempo*time);
		t.add(noteOn);

		ShortMessage b = new ShortMessage();
		b.setMessage(128,1,n.getNum(), 100);
		MidiEvent noteOff = new MidiEvent(b, tempo*(time+1) + 4);
		t.add(noteOff);

	}

	private void addChord(Chord c, int time) throws InvalidMidiDataException{
		if (c == null)
			c = piece.getChord(0);
		else {
			for(int i = 0; i < c.getMemberNotes().size() - 4; i++){
				this.addNote(c.getMemberNote(i), time);
			}
		}
	}

	public void setTempo(int i){
		this.tempo = i;
	}
}
