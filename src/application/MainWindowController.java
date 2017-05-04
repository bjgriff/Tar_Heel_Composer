package application;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class MainWindowController {
	
	//Views
	@FXML private Label title;
	@FXML private TextField field;
	@FXML private TextArea instruction_text = new TextArea();
	
	
	@FXML private Label note0;
	@FXML private Label note1;
	@FXML private Label note2;
	@FXML private Label note3;
	@FXML private Label note4;
	@FXML private Label note5;
	@FXML private Label note6;
	@FXML private Label note7;
	
	@FXML private Label chord0;
	@FXML private Label chord1;
	@FXML private Label chord2;
	@FXML private Label chord3;
	@FXML private Label chord4;
	@FXML private Label chord5;
	@FXML private Label chord6;
	@FXML private Label chord7;
	
	@FXML private Button button1;
	@FXML private Button button2;
	@FXML private Button button3;
	@FXML private Button button4;
	
	private Main main;
	private Composition composition;
	private ChordList chordList;
	private NoteList noteList;
	private Player keyboard;
	private int degreesOfFreedom;
	private int decision;
	private int decisionIndex;
	private String[] prompt;
	Note tempNotes[];
	ArrayList<String> candidates;
	int offset;
	boolean fromDef1 = false;
	boolean fromDef2 = false;
	int promptChordsCalls = -3;
	
	public MainWindowController(){
		composition = new Composition();
		keyboard = new Player(composition, 8);
		noteList = new NoteList();
		chordList = new ChordList(noteList);
		prompt = new String[5];
		tempNotes = new Note[5];
	}
	
	private void prompt(){
		String displayString = "";
		for(int i=0;i<5;i++){
			displayString += prompt[i] + "\n";
		}
		setInstructions(displayString);
		//System.out.println(displayString);
	}
	
	public void run(){
		this.welcomeInstructions1();		// check degree of freedom, first option displayed
	}
	
	private void welcomeInstructions1(){
		decisionIndex = 8;
		setInstructions(""
				+ "Welcome to Tar Heel Composer!" +"\n"+"\n"
				+ "In music analysis, chords are commonly represented by roman numerals. The number "+"\n"
				+ "comes from the scale degree of the lowest member of the chord, and if the number "+"\n"
				+ "is uppercase, it means that the chord is major, and if it is lowercase, the chord "+"\n"
				+ "is minor. Minor chords usually sound sadder or more serious than major chords. " +"\n"+"\n"
				+ "Please press any button to continue.");
	}
	
	private void welcomeInstructions2(){
		decisionIndex = 9;
		setInstructions(
				"Sometimes there is also a symbol after the numeral. A ' means that that chord is what "+"\n"
				+ "we call \"diminished,\"and those sound scary or angry. A 7 means that the chord has "+"\n"
				+ "an extra note that pulls toward the next chord. In classical music, there is a "+"\n"
				+ "cycle of chords that is commonly used. You may not know it, but you hear this "+"\n"
				+ "progression in music all the time! Here is a picture of that cycle. Use what you "+"\n"
				+ "know about major and minor chords to think about how these might sound!"+"\n"+"\n"
				+ "Please press any button to continue.");
	}
	
	private void freedomCheck(){		// method for starting out a composition, choosing "mode"
		decisionIndex = 0;
		this.addChord("I");					//initialize first chord to "I"
		prompt[0] = "Please enter your degree of freedom: 1, 2, 3, or 4."; //display prompt
		prompt[1] = "1: Composes and plays for you";
		prompt[2] = "2: Fill in just notes with chords given to you";
		prompt[3] = "3: Fill in chords and notes, with software giving you options for both";
		//prompt[4] = "4: Fill in chords and notes with full independence";
		prompt[4] = "4: Play a demo song";
		prompt();					// get user input (can be changed to button press, etc.
		
	}
	
	private void freedomCheckReturn(){
		this.degreesOfFreedom = decision;
		compose();
	}
	
	private void compose(){
		//System.out.println("Compose");
		//System.out.println(degreesOfFreedom);
		switch(this.degreesOfFreedom){
		case 0:
			for(int i = 0; i < 8; i++){		//for loop to auto-fill chords and notes
				this.addNote();
				if(i<7){this.addChord();}	//1 less for chord since first one is pre-filled with I
				
			}
			this.def0();
			break;
		case 1:
			this.def1();
			break;
		case 2: 
			this.def2();
			break;
		case 3:
			this.demoSong();
			this.keyboard.setTempo(4);
			this.keyboard.playAll();
			this.composition.clear();
			this.keyboard.setTempo(8);
			freedomCheck();
			break;
		}
		//System.exit(0);
	}

	private void def0(){
		decisionIndex = 1;
		//System.out.println("def0");
			//view.display("0 to play, 1 to add chord, 2 to exit");
			/*input = view.getInput();
			if(input.equals("0"))
				keyboard.playAll();
			else if(input.equals("1"))
				this.addChord();
			else if(input.equals("2"))
				System.exit(0);*/
		setInstructions("Press 1 to play, 2 to generate a new composition, 3 to undo last chord and note");
		//System.out.println("End of def0");
		//getButtonInput();
	}
	
	private void def0Return(){
		//System.out.println("def0Return");
		switch(decision){
		case 0: 
			keyboard.playAll();
			break;
		case 1:
			composition.clear(); 	//clears old composition
			this.addChord("I");		//re-initialize first chord to "I"
			this.compose();			//makes a new composition
			break;
		case 2: 
			undo();
			break;
		}
		def0();
	}

	private void def1(){
		decisionIndex = 2;
		if(composition.getNoteSize()>=8){setInstructions("Composition Full. \nPress 1 to play, 3 to reset, or 4 to exit.");}	//check if comp. full
		else{setInstructions("Press 1 to play, 2 to add note, 3 to reset, or 4 to exit.");}	//if prompt still available, prompt it
	}
		
	private void def1Return(){
		switch(decision){
		case 0:
			keyboard.playAll();
			def1();
			break;
		case 1:
			if(composition.getNoteSize()<8){	//if composition not full yet, prompt
				fromDef1=true;
				this.promptNotes(); 
			}
			else{def1();}						//else pass back to original instructions, which add "comp full" text
			break;
		case 2:
			composition.clear();
			clearGrid();
			this.addChord("I");
			def1();
			break;
		case 3:
			System.exit(0);
		}
	}

	private void def2(){
		decisionIndex = 3;
		setInstructions("Press 1 to play, 2 to add note, 3 to add chord, 4 to undo last chord and note");
	}
	
	private void def2Return(){
		switch(decision){
		case 0:
			keyboard.playAll();
			def2();
			break;
		case 1:
			if(this.composition.getSize() <= this.composition.noteLength()){
				decisionIndex = 7;
				setInstructions("Please select a chord first. Press any button to continue.");	
			}	
			else{fromDef2=true; this.promptNotes();}
			break;
		case 2:
			fromDef2=true;
			this.promptChords();
			break;
		case 3: 
			undo();
			def2();
		}
	}

	private void addChord(){
		this.addChord(chordList.getChord(this.composition.getChord(this.composition.getSize()-1).getNextChord()).getName());
	}
	
	private void addChord(String s){
		this.composition.addChord(chordList.getChord(s));
		switch(composition.getSize()-1){
		case 0:
			chord0.setText(composition.getChord(composition.getSize()-1).getName());
			break;
		case 1:
			chord1.setText(composition.getChord(composition.getSize()-1).getName());
			break;
		case 2:
			chord2.setText(composition.getChord(composition.getSize()-1).getName());
			break;
		case 3:
			chord3.setText(composition.getChord(composition.getSize()-1).getName());
			break;
		case 4:
			chord4.setText(composition.getChord(composition.getSize()-1).getName());
			break;
		case 5:
			chord5.setText(composition.getChord(composition.getSize()-1).getName());
			break;
		case 6:
			chord6.setText(composition.getChord(composition.getSize()-1).getName());
			break;
		case 7:
			chord7.setText(composition.getChord(composition.getSize()-1).getName());
			break;
		}
		offset = -3;
	}
	
	private void addNote(){
		int noteIndex = composition.getSize()-1;
		//if composition.getNote(noteIndex - 1).getNum() == ()
		Chord chordForNote = composition.getChord(noteIndex);
		Note nextNote = chordForNote.getNextNote();
		String noteName = nextNote.getName();
		this.addNote(noteName);
	}
	
	private void addNote(String s){
		this.composition.addNote(noteList.getNote(s));
		switch(composition.getNoteSize()-1){
		case 0:
			note0.setText(composition.getNote(composition.getNoteSize()-1).getName());
			break;
		case 1:
			note1.setText(composition.getNote(composition.getNoteSize()-1).getName());
			break;
		case 2:
			note2.setText(composition.getNote(composition.getNoteSize()-1).getName());
			break;
		case 3:
			note3.setText(composition.getNote(composition.getNoteSize()-1).getName());
			break;
		case 4:
			note4.setText(composition.getNote(composition.getNoteSize()-1).getName());
			break;
		case 5:
			note5.setText(composition.getNote(composition.getNoteSize()-1).getName());
			break;
		case 6:
			note6.setText(composition.getNote(composition.getNoteSize()-1).getName());
			break;
		case 7:
			note7.setText(composition.getNote(composition.getNoteSize()-1).getName());
			break;
		}
	}

	public void promptNotes(){
		decisionIndex = 4;
		int index;
		if (composition.noteLength() == 0){index = composition.getNoteSize();}
		else {index = composition.noteLength();}
		prompt[0] = "The current chord is " + composition.getChord(index).getName() + ". Please pick a note:";//prompt
		
		Chord temp = composition.getChord(composition.getNoteSize());
		ArrayList<Note> candidates = temp.getOfferedNotes();
		int notesLen = candidates.size();		//length of notes list for current chord
		
		for(int i = 1; i < 5; i++){
			tempNotes[i] = candidates.get(notesLen - i);
			prompt[i] = (i) + ": " + tempNotes[i].getName();
		}
		
		/*Note note1 = candidates.get(notesLen-4);//note1 = 4th note from end
		Note note2 = candidates.get(notesLen-3);//note2 = 3rd note from end
		Note note3 = candidates.get(notesLen-2);//note3 = 2nd note from end
		Note note4 = candidates.get(notesLen-1);	//note4 = last note*/
		
		prompt();
		
		
		/*if(choiceNumber.equals("1")){ composition.addNote(note1);}
		else if(choiceNumber.equals("2")){composition.addNote(note2);}
		else if(choiceNumber.equals("3")){composition.addNote(note3);}
		else if(choiceNumber.equals("4")){composition.addNote(note4);}
		else {
			view.display("Invalid Input");			//error case
			view.display("Choice 1 assigned");
			composition.addNote(note1);
		}*/
	}
	
	private void promptNotesReturn(){
		composition.addNote(tempNotes[decision + 1]);
		switch(composition.getNoteSize()-1){
		case 0:
			note0.setText(composition.getNote(composition.getNoteSize()-1).getName()); //getNoteSize?
			break;
		case 1:
			note1.setText(composition.getNote(composition.getNoteSize()-1).getName());
			break;
		case 2:
			note2.setText(composition.getNote(composition.getNoteSize()-1).getName());
			break;
		case 3:
			note3.setText(composition.getNote(composition.getNoteSize()-1).getName());
			break;
		case 4:
			note4.setText(composition.getNote(composition.getNoteSize()-1).getName());
			break;
		case 5:
			note5.setText(composition.getNote(composition.getNoteSize()-1).getName());
			break;
		case 6:
			note6.setText(composition.getNote(composition.getNoteSize()-1).getName());
			break;
		case 7:
			note7.setText(composition.getNote(composition.getNoteSize()-1).getName());
			break;
		}
		if(fromDef1){fromDef1=false; if(composition.getSize()<8){addChord();} def1();}
		else if(fromDef2){fromDef2=false; def2();}
		
	}

	
	private void promptChords(){
		promptChordsCalls += 3;
		String currentChordName = composition.getChord(composition.getSize()-1).getName();
		prompt[0] = "Your current chord is " + currentChordName + ". Which chord would you like next?";
		candidates = chordList.getChord(currentChordName).getNextChords();
		int numCandidates = candidates.size();
		//promptChordsCalls = promptChordsCalls % numCandidates;
		if(promptChordsCalls >= numCandidates){promptChordsCalls = 0;}	//counter for number of calls by 3's. If longer than len. of candidates, reset.
		if(numCandidates < 5){
			decisionIndex = 5;
			for(int i = 1; i < 5; i++){		//covers case for 1-4 choices for next chord
				if(i<numCandidates+1){prompt[i] = i + ": " + candidates.get(i-1);}
				else{prompt[i] = "";}
			}
		}	
		else{								//covers case for 5+ choices for next chord
			decisionIndex = 6;
			prompt[4] = "4: More Options";
			for(int i = 1; i<4; i++){
				if(candidates.get(i-1)!=null){prompt[i] = i + ": " + candidates.get(((i-1)+promptChordsCalls)%9);}
				else{prompt[i] = "";}
			}
		}
		prompt();
	}
	
	private void promptChordsReturn1(){
		//System.out.println("Decision: " + decision);
		//System.out.println(candidates.get(decision+promptChordsCalls));
		if(decision < candidates.size()){addChord(candidates.get(decision));}
		def2();
	}
	
	private void promptChordsReturn2(){
		//System.out.println("Decision: " + decision);
		//System.out.println("Offset: " + promptChordsCalls);
		//System.out.println(candidates.get(decision+promptChordsCalls));
		if(decision<3){
			if(candidates.get(decision+promptChordsCalls) != null){addChord(candidates.get(decision+promptChordsCalls));}
			def2();
		}
		else if(decision==3){
			promptChords();
		}
	}
	
	public void setMain(Main main){
		this.main = main;
	}
	
	public void setCompositon(Composition composition){
		this.composition = composition;
	}
	
	@FXML private void button1EnterPressed(KeyEvent event){
		if(event.getCode()==KeyCode.ENTER){
			button1Pressed();
		}
	}
	
	@FXML private void button2EnterPressed(KeyEvent event){
		if(event.getCode()==KeyCode.ENTER){
			button2Pressed();
		}
	}
	
	@FXML private void button3EnterPressed(KeyEvent event){
		if(event.getCode()==KeyCode.ENTER){
			button3Pressed();
		}
	}
	
	@FXML private void button4EnterPressed(KeyEvent event){
		if(event.getCode()==KeyCode.ENTER){
			button4Pressed();
		}
	}
	
	@FXML private void button1Pressed(){
		decision = 0;
		switch(decisionIndex){
		case 0:
			this.freedomCheckReturn();
			break;
		case 1:
			this.def0Return();
			break;
		case 2:
			this.def1Return();
			break;
		case 3:
			this.def2Return();
			break;
		case 4:
			this.promptNotesReturn();
			break;
		case 5:
			this.promptChordsReturn1();
			break;
		case 6:
			this.promptChordsReturn2();
			break;
		case 7:
			this.def2();
			break;
		case 8:
			this.welcomeInstructions2();
			break;
		case 9:
			this.freedomCheck();
			break;
		}
	}
	
	@FXML private void button2Pressed(){
		decision = 1;
		switch(decisionIndex){
		case 0:
			this.freedomCheckReturn();
			break;
		case 1:
			this.def0Return();
			break;
		case 2:
			this.def1Return();
			break;
		case 3:
			this.def2Return();
			break;
		case 4:
			this.promptNotesReturn();
			break;
		case 5:
			this.promptChordsReturn1();
			break;
		case 6:
			this.promptChordsReturn2();
			break;
		case 7:
			this.def2();
			break;
		case 8:
			this.welcomeInstructions2();
			break;
		case 9:
			this.freedomCheck();
			break;
		}
	}
	
	@FXML private void button3Pressed(){
		decision = 2;
		switch(decisionIndex){
		case 0:
			this.freedomCheckReturn();
			break;
		case 1:
			this.def0Return();
			break;
		case 2:
			this.def1Return();
			break;
		case 3:
			this.def2Return();
			break;
		case 4:
			this.promptNotesReturn();
			break;
		case 5:
			this.promptChordsReturn1();
			break;
		case 6:
			this.promptChordsReturn2();
			break;
		case 7:
			this.def2();
			break;
		case 8:
			this.welcomeInstructions2();
			break;
		case 9:
			this.freedomCheck();
			break;
		}
	}
	
	@FXML private void button4Pressed(){
		decision = 3;
		switch(decisionIndex){
		case 0:
			this.freedomCheckReturn();
			break;
		case 1:
			this.def0Return();
			break;
		case 2:
			this.def1Return();
			break;
		case 3:
			this.def2Return();
			break;
		case 4:
			this.promptNotesReturn();
			break;
		case 5:
			this.promptChordsReturn1();
			break;
		case 6:
			this.promptChordsReturn2();
			break;
		case 7:
			this.def2();
			break;
		case 8:
			this.welcomeInstructions2();
			break;
		case 9:
			this.freedomCheck();
			break;
		}
	}
	
	private void setNote(int num, String newText){
		switch (num){
			case 0: note0.setText(newText);
				break;
			case 1: note1.setText(newText);
				break;
			case 2: note2.setText(newText);
				break;
			case 3: note3.setText(newText);
				break;
			case 4: note4.setText(newText);
				break;
			case 5: note5.setText(newText);
				break;
			case 6: note6.setText(newText);
				break;
			case 7: note7.setText(newText);
				break;
			default: instruction_text.setText("Invalid note value.");
				break;
		}
	}
	
	private void setChord(int num, String newText){
		switch (num){
			case 0: chord0.setText(newText);
				break;
			case 1: chord1.setText(newText);
				break;
			case 2: chord2.setText(newText);
				break;
			case 3: chord3.setText(newText);
				break;
			case 4: chord4.setText(newText);
				break;
			case 5: chord5.setText(newText);
				break;
			case 6: chord6.setText(newText);
				break;
			case 7: chord7.setText(newText);
				break;
			default: instruction_text.setText("Invalid chord value.");
				break;
		}
	}
	
	private void setInstructions(String newString){
		//System.out.println(System.identityHashCode(instruction_text));
		instruction_text.setText(newString);
	}
	
	private void clearGrid(){
		note0.setText("?");
		note1.setText("?");
		note2.setText("?");
		note3.setText("?");
		note4.setText("?");
		note5.setText("?");
		note6.setText("?");
		note7.setText("?");
		chord0.setText("?");
		chord1.setText("?");
		chord2.setText("?");
		chord3.setText("?");
		chord4.setText("?");
		chord5.setText("?");
		chord6.setText("?");
		chord7.setText("?");
	}
	
	private void undo(){
		if((composition.getSize())>0 && (composition.getNoteSize())>0){
			this.setChord(this.composition.getSize()-1, "?");
			this.setNote(this.composition.getNoteSize()-1, "?");
			this.composition.removeLastChord();
			this.composition.removeLastNote();
		}
		
	}
	
	private void demoSong(){
		this.addChord("I");
		this.addChord("V7");
		this.addChord("I");
		
		this.addChord("I");
		this.addChord("V7");
		this.addChord("I");
		this.addChord("V7");
		
		this.addChord("I");
		this.addChord("I");
		this.addChord("vii'");
		this.addChord("I");
		
		this.addChord("I");
		this.addChord("V");
		this.addChord("I");
		this.addChord("V");
		
		
		this.addChord("I");
		this.addChord("I");
		this.addChord("IV");
		this.addChord("I");
		
		this.addChord("IV");
		this.addChord("ii");
		this.addChord("IV");
		this.addChord("ii");
		
		this.addChord("I");
		this.addChord("I");
		this.addChord("V7");
		this.addChord("I");
		
		this.addChord("V7");
		this.addChord("I");
		this.addChord("I");
		this.addChord("I");
		
		
		this.addChord("V");
		this.addChord("V");
		this.addChord("I");
		this.addChord("I");
		
		this.addChord("V7");
		this.addChord("I");
		this.addChord("I");
		this.addChord("I");
		
		this.addChord("V7");
		this.addChord("iii");
		this.addChord("I");
		this.addChord("I");
		
		this.addChord("iii");
		this.addChord("V7/V");
		this.addChord("V");
		this.addChord("I");
		
		
		this.addChord("I");
		this.addChord("I");
		this.addChord("IV");
		this.addChord("I");
		
		this.addChord("IV");
		this.addChord("ii");
		this.addChord("I");
		this.addChord("ii");
		
		this.addChord("I");
		this.addChord("I");
		this.addChord("V7");
		this.addChord("I");
		
		this.addChord("V7");
		this.addChord("I");
		this.addChord("I");
		
		this.addNote("E6");
		this.addNote("E6");
		this.addNote("F6");
		this.addNote("G6");

		this.addNote("G6");
		this.addNote("F6");
		this.addNote("E6");
		this.addNote("D6");

		this.addNote("C6");
		this.addNote("C6");
		this.addNote("D6");
		this.addNote("E6");

		this.addNote("E6");
		this.addNote("D6");
		this.addNote("D6");
		this.addNote("D6");


		
		this.addNote("E6");
		this.addNote("E6");
		this.addNote("F6");
		this.addNote("G6");

		this.addNote("G6");
		this.addNote("F6");
		this.addNote("E6");
		this.addNote("D6");

		this.addNote("C6");
		this.addNote("C6");
		this.addNote("D6");
		this.addNote("E6");

		this.addNote("D6");
		this.addNote("C6");
		this.addNote("C6");
		this.addNote("C6");
		
		
		
		this.addNote("D6");
		this.addNote("D6");
		this.addNote("E6");
		this.addNote("C6");

		this.addNote("D6");
		this.addNote("F6");
		this.addNote("E6");
		this.addNote("C6");
		
		this.addNote("D6");
		this.addNote("F6");
		this.addNote("E6");
		this.addNote("D6");

		this.addNote("C6");
		this.addNote("D6");
		this.addNote("G5");
		this.addNote("E6");

		
		this.addNote("E6");
		this.addNote("E6");
		this.addNote("F6");
		this.addNote("G6");

		this.addNote("G6");
		this.addNote("F6");
		this.addNote("E6");
		this.addNote("D6");

		this.addNote("C6");
		this.addNote("C6");
		this.addNote("D6");
		this.addNote("E6");

		this.addNote("D6");
		this.addNote("C6");
		this.addNote("C6");
	}
	
}
