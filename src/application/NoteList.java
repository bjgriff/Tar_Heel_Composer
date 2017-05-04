package application;
import java.util.HashMap;
import java.util.ArrayList;

public class NoteList {
	private ArrayList<Note> pianoKeys;
	private HashMap<String, Note> noteDict;

	public NoteList(){
		this.pianoKeys = new ArrayList<Note>();
		this.noteDict = new HashMap<String, Note>();
		for(int counter = 0, octave = 0; counter < 100; counter ++){
			if(counter % 12 == 0)
				octave ++;
			String name = getName(counter);
			name = name + octave;
			pianoKeys.add(new Note(name, counter + 12));
			noteDict.put(name, pianoKeys.get(counter));
		}
	}
	
	public Note getNote(int n){
		return pianoKeys.get(n);
	}
	
	public Note getNote(String n){
		return noteDict.get(n);
	}
	
	private String getName(int keyNum){
		int note = keyNum % 12;
		switch(note){
		case 0: return "C";
		case 1: return "C#";
		case 2: return "D";
		case 3: return "D#";
		case 4: return "E";
		case 5: return "F";
		case 6: return "F#";
		case 7: return "G";
		case 8: return "G#";
		case 9: return "A";
		case 10: return "A#";
		case 11: return "B";
		default: return null;
		}
	}
}
