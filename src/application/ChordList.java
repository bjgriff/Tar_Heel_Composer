package application;

import java.util.HashMap;
import java.util.Map;

public class ChordList {

	Map<String, Chord> chordDict;
	NoteList piano;

	public ChordList(NoteList keys){
		chordDict = new HashMap<String, Chord>(); 	// A chord dictionary
		piano = keys;

		chordSetup();
	}

	public Chord getChord(String chordName){
		return chordDict.get(chordName);
	}

	public void playChord(String chordName){
		getChord(chordName).play();
	}

	
	private void chordSetup(){
		makeChord("I", 36, 1, 0, false);
		makeChord("ii", 38, 0, 0, false);
		makeChord("iii", 40, 0, 0, false);
		makeChord("IV",29, 1, 0, false);
		makeChord("V", 31, 1, 0, false);
		makeChord("vi", 33, 0, 0, false);
		makeChord("vii'", 35, 0, 1, false);
		makeChord("V7", 31, 1, 0, true);
		makeChord("V7/V", 38, 1, 0, true);
		makeChord("vii'7", 35, 0, 1, true);
	}

	//populates each chord with the notes it contains
	private void makeChord(String name, int root, int major, int diminished, boolean seventh){
		Chord temp = new Chord(name);
		int[] intervals = {0, 7 - diminished, 12, 15 + major, 19-diminished, 24};
		for(int i = 0; i < 6; i++)
			temp.addMemberNote(piano.getNote(root + intervals[i]));
		if(seventh)
			temp.insertMemberNote(piano.getNote(root + 22), 5);
		for(int i = 1; i < 5; i++){
			int chordSize = temp.getMemberNotes().size();
			int noteIndex = temp.getMemberNote(chordSize - i).getNum();
			//noteIndex += 12;
			temp.addOfferedNote(piano.getNote(noteIndex));
		}
		addNexts(temp);
		//temp.printMembers();
		//temp.printOffered();
		chordDict.put(name, temp);
	}

	//populates the NextChord list of each chord
	private void addNexts(Chord temp){
		String name = temp.getName();
		switch(name){
		case "I": 
			temp.addNextChord("IV");
			temp.addNextChord("ii");
			temp.addNextChord("V");
			temp.addNextChord("I");
			temp.addNextChord("vii'");
			temp.addNextChord("vi");
			temp.addNextChord("iii");
			temp.addNextChord("V7");
			temp.addNextChord("V7/V");
			break;
		case "ii":
			temp.addNextChord("V");
			temp.addNextChord("V7/V");
			temp.addNextChord("vii'");
			temp.addNextChord("vii'7");
			break;
		case "iii":
			temp.addNextChord("vi");
			temp.addNextChord("IV");
			break;
		case "IV":
			temp.addNextChord("V");
			temp.addNextChord("ii");
			temp.addNextChord("V7");
			temp.addNextChord("V7/V");
			break;
		case "V": 
			temp.addNextChord("I");
			temp.addNextChord("vi");
			temp.addNextChord("vii'");
			break;
		case "vi":
			temp.addNextChord("IV");
			temp.addNextChord("ii");
			break;
		case "vii'":
			temp.addNextChord("I");
			temp.addNextChord("vii'7");
			break;
		case "vii'7":
			temp.addNextChord("I");
			temp.addNextChord("vii'");
			break;
		case "V7/V":
			temp.addNextChord("V");
			temp.addNextChord("V7");
			break;
		case "V7":
			temp.addNextChord("I");
			temp.addNextChord("IV");
			break;
		}
		
		
	}
}
