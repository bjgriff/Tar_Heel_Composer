package application;


public class Note {
	
	String name;
	int midiNum;
	
	public Note(String noteName, int midiNum){
		name = noteName;
		this.midiNum = midiNum;
	}
	
	public String getName(){		// Not sure if MIDI file needs the note object or can play it directly, one of these methods will be used
		return name;
	}
	
	public int getNum(){
		return midiNum;
	}
	
	public void play(){			// Method to play it directly
		//code to play the note's sound
	}
}
