package application;

import java.util.ArrayList;

public class Composition {
	
	private ArrayList<Note> noteList;			//These are strings for now, will be Notes and Chords later
	private ArrayList<Chord> chordList;
	//private Player piano;
	//could add an "isFull" boolean here if we need a check for a completed composition once deletes are implemented
	
	public Composition(){
		noteList = new ArrayList<Note>();		//Arrays of 8 chords and notes, can be extended if needed
		chordList = new ArrayList<Chord>();
		//piano = new Player();
	}
	
	public void setChord(int index, Chord chord){
		chordList.add(index, chord);
	}
	
	public void addChord(Chord c){
		this.chordList.add(c);
	}
	
	public int getSize(){
		return this.getChordArray().size();
	}
	
	public int getNoteSize(){
		return this.getNoteArray().size();
	}
	
	public int noteLength(){
		return this.getNoteArray().size();
	}
	
	public Chord getChord(int index){
		if(index >= this.getSize())
			return null;
		return chordList.get(index);
	}
	
	public void setNote(int index, Note note){
		noteList.set(index, note);
	}
	
	public void addNote(Note note){
		noteList.add(note);
	}
	
	public void printNames(){
		for(Chord c : chordList){
			c.printName();
		}
	}
	
	public Note getNote(int index){
		if(index >= noteList.size())
			return null;
		return noteList.get(index);
	}
	
	public ArrayList<Chord> getChordArray(){
		return chordList;
	}
	
	public ArrayList<Note> getNoteArray(){
		return noteList;
	}
	
	public void clear(){
		chordList.clear();
		noteList.clear();
	}
	
	public void removeLastNote(){
		noteList.remove(noteList.size()-1);
	}
	
	public void removeLastChord(){
		chordList.remove(chordList.size()-1);
	}
	
}
