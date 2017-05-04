package application;

import java.util.*;

public class Chord {
	
	private	String name;
	private ArrayList<String> nextChord;
	private ArrayList<Note> memberNotes;
	private ArrayList<Note> offeredNotes;
	
	public Chord(String chordName){
		this.name = chordName;
		this.nextChord = new ArrayList<String>();
		this.memberNotes = new ArrayList<Note>();
		this.offeredNotes = new ArrayList<Note>();
	}
	
	public void addOfferedNote(Note n){
		this.offeredNotes.add(n);
	}
	
	public void addMemberNote(Note n){
		this.memberNotes.add(n);
	}
	
	public void replaceMemberNote(int index, Note n){
		this.memberNotes.set(index, n);
	}
	
	public Note getOfferedNote(int i){
		return this.offeredNotes.get(i);
	}
	
	public Note getMemberNote(int i){
		return this.memberNotes.get(i);
	}
	public void addNextChord(String c){
		this.nextChord.add(c);
	}
	
	public void insertMemberNote(Note n, int index){
		this.memberNotes.add(index, n);
	}
	
	public void insertOfferedNote(Note n, int index){
		this.offeredNotes.add(index, n);
	}
	
	public ArrayList<Note> getOfferedNotes(){
		return this.offeredNotes;
	}
	
	public ArrayList<Note> getMemberNotes(){
		return this.memberNotes;
	}
	
	public String getName(){		// Not sure if MIDI file needs the note object or can play it directly, one of these methods will be used
		return name;
	}
	
	public void play(){			// Method to play it directly
		//code to play the chord's sound
	}
	
	public String getNextChord(){
		double next = Math.random();
		double[] odds = new double[10];
		odds[0] = 0;
		odds[9] = 2.9;
		for(int counter = 1; counter < 9; counter ++){
			odds[counter] = odds[counter - 1] + Math.pow(.75, counter);
		}
		for(int counter = 9; counter >= 0; counter --){
			if (odds[counter] <= next){
				try{
					return nextChord.get(counter);
				}catch(IndexOutOfBoundsException e){
					return nextChord.get(nextChord.size()-1);
				}
			}
		}
		System.out.println("Error in getNextChord");
		return null;
	}
	
	public ArrayList<String> getNextChords(){
		return this.nextChord;
	}
	
	public Note getNextNote(){
		double next = Math.random();
		double[] odds = {0.25, 0.5, 0.75, 1.0};
		int length = offeredNotes.size();
		if(next < odds[0]){return offeredNotes.get(length-3);}
		else if(next < odds[1]){return offeredNotes.get(length-2);}
		else if(next < odds[2]){return offeredNotes.get(length-1);}
		else {return offeredNotes.get(length-1);}
		
	}
	
	public void print(){
		System.out.println(this.name);
		System.out.println("Next Chord: " + this.getNextChord());
		System.out.println("Next Note: " + this.getNextNote().getName());
	}
	
	public void printName(){
		System.out.println(this.name);
	}
	
	public void printMembers(){
		System.out.print(this.name + ": ");
		for (Note n : this.memberNotes)
			System.out.print(n.getName()+ " ");
		System.out.println();
	}
	
	public void printOffered(){
		System.out.print("Offered notes: ");
		for (Note n : this.offeredNotes)
			System.out.print(n.getName()+ " ");
		System.out.println();
	}
}

